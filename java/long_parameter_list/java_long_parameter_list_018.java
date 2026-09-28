	private Event createMouseEvent(TreeItem swtTreeItem, Widget item, int type, int x, int y, int button, int stateMask,
			int count) {
		Event event = new Event();
		event.time = (int) System.currentTimeMillis();
		event.widget = swtTreeItem;
		event.display = Display.getDisplay();
		event.x = x;
		event.y = y;
		event.button = button;
		event.stateMask = stateMask;
		event.count = count;
		event.item = item;
		event.type = type;
		return event;
	}
