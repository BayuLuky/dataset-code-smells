	public void drawImage( byte[] data, double height, double width,
			HyperlinkInfo hyper, IStyle style, InlineFlag inlineFlag,
			String altText, String imageUrl )
	{
		if ( inlineFlag == InlineFlag.BLOCK
				|| inlineFlag == InlineFlag.FIRST_INLINE )
		{
			writer.openTag( "w:p" );
		}
		int imageId = getImageID( );
		openHyperlink( hyper );
		writer.openTag( "w:r" );
		writer.openTag( "w:pict" );
		drawImageShapeType( imageId );
		drawImageData( data, imageId );
		drawImageShape( height, width, style, altText, imageId );
		writer.closeTag( "w:pict" );
		writer.closeTag( "w:r" );
		closeHyperlink( hyper );
		if ( inlineFlag == InlineFlag.BLOCK )
		{
			writer.closeTag( "w:p" );
		}
	}
