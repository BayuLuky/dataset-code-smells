    ChangeSetStats(
        int effectiveChangesCount,
        int insertSingleCount,
        int insertRangeCount,
        int deleteSingleCount,
        int deleteRangeCount,
        int updateSingleCount,
        int updateRangeCount,
        int moveCount) {
      mEffectiveChangesCount = effectiveChangesCount;
      mInsertSingleCount = insertSingleCount;
      mInsertRangeCount = insertRangeCount;
      mDeleteSingleCount = deleteSingleCount;
      mDeleteRangeCount = deleteRangeCount;
      mUpdateSingleCount = updateSingleCount;
      mUpdateRangeCount = updateRangeCount;
      mMoveCount = moveCount;
    }
