  private FSDataOutputStream create(Path f, FsPermission permission,
      boolean overwrite, boolean createParent, int bufferSize,
      short replication, long blockSize,
      Progressable progress) throws IOException {
    Path parent = f.getParent();
    if (parent != null) {
      if (!createParent && !exists(parent)) {
        throw new FileNotFoundException("Parent directory doesn't exist: "
            + parent);
      } else if (!mkdirs(parent)) {
        throw new IOException("Mkdirs failed to create " + parent
            + " (exists=" + exists(parent) + ", cwd=" + getWorkingDirectory()
            + ")");
      }
    }
    final FSDataOutputStream out;
    if (writeChecksum) {
      out = new FSDataOutputStream(
          new ChecksumFSOutputSummer(this, f, overwrite, bufferSize, replication,
              blockSize, progress), null);
    } else {
      out = fs.create(f, permission, overwrite, bufferSize, replication,
          blockSize, progress);
      // remove the checksum file since we aren't writing one
      Path checkFile = getChecksumFile(f);
      if (fs.exists(checkFile)) {
        fs.delete(checkFile, true);
      }
    }
    if (permission != null) {
      setPermission(f, permission);
    }
    return out;
  }
