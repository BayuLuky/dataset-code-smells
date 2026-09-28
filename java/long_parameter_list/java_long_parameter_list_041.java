    public JCMethodDecl MethodDef(JCModifiers mods,
                               Name name,
                               JCExpression restype,
                               List<JCTypeParameter> typarams,
                               List<JCVariableDecl> params,
                               List<JCExpression> thrown,
                               JCBlock body,
                               JCExpression defaultValue) {
        return MethodDef(
                mods, name, restype, typarams, null, params,
                thrown, body, defaultValue);
    }
