#!/usr/bin/python3

data = open("ssinput", "r")

lines = [line.strip() for line in data]

grid = {}
pipes = set()
for y, line in enumerate(lines):
  for x, c in enumerate(line):
    pos = x + y * 1j
    if c == "S":
      start = pos
    elif c != ".":
      grid[pos] = c

cdirs = {"|": [-1j, 1j], "-": [-1, 1], "J": [-1j, -1], "F": [1, 1j], "7": [-1, 1j], "L": [-1j, 1]}

pos = start
dirs = []
for d in [1, 1j, -1, -1j]:
  dp = pos + d
  if dp in grid and -d in cdirs[grid[dp]]:
    dirs.append(d)

if not dirs in cdirs.values():
  dirs = [dirs[1], dirs[0]]
for k, v in cdirs.items():
  if dirs == v:
    grid[pos] = k
    break

count = 0
d = dirs[0]
while True:
  count += 1
  pipes.add(pos)
  pos += d
  if pos == start:
    break
  dirs = cdirs[grid[pos]]
  if dirs[0] == -d:
    d = dirs[1]
  else:
    d = dirs[0]

print(count // 2)

count = 0
for y in range(len(lines)):
  s = ""
  out = True
  for x in range(len(lines[0])):
    pos = x + y * 1j
    if pos in pipes:
      s += "+"
      c = grid[pos]
      if c == "F":
        up = False
      elif c == "L":
        up = True
      elif c == "-":
        pass
      elif c == "|":
        out = not out
      elif c == "7":
        if up:
          out = not out
      elif c == "J":
        if not up:
          out = not out
    elif not out:
      s += "@"
      count += 1
    else:
      s += " "
  #print(s)

print(count)


