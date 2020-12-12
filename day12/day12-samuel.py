#!/usr/bin/python3

dirs = []
data = open("input", "r")
for line in data:
  dirs.append((line[0], int(line[1:])))

deltas = [(0, 1), (1, 0), (0, -1), (-1, 0)]
x = 0
y = 0
delt = 1

for d, l in dirs:
  if d == "F":
    x += deltas[delt][0] * l
    y += deltas[delt][1] * l
  elif d == "N":
    y += l
  elif d == "S":
    y -= l
  elif d == "W":
    x -= l
  elif d == "E":
    x += l
  elif d == "L":
    delt -= l // 90
    while delt < 0:
      delt += 4
  elif d == "R":
    delt += l // 90
    while delt > 3:
      delt -= 4

print(abs(x) + abs(y))

x = 0
y = 0
dx = 10
dy = 1

for d, l in dirs:
  if d == "F":
    x += dx * l
    y += dy * l
  elif d == "N":
    dy += l
  elif d == "S":
    dy -= l
  elif d == "W":
    dx -= l
  elif d == "E":
    dx += l
  elif d == "L":
    if l == 90:
      dx, dy = -dy, dx
    elif l == 180:
      dx, dy = -dx, -dy
    else:
      dx, dy = dy, -dx
  elif d == "R":
    if l == 90:
      dx, dy = dy, -dx
    elif l == 180:
      dx, dy = -dx, -dy
    else:
      dx, dy = -dy, dx

print(abs(x) + abs(y))

