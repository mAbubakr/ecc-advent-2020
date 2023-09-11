#!/usr/bin/python3

import re
import itertools

linematch = re.compile("(.+) would (gain|lose) (\d+) happiness units by sitting next to (.+)\.")

data = open("ssinput", "r")

people = {}

for line in data:
  m = linematch.match(line)
  if not m:
    print(f"bad match: {line}")
  p1 = m[1]
  p2 = m[4]
  num = int(m[3])
  if p1 not in people:
    people[p1] = {}
  if m[2] == "gain":
    people[p1][p2] = num
  else:
    people[p1][p2] = -num

n = len(people)
happy = 0
for order in itertools.permutations(people.keys()):
  total = 0
  for i in range(len(order)):
    p1 = order[i]
    total += people[p1][order[(i + 1) % n]] + people[p1][order[(i + n - 1) % n]]
  if total > happy:
    happy = total

print(happy)

for k, v in people.items():
  v["me"] = 0
people["me"] = dict([(k, 0) for k in people.keys()])
n = len(people)
happy = 0
for order in itertools.permutations(people.keys()):
  total = 0
  for i in range(len(order)):
    p1 = order[i]
    total += people[p1][order[(i + 1) % n]] + people[p1][order[(i + n - 1) % n]]
  if total > happy:
    happy = total

print(happy)

