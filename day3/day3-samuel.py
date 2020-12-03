#!/usr/bin/python3

from functools import reduce

def countTrees(x, y):
  data = open("input", "r")
  pos = 0
  row = 0
  trees = 0
  for line in data:
    if row % y:
      row += 1
      continue
    line = line.strip()
    if line[pos % len(line)] == "#":
      trees += 1
    pos += x
    row += 1
  return trees

slopes = [(1, 1), (3, 1), (5, 1), (7, 1), (1, 2)]
results = [countTrees(x, y) for x, y in slopes]
print(results[1])

print(reduce(lambda x, y: x*y, results))

