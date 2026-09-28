    public void XRComposite(int src, int mask, int dst, int srcX, int srcY,
            int maskX, int maskY, int dstX, int dstY, int width, int height) {
        int cachedSrc = (src == XRUtils.None) ? getCurrentSource().picture : src;
        int cachedX = srcX;
        int cachedY = srcY;

        if (enableGradCache && gradient != null
                && cachedSrc == gradient.picture) {
            con.renderComposite(XRUtils.PictOpSrc, gradient.picture,
                    XRUtils.None, gradCachePicture, srcX, srcY, 0, 0, 0, 0,
                    width, height);
            cachedX = 0;
            cachedY = 0;
            cachedSrc = gradCachePicture;
        }

        con.renderComposite(compRule, cachedSrc, mask, dst, cachedX, cachedY,
                maskX, maskY, dstX, dstY, width, height);
    }
