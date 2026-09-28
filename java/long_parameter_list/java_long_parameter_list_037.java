	public void drawBackgroundImage( String imageURI, byte[] imageData,
	        float x, float y, float width, float height, float imageWidth,
	        float imageHeight, float positionX, float positionY, int repeat )
	        throws IOException
	{
		if ( imageData == null || imageData.length == 0 )
		{
			return;
		}
		org.eclipse.birt.report.engine.layout.emitter.Image image = EmitterUtil
		        .parseImage( imageData, null, null );
		imageData = image.getData( );

		if ( imageWidth == 0 || imageHeight == 0 )
		{
			int resolutionX = image.getPhysicalWidthDpi( );
			int resolutionY = image.getPhysicalHeightDpi( );
			if ( resolutionX <= 0 || resolutionY <= 0 )
			{
				resolutionX = 96;
				resolutionY = 96;
			}
			imageWidth = ( (float) image.getWidth( ) ) / resolutionX * 72;
			imageHeight = ( (float) image.getHeight( ) ) / resolutionY * 72;
		}

		Position imageSize = new Position( imageWidth, imageHeight );
		Position areaPosition = new Position( x, y );
		Position areaSize = new Position( width, height );
		Position imagePosition = new Position( x + positionX, y + positionY );
		BackgroundImageLayout layout = new BackgroundImageLayout( areaPosition,
		        areaSize, imagePosition, imageSize );
		Collection positions = layout.getImagePositions( repeat );
		gSave( );
		setColor( Color.WHITE );
		out.println( "newpath" );
		drawRawRect( x, y, width, height );
		out.println( "closepath clip" );
		Iterator iterator = positions.iterator( );
		while ( iterator.hasNext( ) )
		{
			Position position = (Position) iterator.next( );
			try
			{
				drawImage( imageURI, new ByteArrayInputStream( imageData ),
				           position.getX( ), position.getY( ),
				           imageSize.getX( ), imageSize.getY( ) );
			}
			catch ( Exception e )
			{
				log.log( Level.WARNING, e.getLocalizedMessage( ) );
			}
		}
		gRestore( );
	}
