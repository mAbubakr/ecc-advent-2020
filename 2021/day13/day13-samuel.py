#!/usr/bin/python3

dots = set()
file = open("input", "r")
while True:
  line = file.readline().strip()
  if not line:
    break
  x, y = [int(n) for n in line.split(",")]
  dots.add((x, y))

def xfold(n):
  for p in list(dots):
    x, y = p
    if x > n:
      dots.remove(p)
      dots.add((2 * n - x, y))

def yfold(n):
  for p in list(dots):
    x, y = p
    if y > n:
      dots.remove(p)
      dots.add((x, 2 * n - y))

first = True
for line in file:
  axis, n = line.split(" ")[2].split("=")
  n = int(n)
  if axis == "x":
    xfold(n)
  else:
    yfold(n)
  if first:
    print(len(dots))
    first = False

xmax = max([x for x, y in dots]) + 1
ymax = max([y for x, y in dots]) + 1

output = [[" "] * xmax for y in range(ymax)]

for x, y in dots:
  output[y][x] = "X"

for line in output:
  print("".join(line))

