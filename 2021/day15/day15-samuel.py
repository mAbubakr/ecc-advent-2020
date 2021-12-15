#!/usr/bin/python3

import itertools

grid = dict(itertools.chain(*[[((x, y), int(n)) for x, n in enumerate(line.strip())] for y, line in enumerate(open("input", "r"))]))

ow, oh = max(grid)
ow += 1
oh += 1
w = ow * 5
h = oh * 5

p = (0, 0)

risk = {p: 0}

tocheck = set()
tocheck.add(p)

def check(x, y, r):
  if x < 0 or y < 0 or x >= w or y >= h:
    return
  p = (x, y)
  p2 = (x % ow, y % oh)
  scale = x // ow + y // oh
  r += (grid[p2] + scale - 1) % 9 + 1
  if not p in risk:
    risk[p] = r
    tocheck.add(p)
  elif risk[p] > r:
    risk[p] = r
    tocheck.add(p)
  
while tocheck:
  tcheck = list(tocheck)
  tocheck = set()
  for p in tcheck:
    x, y = p
    r = risk[p]
    check(x-1, y, r)
    check(x+1, y, r)
    check(x, y-1, r)
    check(x, y+1, r)

print(risk[(ow-1, oh-1)])
print(risk[(w-1, h-1)])

