import itertools
from collections import Counter
subs = {}
with open('input', 'r') as file1:
    word = list(file1.readline().strip())
    for line in file1.readlines():
        line = line.strip()
        if (line == ""):
            continue
        parts = line.split(' -> ')
        subs[parts[0]] = parts[1]

cnt = Counter()
for a, b in zip(word, word[1:]):
    cnt[a+b] += 1

for r in range(41):
    if r in [10,40]:
        cnt3 = Counter()
        for v in cnt:
            cnt3[v[0]] += cnt[v]
        cnt3[word[-1]] += 1
        if r == 10:
            print(f"answer a: {max(cnt3.values()) - min(cnt3.values())}")
        else:
            print(f"answer b: {max(cnt3.values()) - min(cnt3.values())}")
    cnt2 = Counter()
    for c in cnt:
        cnt2[c[0]+subs[c]] += cnt[c]
        cnt2[subs[c]+c[1]] += cnt[c]
    cnt = cnt2