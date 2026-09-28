    public HadoopShuffleJob(T locReduceAddr, IgniteLogger log, HadoopJobEx job, GridUnsafeMemory mem,
        int totalReducerCnt, int[] locReducers, int locMappersCnt, boolean embedded) throws IgniteCheckedException {
        this.locReduceAddr = locReduceAddr;
        this.totalReducerCnt = totalReducerCnt;
        this.job = job;
        this.mem = mem;
        this.log = log.getLogger(HadoopShuffleJob.class);
        this.embedded = embedded;

        boolean stripeMappers0 = get(job.info(), SHUFFLE_MAPPER_STRIPED_OUTPUT, true);

        if (stripeMappers0) {
            if (!embedded) {
                if (log.isInfoEnabled())
                    log.info("Striped mapper output is disabled becuase it cannot be used in external mode [jobId=" +
                        job.id() + ']');

                stripeMappers0 = false;
            }
        }

        stripeMappers = stripeMappers0;

        msgSize = get(job.info(), SHUFFLE_MSG_SIZE, DFLT_SHUFFLE_MSG_SIZE);
        msgGzip = get(job.info(), SHUFFLE_MSG_GZIP, DFLT_SHUFFLE_MSG_GZIP);

        locReducersCtx = new AtomicReferenceArray<>(totalReducerCnt);

        if (!F.isEmpty(locReducers)) {
            for (int rdc : locReducers) {
                HadoopTaskInfo taskInfo = new HadoopTaskInfo(HadoopTaskType.REDUCE, job.id(), rdc, 0, null);

                locReducersCtx.set(rdc, new LocalTaskContextProxy(taskInfo));
            }
        }

        needPartitioner = totalReducerCnt > 1;

        // Size of local map is always equal to total reducer number to allow index-based lookup.
        locMaps = new AtomicReferenceArray<>(totalReducerCnt);

        // Size of remote map:
        // - If there are no local mappers, then we will not send anything, so set to 0;
        // - If output is not striped, then match it to total reducer count, the same way as for local maps.
        // - If output is striped, then multiply previous value by number of local mappers.
        int rmtMapsSize = locMappersCnt == 0 ? 0 : totalReducerCnt;

        if (stripeMappers)
            rmtMapsSize *= locMappersCnt;

        rmtMaps = new AtomicReferenceArray<>(rmtMapsSize);
        msgs = new HadoopShuffleMessage[rmtMapsSize];

        throttle = get(job.info(), SHUFFLE_JOB_THROTTLE, 0);
    }
