      public get_call(java.nio.ByteBuffer tableName, java.nio.ByteBuffer row, java.nio.ByteBuffer column, java.util.Map<java.nio.ByteBuffer,java.nio.ByteBuffer> attributes, org.apache.thrift.async.AsyncMethodCallback<java.util.List<TCell>> resultHandler, org.apache.thrift.async.TAsyncClient client, org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.transport.TNonblockingTransport transport) throws org.apache.thrift.TException {
        super(client, protocolFactory, transport, resultHandler, false);
        this.tableName = tableName;
        this.row = row;
        this.column = column;
        this.attributes = attributes;
      }
