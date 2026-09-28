	public TaskExecutor(
			RpcService rpcService,
			TaskManagerConfiguration taskManagerConfiguration,
			HighAvailabilityServices haServices,
			TaskManagerServices taskExecutorServices,
			HeartbeatServices heartbeatServices,
			TaskManagerMetricGroup taskManagerMetricGroup,
			@Nullable String metricQueryServicePath,
			BlobCacheService blobCacheService,
			FatalErrorHandler fatalErrorHandler) {

		super(rpcService, AkkaRpcServiceUtils.createRandomName(TASK_MANAGER_NAME));

		checkArgument(taskManagerConfiguration.getNumberSlots() > 0, "The number of slots has to be larger than 0.");

		this.taskManagerConfiguration = checkNotNull(taskManagerConfiguration);
		this.taskExecutorServices = checkNotNull(taskExecutorServices);
		this.haServices = checkNotNull(haServices);
		this.fatalErrorHandler = checkNotNull(fatalErrorHandler);
		this.taskManagerMetricGroup = checkNotNull(taskManagerMetricGroup);
		this.blobCacheService = checkNotNull(blobCacheService);
		this.metricQueryServicePath = metricQueryServicePath;

		this.taskSlotTable = taskExecutorServices.getTaskSlotTable();
		this.jobManagerTable = taskExecutorServices.getJobManagerTable();
		this.jobLeaderService = taskExecutorServices.getJobLeaderService();
		this.taskManagerLocation = taskExecutorServices.getTaskManagerLocation();
		this.localStateStoresManager = taskExecutorServices.getTaskManagerStateStore();
		this.networkEnvironment = taskExecutorServices.getNetworkEnvironment();
		this.resourceManagerLeaderRetriever = haServices.getResourceManagerLeaderRetriever();

		this.jobManagerConnections = new HashMap<>(4);

		final ResourceID resourceId = taskExecutorServices.getTaskManagerLocation().getResourceID();

		this.jobManagerHeartbeatManager = heartbeatServices.createHeartbeatManager(
			resourceId,
			new JobManagerHeartbeatListener(),
			rpcService.getScheduledExecutor(),
			log);

		this.resourceManagerHeartbeatManager = heartbeatServices.createHeartbeatManager(
			resourceId,
			new ResourceManagerHeartbeatListener(),
			rpcService.getScheduledExecutor(),
			log);

		this.hardwareDescription = HardwareDescription.extractFromSystem(
			taskExecutorServices.getMemoryManager().getMemorySize());

		this.resourceManagerAddress = null;
		this.resourceManagerConnection = null;
		this.currentRegistrationTimeoutId = null;

		this.stackTraceSampleService = new StackTraceSampleService(rpcService.getScheduledExecutor());
	}
