#!/usr/bin/python3

lines = [line.strip() for line in open("ssinput", "r")]

grid = {}
nums = []
gears = set()

for y, line in enumerate(lines):
  accum = 0
  for x, c in enumerate(line):
    if c == ".":
      if accum:
        nums.append(accum)
        accum = 0
      continue
    pos = x + y * 1j
    if c in "0123456789":
      grid[pos] = len(nums)
      accum = accum * 10 + int(c)
    else:
      if accum:
        nums.append(accum)
        accum = 0
      grid[pos] = -1
      if c == "*":
        gears.add(pos)
  if accum:
    nums.append(accum)
    accum = 0

height = len(lines)
width = len(lines[0])
found = set()

for y in range(height):
  for x in range(width):
    pos = x + y * 1j
    if not pos in grid:
      continue
    n = grid[pos]
    if n == -1:
      continue
    for d in [-1, -1-1j, -1j, 1-1j, 1, 1+1j, 1j, -1+1j]:
      dp = pos + d
      if dp in grid and grid[dp] == -1:
        found.add(n)
        break

total = sum([nums[i] for i in found])
print(total)

total = 0
for pos in gears:
  r = set()
  for d in [-1, -1-1j, -1j, 1-1j, 1, 1+1j, 1j, -1+1j]:
    dp = pos + d
    if dp in grid and grid[dp] >= 0:
      r.add(nums[grid[dp]])
  if len(r) == 2:
    r = list(r)
    total += r[0] * r[1]

print(total)

