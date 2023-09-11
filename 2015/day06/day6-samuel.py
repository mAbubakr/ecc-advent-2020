#!/usr/bin/python3

import re
import itertools

pattern = re.compile("(.+) (\d+),(\d+) through (\d+),(\d+)")

data = open("ssinput", "r")
lines = [line for line in data]

row = [False] * 1000
lights = [row[:] for i in range(1000)]

for line in lines:
  m = pattern.match(line)
  cmd = m[1]
  x1 = int(m[2])
  y1 = int(m[3])
  x2 = int(m[4])
  y2 = int(m[5])

  for x, y in itertools.product(range(x1, x2+1), range(y1, y2+1)):
    if cmd == "turn on":
      lights[y][x] = True
    elif cmd == "turn off":
      lights[y][x] = False
    elif cmd == "toggle":
      lights[y][x] = not lights[y][x]

print(list(itertools.chain(*lights)).count(True))

row = [0] * 1000
lights = [row[:] for i in range(1000)]

for line in lines:
  m = pattern.match(line)
  cmd = m[1]
  x1 = int(m[2])
  y1 = int(m[3])
  x2 = int(m[4])
  y2 = int(m[5])

  for x, y in itertools.product(range(x1, x2+1), range(y1, y2+1)):
    if cmd == "turn on":
      lights[y][x] += 1
    elif cmd == "turn off":
      lights[y][x] = max(lights[y][x] - 1, 0)
    elif cmd == "toggle":
      lights[y][x] += 2

print(sum((itertools.chain(*lights))))

