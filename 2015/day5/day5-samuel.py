#!/usr/bin/python3

import re

data = open("input", "r")
lines = [line for line in data]

nice = 0

for line in lines:
  if sum([line.count(c) for c in "aeiou"]) < 3:
    continue
  for i in range(len(line) - 1):
    if line[i] == line[i+1]:
      break
  else:
    continue
  if any([line.find(s) != -1 for s in ["ab", "cd", "pq", "xy"]]):
    continue
  nice += 1

print(nice)

nice = 0

for line in lines:
  for i in range(len(line) - 3):
    if line.find(line[i:i+2], i+2) != -1:
      break
  else:
    continue
  for i in range(len(line) - 2):
    if line[i] == line[i+2]:
      break
  else:
    continue
  nice += 1

print(nice)

