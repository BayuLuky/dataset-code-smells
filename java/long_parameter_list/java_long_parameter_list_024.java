    DiscreteXYPainter(float lineWidth, Color lineColor, Color fillColor,
                      int width, boolean fixedWidth, boolean topLineOnly,
                      boolean outlineOnly, double dataFactor, PointsComputer computer) {

        super((int)Math.ceil(lineWidth), fillColor != null ||
              (!topLineOnly && !outlineOnly), dataFactor);
        
        if (lineColor == null && fillColor == null)
            throw new IllegalArgumentException("lineColor or fillColor must not be null"); // NOI18N

        this.lineWidth = (int)Math.ceil(lineWidth);
        this.lineColor = Utils.checkedColor(lineColor);
        this.fillColor = Utils.checkedColor(fillColor);

        definingColor = lineColor != null ? lineColor : fillColor;

        this.lineStroke = new BasicStroke(lineWidth, BasicStroke.CAP_ROUND,
                                          BasicStroke.JOIN_ROUND);

        this.width = width;
        this.fixedWidth = fixedWidth;
        this.topLineOnly = topLineOnly;
        this.outlineOnly = outlineOnly;

        this.computer = computer;
    }
