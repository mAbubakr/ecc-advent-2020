#!/usr/bin/python3

data = open("ssinput", "r")

seeds = [int(n) for n in data.readline().split(": ")[1].split()]
data.readline()

maps = []
while data.readline():
  l = []
  while True:
    line = data.readline().strip()
    if not line:
      break
    nums = [int(n) for n in line.split()]
    l.append([nums[1], nums[2], nums[0]])
  l.sort()
  maps.append(l)

locs = []

def readMap(m, n):
  for s, l, d in m:
    if n >= s and n < s + l:
      return d + n - s
  return n

for seed in seeds:
  n = seed
  for m in maps:
    n = readMap(m, n)
  locs.append(n)

print(min(locs))

seedr = []
for i in range(0, len(seeds), 2):
  seedr.append((seeds[i], seeds[i+1]))

def rangeMap(m, rin):
  res = []
  while rin:
    rs, rl = rin.pop(0)
    for s, l, d in m:
      if rs < s:
        if rs + rl <= s:
          res.append((rs, rl))
          break
        diff = s - rs
        res.append((rs, diff))
        rs = s
        rl -= diff
      if rs < s + l:
        if rs + rl <= s + l:
          res.append((d + rs - s, rl))
          break
        diff = rs - s
        res.append((d + diff, l - diff))
        rs = s + l
        rl -= l - diff
    else:
      res.append((rs, rl))
  return res

for m in maps:
  seedr = rangeMap(m, seedr)

seedr.sort()
print(seedr[0][0])

