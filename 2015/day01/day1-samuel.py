#!/usr/bin/python3

import itertools

data = open("ssinput", "r").read()

print(data.count("(") - data.count(")"))

n = 0
for i, c in zip(itertools.count(1), data):
  if c == "(":
    n += 1
  else:
    n -= 1
  if n == -1:
    print(i)
    break

