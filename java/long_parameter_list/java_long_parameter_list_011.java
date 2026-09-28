  protected CalciteSchema(CalciteSchema parent, Schema schema,
      String name, NameMap<CalciteSchema> subSchemaMap,
      NameMap<TableEntry> tableMap, NameMap<LatticeEntry> latticeMap, NameMap<TypeEntry> typeMap,
      NameMultimap<FunctionEntry> functionMap, NameSet functionNames,
      NameMap<FunctionEntry> nullaryFunctionMap,
      List<? extends List<String>> path) {
    this.parent = parent;
    this.schema = schema;
    this.name = name;
    if (tableMap == null) {
      this.tableMap = new NameMap<>();
    } else {
      this.tableMap = Objects.requireNonNull(tableMap);
    }
    if (latticeMap == null) {
      this.latticeMap = new NameMap<>();
    } else {
      this.latticeMap = Objects.requireNonNull(latticeMap);
    }
    if (subSchemaMap == null) {
      this.subSchemaMap = new NameMap<>();
    } else {
      this.subSchemaMap = Objects.requireNonNull(subSchemaMap);
    }
    if (functionMap == null) {
      this.functionMap = new NameMultimap<>();
      this.functionNames = new NameSet();
      this.nullaryFunctionMap = new NameMap<>();
    } else {
      // If you specify functionMap, you must also specify functionNames and
      // nullaryFunctionMap.
      this.functionMap = Objects.requireNonNull(functionMap);
      this.functionNames = Objects.requireNonNull(functionNames);
      this.nullaryFunctionMap = Objects.requireNonNull(nullaryFunctionMap);
    }
    if (typeMap == null) {
      this.typeMap = new NameMap<>();
    } else {
      this.typeMap = Objects.requireNonNull(typeMap);
    }
    this.path = path;
  }
