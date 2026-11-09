# Qwen variant v1 — Zero-shot  (Macro-F1 0.367)

**System:**
```
You are an expert software engineer specialized in detecting code smells. You classify a single code unit into exactly ONE category and reply with strict JSON only.
```

**User template:**
```
<DEFINISI — see _shared_definition.md>

Language: {language}
Code unit:
```{language}
{code_text}
```
Now reply with JSON only, e.g. {"label": "long_method"}.
```
Decoding: greedy. Output parsed from the last valid JSON object.
