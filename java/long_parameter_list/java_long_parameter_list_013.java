	CreateViewConstantAction(
								String			schemaName,
								String			tableName,
								int				tableType,
								String			viewText,
								int				checkOption,
								ColumnInfo[]	columnInfo,
								ProviderInfo[]  providerInfo,
								UUID			compSchemaId)
	{
		this.schemaName = schemaName;
		this.tableName = tableName;
		this.tableType = tableType;
		this.viewText = viewText;
		this.checkOption = checkOption;
		this.columnInfo = columnInfo;
		this.providerInfo = providerInfo;
		this.compSchemaId = compSchemaId;

		if (SanityManager.DEBUG)
		{
			SanityManager.ASSERT(schemaName != null, "Schema name is null");
		}
	}
