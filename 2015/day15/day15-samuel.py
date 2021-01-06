#!/usr/bin/python3

import re
from math import prod

linematch = re.compile("([^:]+): capacity (-?\d+), durability (-?\d+), flavor (-?\d+), texture (-?\d+), calories (-?\d+)")

data = open("input", "r")
lines = [line.strip() for line in data]

ingred = []
cals = []
for line in lines:
  m = linematch.match(line)
  ingred.append([int(m[n]) for n in range(2, 6)])
  cals.append(int(m[6]))

best = 0
bestcal = 0

for i0 in range(0, 101):
  s0 = [i0 * iv for iv in ingred[0]]
  for i1 in range(0, 100 - i0 + 1):
    s1 = [i1 * ingred[1][i] + s0[i] for i in range(4)]
    for i2 in range(0, 100 - i0 - i1 + 1):
      i3 = 100 - i0 - i1 - i2
      s2 = [i2 * ingred[2][i] + i3 * ingred[3][i] + s1[i] for i in range(4)]
      total = prod(s2)
      if total > best and all([v > 0 for v in s2]):
        best = total
      if total > bestcal and all([v > 0 for v in s2]) and (i0 * cals[0] + i1 * cals[1] + i2 * cals[2] + i3 * cals[3] == 500):
        bestcal = total

print(best)
print(bestcal)

