    private void addTag(Definitions defs, int lineno, String symbol,
            String type, String text, String namespace, String signature,
            int lineStart, int lineEnd) {
        // The strings are frequently repeated (a symbol can be used in
        // multiple definitions, multiple definitions can have the same type,
        // one line can contain multiple definitions). Intern them to minimize
        // the space consumed by them (see bug #809).
        defs.addTag(lineno, symbol.trim().intern(), type.trim().intern(),
            text.trim().intern(), namespace == null ? null :
            namespace.trim().intern(), signature, lineStart, lineEnd);
    }
