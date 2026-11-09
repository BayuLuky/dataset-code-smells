# Annotation (provenance for the manually labeled Python cells)

Java design smells (Long Method, God Class, Feature Envy, Data Class) use expert
consensus labels from the **MLCQ** dataset (Madeyski & Lewowski, 2020,
DOI: 10.5281/zenodo.3666840). Objective smells (Long Parameter List, both
languages; Long Method, Python) were labeled by independent parser rules
(> 7 parameters; SLOC > 50).

Subjective Python smells (God Class, Data Class, Feature Envy) were labeled by
**two independent annotators** under an intersection rule: a unit enters the
dataset only when BOTH annotators confirm the smell.

## Files

| File | Description |
|---|---|
| `annotation_guidelines.md` | Written guidelines both annotators followed |
| `annotations_1.csv` | Annotator 1 decisions — `sample_id, proposed_label, final_label, source_file, unit_name, start_line, end_line, annotator` |
| `annotations_2.csv` | Annotator 2 decisions (same columns) |
| `compute_kappa.py` | Reproduces Cohen's κ from the two CSVs (standalone) |

`final_label` is the smell name when confirmed, `tidak` when rejected, or `skip`
when deferred. Only confirmed labels enter the dataset.

## Reproduce the agreement figure

```bash
pip install pandas scikit-learn
python compute_kappa.py annotations_1.csv annotations_2.csv
```

Expected output (writes `results/metrics/kappa_table.csv`):

| scope | n | pct_agreement | cohen_kappa | interpretation |
|---|---|---|---|---|
| overall | 603 | 99.2 | **0.971** | Almost perfect |
| data_class | 345 | 100.0 | 1.000 | Almost perfect |
| feature_envy | 146 | 97.9 | 0.949 | Almost perfect |
| god_class | 112 | 98.2 | 0.962 | Almost perfect |

Reliability is measured on the binary "smell vs not-smell" decision, consistent
with the intersection rule that governs dataset inclusion. The 5 disagreements
are listed in `results/metrics/kappa_disagreements.csv`.
