# Annotation (ground-truth provenance for manually labeled cells)

Java design smells (Long Method, God Class, Feature Envy, Data Class) use expert
consensus labels from the **MLCQ** dataset (Madeyski & Lewowski, 2020,
DOI: 10.5281/zenodo.3666840).

Objective smells (Long Parameter List in both languages; Long Method in Python)
were labeled by independent parser rules (>7 parameters; SLOC > 50).

Subjective Python smells (God Class, Data Class, Feature Envy) were labeled by
**two independent annotators** under an intersection rule. Place here:

- `annotation_guidelines.md` — the written guidelines the annotators followed
- `annotator_decisions.csv` — id, annotator_1, annotator_2, final_label
- `kappa.md` (or `.ipynb`) — the Cohen's kappa computation (overall κ = 0.971, 603 candidates)

> These files document the manual labeling and must be added from your own records.
