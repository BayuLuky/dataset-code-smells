  public ReduceContextImpl(Configuration conf, TaskAttemptID taskid,
                           RawKeyValueIterator input, 
                           Counter inputKeyCounter,
                           Counter inputValueCounter,
                           RecordWriter<KEYOUT,VALUEOUT> output,
                           OutputCommitter committer,
                           StatusReporter reporter,
                           RawComparator<KEYIN> comparator,
                           Class<KEYIN> keyClass,
                           Class<VALUEIN> valueClass
                          ) throws InterruptedException, IOException{
    super(conf, taskid, output, committer, reporter);
    this.input = input;
    this.inputKeyCounter = inputKeyCounter;
    this.inputValueCounter = inputValueCounter;
    this.comparator = comparator;
    this.serializationFactory = new SerializationFactory(conf);
    this.keyDeserializer = serializationFactory.getDeserializer(keyClass);
    this.keyDeserializer.open(buffer);
    this.valueDeserializer = serializationFactory.getDeserializer(valueClass);
    this.valueDeserializer.open(buffer);
    hasMore = input.next();
    this.keyClass = keyClass;
    this.valueClass = valueClass;
    this.conf = conf;
    this.taskid = taskid;
  }
