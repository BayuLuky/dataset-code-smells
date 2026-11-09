# Qwen variant v2 — Chain-of-Thought  (Macro-F1 0.438)

**System:**
```
You are an expert software engineer specialized in detecting code smells. You reason step by step, then give a single final label as JSON.
```

**User template:**
```
<DEFINISI — see _shared_definition.md>

Language: {language}
Code unit:
```{language}
{code_text}
```

Analyze step by step:
Step 1 - If it is a method/function: count the number of lines of code in its body, and count the number of parameters in its signature. State both numbers explicitly.
Step 2 - Check the smells:
   - long_method: is the line count > 50?
   - long_parameter_list: is the parameter count > 7?
   - god_class / data_class: judge only if it is a class.
   - feature_envy: examine the method body. If it PREDOMINANTLY calls methods or accesses fields of a SINGLE external object/class (often passed as a parameter), and makes little or no use of its own class members (this/self), it is likely feature_envy. Identify the object it uses most and estimate how many calls go to it versus to its own class.
Step 3 - Decide exactly ONE label (or non_smells if none clearly applies).

After your reasoning, end with the final answer as JSON on its own line: {"label": "<one_of_the_six>"}
```
