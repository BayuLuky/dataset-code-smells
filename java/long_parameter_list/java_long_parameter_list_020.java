  public static void writeBack(final ITranslationEnvironment environment, final long offset,
      final IOperandTree targetOperand, final String sourceValue, final OperandSize size,
      final String address, final TranslationResultType targetType,
      final List<ReilInstruction> instructions) throws IllegalArgumentException,
      InternalTranslationException {
    Preconditions.checkNotNull(environment, "Error: Argument environment can't be null");
    Preconditions.checkNotNull(targetOperand, "Error: Argument targetOperand can't be null");
    Preconditions.checkNotNull(sourceValue, "Error: Argument sourceValue can't be null");
    Preconditions.checkNotNull(size, "Error: Argument size can't be null");
    Preconditions.checkNotNull(targetType, "Error: Argument targetType can't be null");
    Preconditions.checkNotNull(instructions, "Error: Argument instructions can't be null");

    // TODO: Exact meaning of parameter size

    final OperandSize archSize = environment.getArchitectureSize();

    if (targetType == TranslationResultType.REGISTER) {

      if (address != null) {
        throw new InternalTranslationException("Error: Invalid combination of parameters");
      }

      // The target is a register. Either write the value directly or mask it into the target

      final String target = getLeafValue(targetOperand.getRootNode());

      if ((size == archSize) || isSegment(target)) {
        instructions.add(ReilHelpers.createStr(offset, size, sourceValue, size, target));
      } else {
        moveAndMask(environment, offset, size, sourceValue, target, instructions);
      }

    } else if (targetType == TranslationResultType.MEMORY_ACCESS) {

      // The target is a memory address. Store the value there.

      instructions.add(ReilHelpers.createStm(offset, size, sourceValue, archSize, address));
    } else {
      throw new InternalTranslationException("Error: Invalid target type");
    }
  }
