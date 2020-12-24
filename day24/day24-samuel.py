#!/usr/bin/python3

data = open("input", "r")

lines = [line.strip() for line in data]

tiles = {}
for line in lines:
  north = False
  south = False
  x = 0
  y = 0
  for c in line:
    if c == "e":
      if south:
        y -=1
        x += 1
        south = False
      elif north:
        y += 1
        x += 1
        north = False
      else:
        x += 2
    elif c == "w":
      if south:
        y -= 1
        x -= 1
        south = False
      elif north:
        y += 1
        x -= 1
        north = False
      else:
        x -= 2
    elif c == "s":
      south = True
    elif c == "n":
      north = True
  pos = (x, y)
  if pos not in tiles:
    tiles[pos] = False
  tiles[pos] = not tiles[pos]

total = 0
for v in tiles.values():
  if v:
    total += 1
print(total)

x1 = min([p[0] for p in tiles.keys()])
x2 = max([p[0] for p in tiles.keys()])
y1 = min([p[1] for p in tiles.keys()])
y2 = max([p[1] for p in tiles.keys()])

def ncount(x, y):
  total = 0
  if (x-2, y) in tiles and tiles[(x-2, y)]:
    total += 1
  if (x+2, y) in tiles and tiles[(x+2, y)]:
    total += 1
  if (x-1, y-1) in tiles and tiles[(x-1, y-1)]:
    total += 1
  if (x-1, y+1) in tiles and tiles[(x-1, y+1)]:
    total += 1
  if (x+1, y-1) in tiles and tiles[(x+1, y-1)]:
    total += 1
  if (x+1, y+1) in tiles and tiles[(x+1, y+1)]:
    total += 1
  return total

for i in range(100):
  ntiles = {}
  x1 -= 2
  x2 += 2
  y1 -= 1
  y2 += 1
  for x in range(x1, x2+1):
    for y in range(y1, y2+1):
      if x%2 != y%2:
        continue
      pos = (x, y)
      count = ncount(x, y)
      if pos in tiles and tiles[pos]:
        if count == 0 or count > 2:
          ntiles[pos] = False
        else:
          ntiles[pos] = True
      else:
        if count == 2:
          ntiles[pos] = True
  tiles = ntiles

total = 0
for v in tiles.values():
  if v:
    total += 1
print(total)

