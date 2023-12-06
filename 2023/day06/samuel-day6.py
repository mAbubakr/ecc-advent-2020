#!/usr/bin/python3

from math import sqrt, ceil, floor

data = open("ssinput", "r")

times = data.readline().split()[1:]
dists = data.readline().split()[1:]
time1 = int("".join(times))
dist1 = int("".join(dists))
times = [int(n) for n in times]
dists = [int(n) for n in dists]

def solve(t, d):
  det = sqrt(t * t - 4 * d)
  #print((-t + det) / -2)
  #print((-t - det) / -2)
  tl = (-t + det) / -2
  l = ceil(tl)
  if l == tl:
    l += 1
  th = (-t - det) / -2
  h = floor(th)
  if h == th:
    h -= 1
  return h - l + 1

total = 1

for t, d in zip(times, dists):
  count = solve(t, d)
  total *= count

print(total)

print(solve(time1, dist1))

