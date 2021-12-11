#!/usr/bin/python3

import itertools

lines = [[int(n) for n in line.strip()] for line in open("input", "r")]

count = 0

def update(x, y, first = False):
  global count
  if y < 0 or y > 9 or x < 0 or x > 9:
    return
  if lines[y][x] == 0:
    return
  if not first:
    lines[y][x] += 1
  if lines[y][x] > 9:
    count += 1
    lines[y][x] = 0
    for dy in [-1, 0, 1]:
      for dx in [-1, 0, 1]:
        update(x + dx, y + dy)

for i in itertools.count(1):
  lines = [[n + 1 for n in line] for line in lines]
  pcount = count
  for y in range(10):
    for x in range(10):
      update(x, y, True)
  if i == 100:
    print(count)
  if count - pcount == 100:
    print(i)
    break

