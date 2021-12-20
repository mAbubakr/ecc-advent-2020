#!/usr/bin/python3

file = open("input", "r")
enhance = [c == "#" for c in file.readline().strip()]
file.readline()
image = [[c == "#" for c in line.strip()] for line in file]
points = set()
for y in range(len(image)):
  line = image[y]
  for x in range(len(line)):
    if line[x]:
      points.add((x, y))

def draw():
  for y in range(miny, maxy + 1):
    s = ""
    for x in range(minx, maxx + 1):
      if (x, y) in points:
        s += "#"
      else:
        s += "."
    print(s)
  print()

minx = min([x for x, y in points])
maxx = max([x for x, y in points])
miny = min([y for x, y in points])
maxy = max([y for x, y in points])

#draw()

for i in range(50):
  newp = set()
  for x in range(minx - 2, maxx + 3):
    for y in range(miny - 2, maxy + 3):
      s = ""
      for dy in range(-1, 2):
        for dx in range(-1, 2):
          tx, ty = x + dx, y + dy
          if tx < minx or tx > maxx or ty < miny or ty > maxy:
            if i & 1:
              s += "1"
            else:
              s += "0"
          elif (x+dx, y+dy) in points:
            s += "1"
          else:
            s += "0"
      if enhance[int(s, 2)]:
        newp.add((x, y))
  minx -= 2
  maxx += 2
  miny -= 2
  maxy += 2
  points = newp
  if i == 1:
    print(len(points))
  #draw()

print(len(points))

