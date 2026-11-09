#!/usr/bin/env python3
"""Inter-rater reliability for the subjective Python smell annotation.

Reproduces the paper's figure: Cohen's kappa = 0.971, 99.2% agreement,
5 disagreements, over 603 candidates. Standalone — no project config needed.

Rule (consistent with the intersection labeling used to build the dataset):
a unit enters the dataset only if BOTH annotators call it a smell, so
reliability is measured on the BINARY decision "smell vs not-smell"; a
`skip` or `tidak` counts as "not-smell" (no smell asserted).

Usage:
    python compute_kappa.py                                  # defaults below
    python compute_kappa.py annotations_1.csv annotations_2.csv

Each CSV needs columns: sample_id, proposed_label, final_label.
Writes: results/metrics/kappa_table.csv, results/metrics/kappa_disagreements.csv
"""
import sys
from pathlib import Path
import pandas as pd
from sklearn.metrics import cohen_kappa_score

SMELLS = {"god_class", "data_class", "feature_envy"}
OUT_DIR = Path("results/metrics")

def landis_koch(k):
    if k < 0.0:  return "Poor"
    if k <= 0.20: return "Slight"
    if k <= 0.40: return "Fair"
    if k <= 0.60: return "Moderate"
    if k <= 0.80: return "Substantial"
    return "Almost perfect"

f1 = sys.argv[1] if len(sys.argv) > 1 else "annotations_1.csv"
f2 = sys.argv[2] if len(sys.argv) > 2 else "annotations_2.csv"

def load(path, who):
    d = pd.read_csv(path)[["sample_id", "proposed_label", "final_label"]]
    d["smell"] = d["final_label"].isin(SMELLS).astype(int)
    return d.rename(columns={"smell": who, "proposed_label": f"pl_{who}"})

a = load(f1, "a1"); b = load(f2, "a2")
m = a.merge(b[["sample_id", "a2"]], on="sample_id")
pl = a.set_index("sample_id")["pl_a1"]

rows = []
k = cohen_kappa_score(m["a1"], m["a2"]); agree = (m["a1"] == m["a2"]).mean()
rows.append({"scope": "overall", "n": len(m),
             "pct_agreement": round(agree * 100, 1),
             "cohen_kappa": round(k, 3), "interpretation": landis_koch(k)})
for cls in sorted(SMELLS):
    idx = pl[pl == cls].index
    sub = m[m["sample_id"].isin(idx)]
    if len(sub) == 0:
        continue
    kk = cohen_kappa_score(sub["a1"], sub["a2"]) if sub["a1"].nunique() > 1 and sub["a2"].nunique() > 1 else 1.0
    rows.append({"scope": cls, "n": len(sub),
                 "pct_agreement": round((sub["a1"] == sub["a2"]).mean() * 100, 1),
                 "cohen_kappa": round(kk, 3), "interpretation": landis_koch(kk)})

table = pd.DataFrame(rows)
dis = m[m["a1"] != m["a2"]].merge(
    pd.read_csv(f1)[["sample_id", "final_label"]].rename(columns={"final_label": "annot_1"}), on="sample_id"
).merge(
    pd.read_csv(f2)[["sample_id", "final_label"]].rename(columns={"final_label": "annot_2"}), on="sample_id"
)[["sample_id", "annot_1", "annot_2"]]

print(table.to_string(index=False))
print(f"\nDisagreements ({len(dis)}):")
print(dis.to_string(index=False))

OUT_DIR.mkdir(parents=True, exist_ok=True)
table.to_csv(OUT_DIR / "kappa_table.csv", index=False)
dis.to_csv(OUT_DIR / "kappa_disagreements.csv", index=False)
print(f"\nSaved: {OUT_DIR/'kappa_table.csv'}, {OUT_DIR/'kappa_disagreements.csv'}")
