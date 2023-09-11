#!/usr/bin/python3

import re

linematch = re.compile("Sue (\d+): ([^:]+): (\d+), ([^:]+): (\d+), ([^:]+): (\d+)")

gift = {
"children": 3,
"cats": 7,
"samoyeds": 2,
"pomeranians": 3,
"akitas": 0,
"vizslas": 0,
"goldfish": 5,
"trees": 3,
"cars": 2,
"perfumes": 1
}

sues = {}

data = open("ssinput", "r")

for line in data:
  m = linematch.match(line)
  sues[int(m[1])] = {m[2]: int(m[3]), m[4]: int(m[5]), m[6]: int(m[7])}

for n, v in sues.items():
  for item, num in v.items():
    if gift[item] != num:
      break
  else:
    print(n)

for n, v in sues.items():
  for item, num in v.items():
    if item == "cat" or item == "trees":
      if gift[item] >= num:
        break
    elif item == "pomeranians" or item == "goldfish":
      if gift[item] <= num:
        break
    elif gift[item] != num:
      break
  else:
    print(n)

