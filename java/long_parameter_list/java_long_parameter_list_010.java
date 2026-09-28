    public IndexSearchOperatorNodePushable(IHyracksTaskContext ctx, RecordDescriptor inputRecDesc, int partition,
            int[] minFilterFieldIndexes, int[] maxFilterFieldIndexes, IIndexDataflowHelperFactory indexHelperFactory,
            boolean retainInput, boolean retainMissing, IMissingWriterFactory missingWriterFactory,
            ISearchOperationCallbackFactory searchCallbackFactory, boolean appendIndexFilter)
            throws HyracksDataException {
        this(ctx, inputRecDesc, partition, minFilterFieldIndexes, maxFilterFieldIndexes, indexHelperFactory,
                retainInput, retainMissing, missingWriterFactory, searchCallbackFactory, appendIndexFilter, null, -1,
                false, null, null);
    }
