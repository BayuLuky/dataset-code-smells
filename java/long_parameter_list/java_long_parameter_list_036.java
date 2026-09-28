        protected Object trace(VirtualFrame frame, Object whatObj, Object tracer, Object exit, Object at, Object printObj, Object signature, Object whereObj) {
            Object what = whatObj;
            helper.checkWhat(what);
            Object where = whereObj;
            if (where == RMissing.instance) {
                where = helper.getWhere(frame);
            }
            String funcName = RRuntime.asString(what);
            if (funcName != null) {
                what = helper.getFunction(frame, what, where);
            }
            RFunction func = helper.checkFunction(what);

            if (tracer == RMissing.instance && exit == RMissing.instance && at == RMissing.instance && printObj == RMissing.instance && signature == RMissing.instance) {
                // simple case, nargs() == 1, corresponds to .primTrace that has invisible output
                if (primTrace == null) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    primTrace = insert(PrimTraceNodeGen.create());
                }

                Object result = primTrace.execute(frame, func);
                visibility.execute(frame, false);
                return result;
            }

            if (at != RMissing.instance) {
                throw RError.nyi(this, "'at'");
            }
            boolean print = true;
            if (printObj != RMissing.instance) {
                if (castLogical == null) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    castLogical = insert(CastLogicalNodeGen.create(false, false, false));
                }
                print = RRuntime.fromLogical((byte) castLogical.doCast(printObj));
            }
            complexCase(func, tracer, exit, at, print, signature);
            visibility.execute(frame, true);
            return Utils.toString(func);
        }
