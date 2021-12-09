#!/usr/bin/python3

rows = []
for line in open("input", "r"):
  rows.append([int(n) for n in line.strip()])

lows = set()
w, h = len(rows[0]), len(rows)
for y in range(h):
  for x in range(w):
    n = rows[y][x]
    if y and rows[y-1][x] <= n:
      continue
    if x and rows[y][x-1] <= n:
      continue
    if y < h-1 and rows[y+1][x] <= n:
      continue
    if x < w-1 and rows[y][x+1] <= n:
      continue
    lows.add((x, y))

print(sum([rows[y][x] + 1 for x, y in lows]))

basins = []

for x, y in lows:
  basin = set()
  q = set([(x, y)])
  while q:
    for p in list(q):
      q.remove(p)
      if p in basin or p in q:
        continue
      basin.add(p)
      x, y = p
      if y and rows[y-1][x] != 9:
        q.add((x, y-1))
      if x and rows[y][x-1] != 9:
        q.add((x-1, y))
      if y < h-1 and rows[y+1][x] != 9:
        q.add((x, y+1))
      if x < w-1 and rows[y][x+1] != 9:
        q.add((x+1, y))
  basins.append(len(basin))

basins.sort()
print(basins[-1] * basins[-2] * basins[-3])

