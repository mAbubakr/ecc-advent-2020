#!/usr/bin/python3

from itertools import combinations

data = open("ssinput", "r")

lines = [line.strip() for line in data]

grid = set()
for y, line in enumerate(lines):
  for x, c in enumerate(line):
    if c == "#":
      grid.add((x,y))

cols = set([n[0] for n in grid])
rows = set([n[1] for n in grid])

total = 0
for (x1, y1), (x2, y2) in combinations(grid, 2):
  if x1 > x2:
    x1, x2 = x2, x1
  if y1 > y2:
    y1, y2 = y2, y1
  dist = 0
  for x in range(x1, x2):
    if x in cols:
      dist += 1
    else:
      dist += 2
  for y in range(y1, y2):
    if y in rows:
      dist += 1
    else:
      dist += 2
  total += dist

print(total)

total = 0
for (x1, y1), (x2, y2) in combinations(grid, 2):
  if x1 > x2:
    x1, x2 = x2, x1
  if y1 > y2:
    y1, y2 = y2, y1
  dist = 0
  for x in range(x1, x2):
    if x in cols:
      dist += 1
    else:
      dist += 1000000
  for y in range(y1, y2):
    if y in rows:
      dist += 1
    else:
      dist += 1000000
  total += dist

print(total)

