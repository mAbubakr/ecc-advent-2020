#!/usr/bin/python3

from itertools import chain

data = open("ssinput", "r")

lines = [line.strip() for line in data]

grid = [[c == "#" for c in line] for line in lines]

def count(x, y):
  total = 0
  if x > 0:
    if y > 0 and grid[y-1][x-1]:
      total += 1
    if grid[y][x-1]:
      total += 1
    if y < len(grid) - 1 and grid[y+1][x-1]:
      total += 1
  if y > 0 and grid[y-1][x]:
    total += 1
  if y < len(grid) - 1 and grid[y+1][x]:
    total += 1
  if x < len(grid[0]) - 1:
    if y > 0 and grid[y-1][x+1]:
      total += 1
    if grid[y][x+1]:
      total += 1
    if y < len(grid) - 1 and grid[y+1][x+1]:
      total += 1
  return total

for i in range(100):
  ngrid = [l[:] for l in grid]
  for y in range(len(grid)):
    for x in range(len(grid[0])):
      c = count(x, y)
      if grid[y][x]:
        ngrid[y][x] = c == 2 or c == 3
      else:
        ngrid[y][x] = c == 3
  grid = ngrid

print(list(chain(*grid)).count(True))

grid = [[c == "#" for c in line] for line in lines]
grid[0][0] = True
grid[len(grid) - 1][0] = True
grid[0][len(grid[0]) - 1] = True
grid[len(grid) - 1][len(grid[0]) - 1] = True

for i in range(100):
  ngrid = [l[:] for l in grid]
  for y in range(len(grid)):
    for x in range(len(grid[0])):
      if x == 0 and (y == 0 or y == len(grid) - 1):
        continue
      if x == len(grid[0]) - 1 and (y == 0 or y == len(grid) - 1):
        continue
      c = count(x, y)
      if grid[y][x]:
        ngrid[y][x] = c == 2 or c == 3
      else:
        ngrid[y][x] = c == 3
  grid = ngrid

print(list(chain(*grid)).count(True))

