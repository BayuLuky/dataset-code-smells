    private DefaultIndexingContext( String id,
                                    String repositoryId,
                                    File repository, //
                                    String repositoryUrl, String indexUpdateUrl,
                                    List<? extends IndexCreator> indexCreators, Directory indexDirectory,
                                    TrackingLockFactory lockFactory,
                                    boolean reclaimIndex )
        throws ExistingLuceneIndexMismatchException, IOException
    {

        this.id = id;

        this.searchable = true;

        this.repositoryId = repositoryId;

        this.repository = repository;

        this.repositoryUrl = repositoryUrl;

        this.indexUpdateUrl = indexUpdateUrl;

        this.indexWriter = null;

        this.searcherManager = null;

        this.indexCreators = indexCreators;

        this.indexDirectory = indexDirectory;

        this.lockFactory = lockFactory;

        // eh?
        // Guice does NOT initialize these, and we have to do manually?
        // While in Plexus, all is well, but when in guice-shim,
        // these objects are still LazyHintedBeans or what not and IndexerFields are NOT registered!
        for ( IndexCreator indexCreator : indexCreators )
        {
            indexCreator.getIndexerFields();
        }

        this.gavCalculator = new M2GavCalculator();

        prepareIndex( reclaimIndex );

        setIndexDirectoryFile( null );
    }
