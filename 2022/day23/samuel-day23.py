#!/usr/bin/python3

from collections import deque
from itertools import count

data = open("input", "r")

lines = [line.strip() for line in data]

elves = set()

for y, line in enumerate(lines):
  for x, c in enumerate(line):
    if c == "#":
      elves.add(x + y * 1j)

dirs = deque([(-1 - 1j, -1j, 1 - 1j), (-1 + 1j, 1j, 1 + 1j), (-1 - 1j, -1, -1 + 1j), (1 - 1j, 1, 1 + 1j)])
alldirs = [-1 + 1j, 1j, 1 + 1j, 1, -1 - 1j, -1j, 1 - 1j, -1]

def draw():
  xl = [e.real for e in elves]
  yl = [e.imag for e in elves]
  print(f"{len(elves)} elves")
  for y in range(int(min(yl)), int(max(yl)) + 1):
    s = ""
    for x in range(int(min(xl)), int(max(xl)) + 1):
      if x + y * 1j in elves:
        s += "#"
      else:
        s += "."
    print(s)
  print()

for r in count(1):
  #draw()
  moves = {}
  nelves = set()
  moved = False
  for elf in elves:
    for d in alldirs:
      if elf + d in elves:
        break
    else:
      nelves.add(elf)
      continue
    for d1, d2, d3 in dirs:
      if elf + d1 in elves or elf + d2 in elves or elf + d3 in elves:
        continue
      npos = elf + d2
      if npos in moves:
        nelves.add(elf)
        if moves[npos] is not None:
          nelves.add(moves[npos])
          moves[npos] = None
      else:
        moves[npos] = elf
      break
    else:
      nelves.add(elf)
  for npos, elf in moves.items():
    if elf is not None:
      moved = True
      nelves.add(npos)
  elves = nelves
  dirs.append(dirs.popleft())

  if r == 10:
    xl = [e.real for e in elves]
    yl = [e.imag for e in elves]
    print(int((max(xl) - min(xl) + 1) * (max(yl) - min(yl) + 1)) - len(elves))
  if not moved:
    print(r)
    break

