#!/usr/bin/python3

import re

data = open("input", "r")

splitter = re.compile("(\d+)-(\d+) (.): (.+)")
valid1 = 0
valid2 = 0
for line in data:
  res = splitter.fullmatch(line.strip())
  n1 = int(res[1]) 
  n2 = int(res[2]) 
  n = res[4].count(res[3])
  if n >= n1 and n <= n2:
    valid1 += 1
  if (res[4][n1-1] == res[3]) != (res[4][n2-1] == res[3]):
    valid2 += 1

print(f"{valid1} {valid2}")

