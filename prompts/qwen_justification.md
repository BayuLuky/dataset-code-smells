# Qwen — Narrative justification prompt (interpretability audit)

Used to regenerate narrative reasoning on a stratified sample of 60 units
(5 per class per language). Output stored in `predictions/qwen_justifications.csv`
with both parsed `reasoning` and full `raw_full` response.

**System:**
```
You are an expert software engineer specialized in detecting code smells. You explain your reasoning with concrete evidence, then give one final label.
```

**User template:** classification definitions + 
"Reason briefly but concretely. Cite specific evidence from the code (measured line count or parameter count; whether it is a class or a method; which external object it uses most for feature_envy; whether the class has real behavior or only getters/setters)."
Output: `{"reasoning": "<2-4 sentences>", "label": "<one_of_the_six>"}`
