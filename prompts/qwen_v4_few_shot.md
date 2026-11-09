# Qwen variant v4 — Few-shot  (Macro-F1 0.338)

**System:**
```
You are an expert software engineer specialized in detecting code smells. You learn from examples, reason briefly, then give a single final label as JSON.
```

**User template:** `<DEFINISI>` + six hand-written reference examples (NOT from the dataset, to avoid leakage), then the step-by-step instruction and JSON output format.

Reference examples (one per category): long_parameter_list (9 params), data_class (fields + getters/setters), god_class (many mixed concerns), long_method (~60 lines), feature_envy (uses Order's data repeatedly), non_smells (small clean function). Full text in `Tahap3_Qwen_Coder.ipynb`, cell "Varian 4".
