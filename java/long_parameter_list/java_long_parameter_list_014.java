    private GlyphMapping processWord(final int alignment, final KnuthSequence sequence,
            GlyphMapping prevMapping, final char ch, final boolean breakOpportunity,
            final boolean checkEndsWithHyphen, int level, boolean retainControls) {

        //Word boundary found, process widths and kerning
        int lastIndex = nextStart;
        while (lastIndex > 0 && foText.charAt(lastIndex - 1) == CharUtilities.SOFT_HYPHEN) {
            lastIndex--;
        }
        final boolean endsWithHyphen = checkEndsWithHyphen
                && foText.charAt(lastIndex) == CharUtilities.SOFT_HYPHEN;
        Font font = FontSelector.selectFontForCharactersInText(
            foText, thisStart, lastIndex, foText, this);
        char breakOpportunityChar = breakOpportunity ? ch : 0;
        char precedingChar = prevMapping != null && !prevMapping.isSpace
                && prevMapping.endIndex > 0 ? foText.charAt(prevMapping.endIndex - 1) : 0;
        GlyphMapping mapping = GlyphMapping.doGlyphMapping(foText, thisStart, lastIndex, font,
                letterSpaceIPD, letterSpaceAdjustArray, precedingChar, breakOpportunityChar,
                endsWithHyphen, level, false, false, retainControls);
        prevMapping = mapping;
        addGlyphMapping(mapping);
        tempStart = nextStart;

        //add the elements
        addElementsForAWordFragment(sequence, alignment, mapping, mappings.size() - 1);
        thisStart = nextStart;

        return prevMapping;
    }
