#!/usr/bin/python3

from math import prod

data = open("ssinput", "r")

sizes = [int(line) for line in data]
sizes.sort()
total = sum(sizes)

def search(sizes, collect, accum):
  if not sizes:
    return
  mine = sizes[0]
  rest = sizes[1:]
  if accum + mine > target:
    return
  extend = collect + [mine]
  if accum + mine == target:
    sets.append(extend)
    return
  search(rest, extend, accum + mine)
  search(rest, collect, accum)

target = total // 3
sets = []

search(sizes, [], 0)

qe = [(len(s), prod(s)) for s in sets]
qe.sort()
print(qe[0][1])

target = total // 4
sets = []

search(sizes, [], 0)

qe = [(len(s), prod(s)) for s in sets]
qe.sort()
print(qe[0][1])

