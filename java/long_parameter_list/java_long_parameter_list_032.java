  public OrcEncodedDataReader(LowLevelCache lowLevelCache, BufferUsageManager bufferManager,
      MetadataCache metadataCache, Configuration daemonConf, Configuration jobConf,
      FileSplit split, Includes includes, SearchArgument sarg, OrcEncodedDataConsumer consumer,
      QueryFragmentCounters counters, SchemaEvolutionFactory sef, Pool<IoTrace> tracePool)
          throws IOException {
    this.lowLevelCache = lowLevelCache;
    this.metadataCache = metadataCache;
    this.bufferManager = bufferManager;
    this.daemonConf = daemonConf;
    this.split = split;
    this.sarg = sarg;
    this.consumer = consumer;
    this.counters = counters;
    this.trace = tracePool.take();
    this.tracePool = tracePool;
    try {
      this.ugi = UserGroupInformation.getCurrentUser();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    this.useCodecPool = HiveConf.getBoolVar(daemonConf, ConfVars.HIVE_ORC_CODEC_POOL);
    this.useObjectPools = HiveConf.getBoolVar(daemonConf, ConfVars.LLAP_IO_SHARE_OBJECT_POOLS);

    // LlapInputFormat needs to know the file schema to decide if schema evolution is supported.
    orcReader = null;
    cacheTag = HiveConf.getBoolVar(daemonConf, ConfVars.LLAP_TRACK_CACHE_USAGE)
        ? LlapUtil.getDbAndTableNameForMetrics(split.getPath(), true) : null;
    // 1. Get file metadata from cache, or create the reader and read it.
    // Don't cache the filesystem object for now; Tez closes it and FS cache will fix all that
    fsSupplier = Utilities.getFsSupplier(split.getPath(), jobConf);
    fileKey = determineFileId(fsSupplier, split,
        HiveConf.getBoolVar(daemonConf, ConfVars.LLAP_CACHE_ALLOW_SYNTHETIC_FILEID),
        HiveConf.getBoolVar(daemonConf, ConfVars.LLAP_CACHE_DEFAULT_FS_FILE_ID),
        !HiveConf.getBoolVar(daemonConf, ConfVars.LLAP_IO_USE_FILEID_PATH)
        );
    fileMetadata = getFileFooterFromCacheOrDisk();
    final TypeDescription fileSchema = fileMetadata.getSchema();

    fileIncludes = includes.generateFileIncludes(fileSchema);
    if (LOG.isDebugEnabled()) {
      LOG.debug("From {}, the file includes are {}", includes, DebugUtils.toString(fileIncludes));
    }

    // Do not allow users to override zero-copy setting. The rest can be taken from user config.
    boolean useZeroCopy = OrcConf.USE_ZEROCOPY.getBoolean(daemonConf);
    if (useZeroCopy != OrcConf.USE_ZEROCOPY.getBoolean(jobConf)) {
      jobConf = new Configuration(jobConf);
      jobConf.setBoolean(OrcConf.USE_ZEROCOPY.getAttribute(), useZeroCopy);
    }
    this.jobConf = jobConf;
    // TODO: setFileMetadata could just create schema. Called in two places; clean up later.
    this.evolution = sef.createSchemaEvolution(fileMetadata.getSchema());
    consumer.setUseDecimal64ColumnVectors(HiveConf.getVar(jobConf,
      ConfVars.HIVE_VECTORIZED_INPUT_FORMAT_SUPPORTS_ENABLED).equalsIgnoreCase("decimal_64"));
    consumer.setFileMetadata(fileMetadata);
    consumer.setSchemaEvolution(evolution);
  }
