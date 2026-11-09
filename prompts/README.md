# Prompts

Exact prompts used for Qwen2.5-Coder-7B-Instruct. Each file contains the
`system` message and the `user` template (the code unit is inserted where
marked). All four classification variants were swept on the identical 463-file
evaluation set; **v3 (balanced) is the final configuration** reported in the paper.

| File | Variant | Macro-F1 |
|---|---|---|
| `qwen_v1_zero_shot.md` | Zero-shot (definitions + thresholds) | 0.367 |
| `qwen_v2_chain_of_thought.md` | Chain-of-Thought | 0.438 |
| `qwen_v3_balanced.md` | Balanced (**final**) | **0.532** |
| `qwen_v4_few_shot.md` | Few-shot | 0.338 |
| `qwen_justification.md` | Narrative justification (interpretability audit, 60 samples) | — |

Shared definition block (used by all variants): see the top of each file.
Decoding: greedy (`do_sample=False`) for reproducibility.
