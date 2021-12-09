#!/usr/bin/python3

g = ""
e = ""

counts = None

lines = [line.strip() for line in open("input", "r")]

def search(lines, n):
  count = 0
  for line in lines:
    if line[n] == "1":
      count += 1
  return count

width = len(lines[0])
counts = [0] * width
for i in range(width):
  c = search(lines, i)
  if c > len(lines) / 2:
    g += "1"
    e += "0"
  else:
    g += "0"
    e += "1"

g = int(g, 2)
e = int(e, 2)

print(g * e)

tlines = lines[:]

for i in range(width):
  if len(tlines) == 1:
    break
  c = search(tlines, i)
  if c >= len(tlines) / 2:
    t = "1"
  else:
    t = "0"
  tlines = [line for line in tlines if line[i] == t]

o = tlines[0]

tlines = lines[:]

for i in range(width):
  if len(tlines) == 1:
    break
  c = search(tlines, i)
  if c >= len(tlines) / 2:
    t = "0"
  else:
    t = "1"
  tlines = [line for line in tlines if line[i] == t]

s = tlines[0]

print(int(o, 2) * int(s, 2))

