# Annotation Guidelines — Subjective Python Code Smells

These guidelines were used by two independent annotators to label the
**subjective** Python smells (God Class, Data Class, Feature Envy). Objective
smells (Long Method, Long Parameter List) were labeled by parser rules, not by
these guidelines. The final label for each unit was assigned by an
**intersection rule**: a unit is positive for a smell only when **both
annotators agree**.

## Scope
- Unit of analysis: one Python class or one function/method.
- Each unit receives exactly ONE label from: `god_class`, `data_class`,
  `feature_envy`, or `non_smells` (for the subjective pass).

## Decision criteria

### God Class
A class that concentrates **many responsibilities** — many methods and fields,
mixing data, logic, and coordination across several concerns, with low cohesion.
Mark `god_class` when the class clearly does "too much" and could be split.
NOT judged by line count.

### Data Class
A class that holds **data only** — fields plus getters/setters/constructors and
trivial accessors, with little or no meaningful behavior. Mark `data_class` when
the class has essentially no real logic of its own.

### Feature Envy
A method that uses the **data or methods of another class more than its own**.
Mark `feature_envy` when the method's body predominantly accesses an external
object/class (often a parameter) and barely uses `self`.

### Non-smells (subjective pass)
Clean code exhibiting none of the three subjective smells above.

## Disambiguation notes
- God Class vs Data Class: a God Class HAS substantial, varied behavior; a Data
  Class has little or none.
- One smell per unit: if two could apply, pick the dominant one.
- When in doubt, prefer `non_smells` (conservative labeling).

## Resolution rule
- Both annotators label independently.
- A unit is positive for a smell ONLY if both annotators chose the same smell
  (intersection). Disagreements were excluded from the positive set.
- An annotator may mark `skip` when abstaining; a `skip` does not assert a smell.

## Inter-rater reliability (how kappa is computed)
Because the intersection rule only cares whether BOTH annotators call a unit a
smell, reliability is measured on the BINARY decision "smell vs not-smell"
(final_label in {god_class, data_class, feature_envy} = smell; `tidak`/
`non_smells` and `skip` = not-smell).

- **Cohen's kappa = 0.971**, 99.2% agreement, **5 disagreements** over 603
  candidates. The 5 are units Erick labeled a smell (2 God Class, 3 Feature
  Envy) while Akbar skipped.
- For transparency, the raw multi-class kappa (treating `skip` as its own
  category) is 0.901; the binary figure is the one reported, as it matches the
  dataset-inclusion rule.
- Reproduce with `compute_kappa.py` (see this folder).
