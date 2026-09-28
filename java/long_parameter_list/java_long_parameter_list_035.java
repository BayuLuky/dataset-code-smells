    ReplicaHealth(String shard, String name, String url, String status,
        long numDocs, boolean isLeader, String uptime, String memory) {
      this.shard = shard;
      this.name = name;
      this.url = url;
      this.numDocs = numDocs;
      this.status = status;
      this.isLeader = isLeader;
      this.uptime = uptime;
      this.memory = memory;
    }
