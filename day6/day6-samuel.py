#!/usr/bin/python3

import re

data = open("input", "r").read()

sum1 = 0
sum2 = 0
for group in re.split("^$", data, flags=re.M):
  group = group.strip()
  total = None
  chars1 = set()
  for line in re.split("\n", group):
    chars2 = set()
    for c in line:
      if c in "abcdefghijklmnopqrstuvwxyz":
        chars1.add(c)
        chars2.add(c)
    if total is None:
      total = chars2
    else:
      total = total.intersection(chars2)
  sum1 += len(chars1)
  sum2 += len(total)

print(f"{sum1} {sum2}")

