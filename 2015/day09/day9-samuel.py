#!/usr/bin/python3

import itertools

data = open("ssinput", "r")

dists = {}
for line in data:
  cities, dist = line.split(" = ")
  dist = int(dist)
  c1, c2 = cities.split(" to ")
  if not c1 in dists:
    dists[c1] = {}
  if not c2 in dists:
    dists[c2] = {}
  dists[c1][c2] = dist
  dists[c2][c1] = dist

cities = dists.keys()
mindist = None
for route in itertools.permutations(cities):
  dist = 0
  for i in range(len(route) - 1):
    dist += dists[route[i]][route[i+1]]
  if mindist is None or dist < mindist:
    mindist = dist

print(mindist)

cities = dists.keys()
maxdist = None
for route in itertools.permutations(cities):
  dist = 0
  for i in range(len(route) - 1):
    dist += dists[route[i]][route[i+1]]
  if maxdist is None or dist > maxdist:
    maxdist = dist

print(maxdist)

