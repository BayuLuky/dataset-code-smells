      public submitTopology_call(String name, String uploadedJarLocation, String jsonConf, StormTopology topology, org.apache.thrift.async.AsyncMethodCallback resultHandler, org.apache.thrift.async.TAsyncClient client, org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.transport.TNonblockingTransport transport) throws org.apache.thrift.TException {
        super(client, protocolFactory, transport, resultHandler, false);
        this.name = name;
        this.uploadedJarLocation = uploadedJarLocation;
        this.jsonConf = jsonConf;
        this.topology = topology;
      }
