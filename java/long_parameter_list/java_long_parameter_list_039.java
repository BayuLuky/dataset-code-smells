    public SAXImpl(XSLTCDTMManager mgr, Source source,
                   int dtmIdentity, DTMWSFilter whiteSpaceFilter,
                   XMLStringFactory xstringfactory,
                   boolean doIndexing, int blocksize,
                   boolean buildIdIndex,
                   boolean newNameTable)
    {
        super(mgr, source, dtmIdentity, whiteSpaceFilter, xstringfactory,
            doIndexing, blocksize, false, buildIdIndex, newNameTable);

        _dtmManager = mgr;
        _size = blocksize;

        // Use a smaller size for the space stack if the blocksize is small
        _xmlSpaceStack = new int[blocksize <= 64 ? 4 : 64];

        /* From DOMBuilder */
        _xmlSpaceStack[0] = DTMDefaultBase.ROOTNODE;

        // If the input source is DOMSource, set the _document field and
        // create the node2Ids table.
        if (source instanceof DOMSource) {
            _hasDOMSource = true;
            DOMSource domsrc = (DOMSource)source;
            Node node = domsrc.getNode();
            if (node instanceof Document) {
                _document = (Document)node;
            }
            else {
                _document = node.getOwnerDocument();
            }
            _node2Ids = new HashMap<>();
        }
    }
