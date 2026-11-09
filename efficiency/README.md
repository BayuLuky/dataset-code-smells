# Efficiency (per-file inference time)

| File | Detector | Columns |
|---|---|---|
| `unixcoder_timing.csv` | UniXcoder | id, language, detector, time_ms |
| `qwen_timing.csv` | Qwen v3 | id, language, detector, time_ms |

Measured on a single NVIDIA T4 (Google Colab), batch size 1, model load and
warm-up excluded, generation capped at 64 new tokens for Qwen timing.
UniXcoder mean 20.6 ms / median 21.8 ms; Qwen mean ~2.3 s.
SonarQube scans the whole project at once (amortized ~289.7 ms/file) and is
reported separately in the paper.
