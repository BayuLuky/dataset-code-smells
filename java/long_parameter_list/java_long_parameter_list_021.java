	private java.awt.GradientPaint getGradient(float x, float y, float width, float height, int argbTL, int argbTR, int argbBL, int argbBR)
	{
		float x2 = x + width;
		float y2 = y + height;

		if (argbTL != argbTR || argbTL != argbBL || argbTL != argbBR)
		{
			if (Math.abs(argbTL - argbTR) >= Math.abs(argbTL - argbBL) &&
				Math.abs(argbTL - argbTR) >= Math.abs(argbTL - argbBR))
				return new java.awt.GradientPaint(x, y, new java.awt.Color(argbTL, true), x2, y, new java.awt.Color(argbTR, true));
			else if (Math.abs(argbTL - argbBL) < Math.abs(argbTL - argbBR))
				return new java.awt.GradientPaint(x, y, new java.awt.Color(argbTL, true), x2, y2, new java.awt.Color(argbBR, true));
			else
				return new java.awt.GradientPaint(x, y, new java.awt.Color(argbTL, true), x, y2, new java.awt.Color(argbBL, true));
		}

		return null;
	}
