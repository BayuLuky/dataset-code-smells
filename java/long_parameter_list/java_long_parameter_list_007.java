  public ActiveScan(
    java.lang.String client,
    java.lang.String user,
    java.lang.String table,
    long age,
    long idleTime,
    ScanType type,
    ScanState state,
    KeyExtent extent,
    java.util.List<Column> columns,
    java.util.List<IteratorSetting> iterators,
    java.util.List<java.nio.ByteBuffer> authorizations)
  {
    this();
    this.client = client;
    this.user = user;
    this.table = table;
    this.age = age;
    setAgeIsSet(true);
    this.idleTime = idleTime;
    setIdleTimeIsSet(true);
    this.type = type;
    this.state = state;
    this.extent = extent;
    this.columns = columns;
    this.iterators = iterators;
    this.authorizations = authorizations;
  }
