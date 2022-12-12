#!/usr/bin/python3

data = open("input", "r")

lines = [line.strip() for line in data]

grid = []
start = None
end = None

for line in lines:
  row = list(line)
  if "S" in row:
    pos = row.index("S")
    start = (pos, len(grid))
    row[pos] = "a"
  if "E" in row:
    pos = row.index("E")
    end = (pos, len(grid))
    row[pos] = "z"
  grid.append([ord(c) - ord('a') for c in row])

sx = len(grid[0])
sy = len(grid)

def shortest(start, end):
  dist = 0
  tocheck = [start]
  dmap = {}

  while tocheck:
    checking = tocheck
    tocheck = []
    for pos in checking:
      if pos in dmap:
        continue
      dmap[pos] = dist
      x, y = pos
      h = grid[y][x]
      if x > 0 and h - grid[y][x-1] >= -1:
        tocheck.append((x-1, y))
      if x < sx - 1 and h - grid[y][x+1] >= -1:
        tocheck.append((x+1, y))
      if y > 0 and h - grid[y-1][x] >= -1:
        tocheck.append((x, y-1))
      if y < sy - 1 and h - grid[y+1][x] >= -1:
        tocheck.append((x, y+1))
    dist += 1
  if end in dmap:
    return dmap[end]
  return None
  
print(shortest(start, end))

dists = []
for y in range(sy):
  for x in range(sx):
    if (grid[y][x] == 0):
      d = shortest((x, y), end)
      if d is not None:
        dists.append(d)

print(min(dists))

