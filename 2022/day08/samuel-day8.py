#!/usr/bin/python3

import itertools

data = open("input", "r")

lines = [line.strip() for line in data]

trees = [[[int(c), False] for c in line] for line in lines]

xs = len(trees[0])
ys = len(trees)

for y in range(ys):
  h = -1
  for x in range(xs):
    if h < trees[y][x][0]:
      trees[y][x][1] = True
      h = trees[y][x][0]
  h = -1
  for x in reversed(range(xs)):
    if h < trees[y][x][0]:
      trees[y][x][1] = True
      h = trees[y][x][0]

for x in range(xs):
  h = -1
  for y in range(ys):
    if h < trees[y][x][0]:
      trees[y][x][1] = True
      h = trees[y][x][0]
  h = -1
  for y in reversed(range(ys)):
    if h < trees[y][x][0]:
      trees[y][x][1] = True
      h = trees[y][x][0]

print(len([v for h, v in itertools.chain(*trees) if v]))

def scan(x, y):
  total = 1
  h = trees[y][x][0]
  count = 0
  tx = x - 1
  while tx >= 0:
    count += 1
    if trees[y][tx][0] >= h:
      break
    tx -= 1
  total *= count
  count = 0
  tx = x + 1
  while tx < xs:
    count += 1
    if trees[y][tx][0] >= h:
      break
    tx += 1
  total *= count
  count = 0
  ty = y - 1
  while ty >= 0:
    count += 1
    if trees[ty][x][0] >= h:
      break
    ty -= 1
  total *= count
  count = 0
  ty = y + 1
  while ty < ys:
    count += 1
    if trees[ty][x][0] >= h:
      break
    ty += 1
  total *= count
  return total

views = [scan(x, y) for y in range(ys) for x in range(xs)]
print(max(views))

