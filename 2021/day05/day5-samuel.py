#!/usr/bin/python3

import itertools

infile = open("input", "r")

lines = []
for line in infile:
  p1, p2 = line.split(" -> ")
  x0, y0 = [int(n) for n in p1.split(",")]
  x1, y1 = [int(n) for n in p2.split(",")]
  lines.append((x0, y0, x1, y1))

grid = {}

for x0, y0, x1, y1 in lines:
  if x0 != x1 and y0 != y1:
    continue

  if x0 != x1:
    if x1 < x0:
      x0, x1 = x1, x0
    for x in range(x0, x1 + 1):
      p = (x, y0)
      if not p in grid:
        grid[p] = 0
      grid[p] += 1
  else:
    if y0 > y1:
      y0, y1 = y1, y0
    for y in range(y0, y1 + 1):
      p = (x0, y)
      if not p in grid:
        grid[p] = 0
      grid[p] += 1

count = len([1 for p,n in grid.items() if n > 1])

print(count)

grid = {}

for x0, y0, x1, y1 in lines:
  if x0 < x1:
    xinc = 1
  elif x0 > x1:
    xinc = -1
  else:
    xinc = 0
  if y0 < y1:
    yinc = 1
  elif y0 > y1:
    yinc = -1
  else:
    yinc = 0

  x = x0
  y = y0

  for i in range(max(abs(x0 - x1), abs(y0 - y1)) + 1):
    p = (x, y)
    if not p in grid:
      grid[p] = 0
    grid[p] += 1
    x += xinc
    y += yinc

count = len([1 for p,n in grid.items() if n > 1])

print(count)

