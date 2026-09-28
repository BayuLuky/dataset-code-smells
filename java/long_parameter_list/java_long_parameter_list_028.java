    private String formatErrorMessage(String memTypeExceeded,
        long currentVmemUsage, long vmemLimit,
        long currentPmemUsage, long pmemLimit,
        String pId, ContainerId containerId, ResourceCalculatorProcessTree pTree) {
      return
        String.format("Container [pid=%s,containerID=%s] is running beyond %s memory limits. ",
            pId, containerId, memTypeExceeded) +
        "Current usage: " +
        formatUsageString(currentVmemUsage, vmemLimit,
                          currentPmemUsage, pmemLimit) +
        ". Killing container.\n" +
        "Dump of the process-tree for " + containerId + " :\n" +
        pTree.getProcessTreeDump();
    }
