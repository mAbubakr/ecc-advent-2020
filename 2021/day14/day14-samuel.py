#!/usr/bin/python3

from collections import Counter

file = open("input", "r")
s = file.readline().strip()
file.readline()

insmap = {}
for line in file:
  src, ins = line.strip().split(" -> ")
  insmap[src] = ins

c = Counter([s[i:i+2] for i in range(len(s) - 1)])

def printResult():
  c2 = Counter()
  for k, v in c.items():
    c2[k[0]] += v
  c2[s[-1]] += 1
  l = [(v, k) for k, v in c2.items()]
  l.sort()
  print(l[-1][0] - l[0][0])

for i in range(40):
  nc = Counter()
  for k, v in c.items():
    ins = insmap[k]
    nc[k[0] + ins] += v
    nc[ins + k[1]] += v
  c = nc
  if i == 9:
    printResult()

printResult()

