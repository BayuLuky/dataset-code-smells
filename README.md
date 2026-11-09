# Code Smell Detection — Dataset & Reproducibility Package

Dataset, prompts, per-sample predictions, and code accompanying the paper:

> **Evaluating Open-Weight Large Language Models Against Static Analysis for Code Smell Detection**
> Bayu Luky Istanto, Abba Suganda Girsang — Bina Nusantara University, Jakarta, Indonesia
> The 14th International Conference on Orange Technology (ICOT 2026), Paper 274.

The study compares three detectors on an identical set of **463 single-label
source files** (234 Java, 229 Python) across **six classes** (five code smells
+ a clean class): **SonarQube** (rule-based baseline), **UniXcoder** (125M,
fine-tuned encoder), and **Qwen2.5-Coder-7B** (decoder, prompted).

---

## Repository structure

```
dataset-code-smells/
├── README.md                     ← this file
├── LICENSE                       ← CC BY 4.0
├── CITATION.cff                  ← how to cite
├── requirements.txt              ← package versions
├── ground_truth.csv              ← manifest: id, language, label, code_path (+ provenance)
│
├── java/                         ← 234 Java units, by smell category
│   ├── data_class/  feature_envy/  god_class/
│   ├── long_method/  long_parameter_list/  non_smells/
├── python/                       ← 229 Python units, by smell category
│   └── (same six categories)
│
├── prompts/                      ← exact Qwen prompts (4 variants + justification)
├── predictions/                  ← paired per-sample predictions (all 3 detectors)
├── efficiency/                   ← per-file inference timing
├── annotation/                   ← annotation guidelines, annotator decisions, kappa
└── notebooks/                    ← full training / inference code (Tahap 2–4)
```

Each subfolder has its own `README.md` describing the files it should contain.

## Dataset composition

| Class | Java | Python |
|---|---|---|
| long_method | 42 | 42 |
| god_class | 42 | 40 |
| feature_envy | 24 | 39 |
| data_class | 42 | 24 |
| long_parameter_list | 42 | 42 |
| non_smells | 42 | 42 |
| **Subtotal** | **234** | **229** |

**Total: 463 single-label units.** The ground-truth label of each file is given
by its category folder and recorded in `ground_truth.csv`.

## Label provenance

- **Java design smells** (Long Method, God Class, Feature Envy, Data Class):
  expert consensus from the **MLCQ** dataset (Madeyski & Lewowski, 2020,
  DOI: 10.5281/zenodo.3666840).
- **Objective smells** (Long Parameter List, both languages; Long Method, Python):
  independent parser rules (> 7 parameters; SLOC > 50) — not SonarQube, to keep
  the baseline comparison fair.
- **Subjective Python smells** (God Class, Data Class, Feature Envy):
  two independent annotators under an intersection rule (Cohen's κ = 0.971,
  603 candidates). See `annotation/`.

## Reproducing the results

1. `pip install -r requirements.txt`
2. Zip the `java/` and `python/` folders together with `ground_truth.csv` into
   `dataset.zip`.
3. Run the notebooks in `notebooks/` (Colab, T4 GPU). Each writes its predictions
   to the matching file in `predictions/`.
4. Aggregate metrics (Macro-F1, MCC, accuracy), McNemar/Cochran's Q tests, and
   bootstrap intervals are computed from the paired files in `predictions/`.

Key figures (reproducible): Macro-F1 — UniXcoder 0.809, Qwen2.5-Coder 0.532
(v3 balanced, the final prompt), SonarQube 0.353. UniXcoder timing: mean 20.6 ms
/ file. Qwen parsing-failure rate: 0%.

## License

Released under **Creative Commons Attribution 4.0 International (CC BY 4.0)** —
see `LICENSE`. You may share and adapt the material with attribution. The MLCQ-
derived Java samples retain their original terms; cite MLCQ when using them.

## Citation

See `CITATION.cff`, or cite the ICOT 2026 paper above.
