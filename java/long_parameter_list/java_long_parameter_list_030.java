      public getGroupMembersOfTypeUser_call(String domainId, String groupId, int offset, int limit, org.apache.thrift.async.AsyncMethodCallback resultHandler, org.apache.thrift.async.TAsyncClient client, org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.transport.TNonblockingTransport transport) throws org.apache.thrift.TException {
        super(client, protocolFactory, transport, resultHandler, false);
        this.domainId = domainId;
        this.groupId = groupId;
        this.offset = offset;
        this.limit = limit;
      }
