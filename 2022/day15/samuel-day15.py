#!/usr/bin/python3

import re

data = open("input", "r")

lines = [line.strip() for line in data]

pattern = re.compile("x=(-?\d+), y=(-?\d+):.*?x=(-?\d+), y=(-?\d+)")

info = []
for line in lines:
  m = pattern.search(line)
  info.append(list(map(int, m.groups())))

sensors = [(x1, y1, abs(x1 - x2) + abs(y1 - y2)) for x1, y1, x2, y2 in info]
beacons = set([(x2, y2) for x1, y1, x2, y2 in info])

coverage = set()
ty = 2000000
for x, y, d in sensors:
  if abs(y - ty) > d:
    continue
  dw = d - abs(y - ty)
  for td in range(-dw, dw+1):
    coverage.add(x + td)

count = len([x for x in coverage if (x, ty) not in beacons])

print(count)

for y in range(0, 4000000):
  row = []
  for sx, sy, sd in sensors:
    if abs(sy - y) > sd:
      continue
    dw = sd - abs(sy - y)
    row.append((sx - dw, sx + dw))
  row.sort()
  x = 0
  for x1, x2 in row:
    if x1 > x + 1:
      print((x + 1) * 4000000 + y)
    if x2 > x:
      x = x2
  
