  public void describeTable(DataOutputStream out, String colPath,
      String tableName, Table tbl, Partition part, List<FieldSchema> cols,
      boolean isFormatted, boolean isExt,
      boolean isOutputPadded, List<ColumnStatisticsObj> colStats,
      PrimaryKeyInfo pkInfo, ForeignKeyInfo fkInfo,
      UniqueConstraint ukInfo, NotNullConstraint nnInfo, DefaultConstraint dInfo,
      CheckConstraint cInfo, StorageHandlerInfo storageHandlerInfo) throws HiveException {
    MapBuilder builder = MapBuilder.create();
    builder.put("columns", makeColsUnformatted(cols));

    if (isExt) {
      if (part != null) {
        builder.put("partitionInfo", part.getTPartition());
      }
      else {
        builder.put("tableInfo", tbl.getTTable());
      }
      if (pkInfo != null && !pkInfo.getColNames().isEmpty()) {
        builder.put("primaryKeyInfo", pkInfo);
      }
      if (fkInfo != null && !fkInfo.getForeignKeys().isEmpty()) {
        builder.put("foreignKeyInfo", fkInfo);
      }
      if (ukInfo != null && !ukInfo.getUniqueConstraints().isEmpty()) {
        builder.put("uniqueConstraintInfo", ukInfo);
      }
      if (nnInfo != null && !nnInfo.getNotNullConstraints().isEmpty()) {
        builder.put("notNullConstraintInfo", nnInfo);
      }
      if (dInfo != null && !dInfo.getDefaultConstraints().isEmpty()) {
        builder.put("defaultConstraintInfo", dInfo);
      }
      if (cInfo != null && !cInfo.getCheckConstraints().isEmpty()) {
        builder.put("checkConstraintInfo", cInfo);
      }
      if(storageHandlerInfo != null) {
        builder.put("storageHandlerInfo", storageHandlerInfo.toString());
      }
    }

    asJson(out, builder.build());
  }
