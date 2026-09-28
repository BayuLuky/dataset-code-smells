    private void buildResolveList(Intent intent, FastImmutableArraySet<String> categories,
            boolean debug, boolean defaultOnly,
            String resolvedType, String scheme, F[] src, List<R> dest) {
        final String action = intent.getAction();
        final Uri data = intent.getData();
        final String packageName = intent.getPackage();

        final boolean excludingStopped = (intent.getFlags()&(Intent.FLAG_EXCLUDE_STOPPED_PACKAGES|Intent.FLAG_INCLUDE_STOPPED_PACKAGES))
                == Intent.FLAG_EXCLUDE_STOPPED_PACKAGES;

        final Printer logPrinter = null;
        final PrintWriter logPrintWriter = null;

        final int N = src != null ? src.length : 0;
        boolean hasNonDefaults = false;
        int i;
        F filter;
        for (i=0; i<N && (filter=src[i]) != null; i++) {
            int match;
            if (debug) Log.v(TAG, "Matching against filter " + filter);

            if (excludingStopped && isFilterStopped(filter)) {
                if (debug) {
                    Log.v(TAG, "  Filter's target is stopped; skipping");
                }
                continue;
            }

            // Is delivery being limited to filters owned by a particular package?
            if (packageName != null && !isPackageForFilter(packageName, filter)) {
                if (debug) {
                    Log.v(TAG, "  Filter is not from package " + packageName + "; skipping");
                }
                continue;
            }

            // Do we already have this one?
            if (!allowFilterResult(filter, dest)) {
                if (debug) {
                    Log.v(TAG, "  Filter's target already added");
                }
                continue;
            }

            match = filter.match(action, resolvedType, scheme, data, categories, TAG);
            if (match >= 0) {
                if (debug) Log.v(TAG, "  Filter matched!  match=0x" +
                        Integer.toHexString(match) + " hasDefault="
                        + filter.hasCategory(Intent.CATEGORY_DEFAULT));
                if (!defaultOnly || filter.hasCategory(Intent.CATEGORY_DEFAULT)) {
                    final R oneResult = newResult(filter, match);
                    if (oneResult != null) {
                        dest.add(oneResult);
//                        if (debug) {
//                            dumpFilter(logPrintWriter, "    ", filter);
//                            logPrintWriter.flush();
//                            filter.dump(logPrinter, "    ");
//                        }
                    }
                } else {
                    hasNonDefaults = true;
                }
            } else {
                if (debug) {
                    String reason;
                    switch (match) {
                        case IntentFilter.NO_MATCH_ACTION: reason = "action"; break;
                        case IntentFilter.NO_MATCH_CATEGORY: reason = "category"; break;
                        case IntentFilter.NO_MATCH_DATA: reason = "data"; break;
                        case IntentFilter.NO_MATCH_TYPE: reason = "type"; break;
                        default: reason = "unknown reason"; break;
                    }
                    Log.v(TAG, "  Filter did not match: " + reason);
                }
            }
        }

        if (hasNonDefaults) {
            if (dest.size() == 0) {
                Log.w(TAG, "resolveIntent failed: found match, but none with CATEGORY_DEFAULT");
            } else if (dest.size() > 1) {
                Log.w(TAG, "resolveIntent: multiple matches, only some with CATEGORY_DEFAULT");
            }
        }
    }
