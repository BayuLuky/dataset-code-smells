# Notebooks (full training / inference code)

| File | Stage | Produces |
|---|---|---|
| `Tahap3_Qwen_Coder.ipynb` | Qwen2.5-Coder-7B (4-bit NF4), 4 prompt variants | `qwen_*_predictions.csv`, `qwen_justifications.csv`, `qwen_timing.csv` |
| `Tahap4_UniXcoder.ipynb` | UniXcoder fine-tuning, 5-fold CV | `unixcoder_predictions.csv` (with fold), `unixcoder_timing.csv` |

Model identifiers: `Qwen/Qwen2.5-Coder-7B-Instruct`, `microsoft/unixcoder-base`.
Qwen quantization: 4-bit NF4, double quant, fp16 compute (bitsandbytes).
UniXcoder: AutoModelForSequenceClassification (encoder mode + linear head),
512 tokens, 6 epochs/fold, LR 2e-5, batch 8, weighted cross-entropy, seed 42.
