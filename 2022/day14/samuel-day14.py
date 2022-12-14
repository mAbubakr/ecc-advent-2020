#!/usr/bin/python3

data = open("input", "r")

lines = [line.strip() for line in data]

stuff = set()

for line in lines:
  points = [(int(n1), int(n2)) for n1, n2 in [e.split(",") for e in line.split(" -> ")]]
  x, y = points[0]
  stuff.add((x, y))
  for dx, dy in points[1:]:
    while True:
      if x < dx:
        x += 1
      elif x > dx:
        x -= 1
      elif y < dy:
        y += 1
      elif y > dy:
        y -= 1
      else:
        break
      stuff.add((x, y))

orig = set(stuff)

l = [x for x, y in stuff]
minx = min(l)
maxx = max(l)
l = [y for x, y in stuff]
maxy = max(l)

done = False
count = 0

while not done:
  sx = 500
  sy = 0
  while not done:
    if not (sx, sy+1) in stuff:
      sy += 1
      if sy > maxy:
        done = True
      continue
    if not (sx-1, sy+1) in stuff:
      sx -= 1
      if sx < minx:
        done = True
      continue
    if not (sx+1, sy+1) in stuff:
      sx += 1
      if sx > maxx:
        done = True
      continue
    if sy == 0:
      done = True
    count += 1
    stuff.add((sx, sy))
    break

print(count)

stuff = orig
done = False
count = 0
maxy += 2

while not done:
  sx = 500
  sy = 0
  while not done:
    if sy + 1 < maxy:
      if not (sx, sy+1) in stuff:
        sy += 1
        continue
      if not (sx-1, sy+1) in stuff:
        sx -= 1
        continue
      if not (sx+1, sy+1) in stuff:
        sx += 1
        continue
    if sy == 0:
      done = True
    count += 1
    stuff.add((sx, sy))
    break

print(count)


