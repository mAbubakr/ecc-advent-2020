#!/usr/bin/python3

data = open("input", "r").read()

x = 0
y = 0

houses = set()
houses.add((0, 0))

for c in data:
  if c == "<":
    x -= 1
  elif c == ">":
    x += 1
  elif c == "^":
    y += 1
  elif c == "v":
    y -= 1
  pos = (x, y)
  houses.add(pos)

print(len(houses))

x1 = 0
y1 = 0
x2 = 0
y2 = 0

houses = set()
houses.add((0, 0))

toggle = False
for c in data:
  if c == "<":
    if toggle:
      x1 -= 1
    else:
      x2 -= 1
  elif c == ">":
    if toggle:
      x1 += 1
    else:
      x2 += 1
  elif c == "^":
    if toggle:
      y1 += 1
    else:
      y2 += 1
  elif c == "v":
    if toggle:
      y1 -= 1
    else:
      y2 -= 1
  if toggle:
    pos = (x1, y1)
  else:
    pos = (x2, y2)
  houses.add(pos)
  toggle = not toggle

print(len(houses))

