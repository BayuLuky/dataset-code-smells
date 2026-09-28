    public IntermediateModel(
        Metadata metadata,
        Map<String, OperationModel> operations,
        Map<String, ShapeModel> shapes,
        CustomizationConfig customizationConfig,
        ServiceExamples examples,
        OperationModel endpointOperation,
        Map<String, AuthorizerModel> customAuthorizers,
        Map<String, PaginatorDefinition> paginators,
        NamingStrategy namingStrategy) {
        this.metadata = metadata;
        this.operations = operations;
        this.shapes = shapes;
        this.customizationConfig = customizationConfig;
        this.examples = examples;
        this.endpointOperation = Optional.ofNullable(endpointOperation);
        this.customAuthorizers = customAuthorizers;
        this.paginators = paginators;
        this.namingStrategy = namingStrategy;
    }
