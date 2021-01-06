#!/usr/bin/python3

import re
import itertools

linematch = re.compile("(.+) can fly (\d+) km/s for (\d+) seconds, but then must rest for (\d+) seconds\.")

data = open("input", "r")

deer = []

for line in data:
  m = linematch.match(line)
  deer.append([int(m[2]), int(m[3]), int(m[4]), int(m[3]) + int(m[4]), 0])

def calc(time):
  dist = []
  for s, t1, t2, tt, p in deer:
    full, part = divmod(time, tt)
    d = full * s * t1 + s * min(part, t1)
    dist.append(d)
  return dist

print(max(calc(2503)))

for time in range(1, 2504):
  dist = calc(time)
  m = max(dist)
  for i in range(len(deer)):
    if dist[i] == m:
      deer[i][4] += 1

print(max([d[4] for d in deer]))

