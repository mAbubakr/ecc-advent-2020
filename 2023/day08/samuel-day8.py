#!/usr/bin/python3

import re

from itertools import cycle
from math import prod, sqrt

data = open("ssinput", "r")

dirs = data.readline().strip()
data.readline()

p = re.compile(r"(...) = \((...), (...)\)")
nodes = {}
for line in data:
  m = p.match(line.strip())
  nodes[m[1]] = (m[2], m[3])

count = 0
pos = "AAA"
d = cycle(dirs)
while pos != "ZZZ":
  count += 1
  if next(d) == "L":
    pos = nodes[pos][0]
  else:
    pos = nodes[pos][1]

print(count)

pos = [k for k in nodes if k[-1] == "A"]
d = cycle(dirs)
counts = []
for p in pos:
  count = 0
  while p[-1] != "Z":
    count += 1
    if next(d) == "L":
      p = nodes[p][0]
    else:
      p = nodes[p][1]
  counts.append(count)

def factor(n):
  for i in range(2, int(sqrt(n))):
    if n % i == 0:
      return (i, n // i)
  return None

#print([factor(n) for n in counts])

print(263 * prod([c // 263 for c in counts]))

