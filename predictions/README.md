# Predictions (paired, per-sample)

Per-sample predictions from all three detectors on the identical 463 units, so
that every metric and McNemar/bootstrap test in the paper can be reproduced.

| File | Detector | Key columns |
|---|---|---|
| `sonar_predictions.csv` | SonarQube (rule-based baseline) | `id, language, true_label, sonar_pred, rules_fired` |
| `unixcoder_predictions.csv` | UniXcoder (fine-tuned encoder) | `id, language, true_label, unix_pred, fold` |
| `qwen_predictions.csv` | Qwen v1 zero-shot | `id, language, true_label, qwen_pred, parsed, qwen_raw` |
| `qwen_cot_predictions.csv` | Qwen v2 chain-of-thought | (same) |
| `qwen_v3_predictions.csv` | Qwen v3 balanced (**final prompt**) | (same) |
| `qwen_v4_predictions.csv` | Qwen v4 few-shot | (same) |
| `qwen_justifications.csv` | Qwen interpretability audit (60 units) | `id, language, true_label, qwen_pred, correct, reasoning, raw_full` |

**Fold assignments** for the UniXcoder 5-fold cross-validation are the `fold`
column of `unixcoder_predictions.csv` (seed 42, StratifiedKFold, shuffle=True).

All files share the `id` key with `../ground_truth.csv`, so predictions can be
joined back to the ground-truth label and source path of each unit.
