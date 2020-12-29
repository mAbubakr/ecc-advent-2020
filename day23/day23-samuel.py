#!/usr/bin/python3

import re
import collections
import itertools

from collections import Counter
from itertools import permutations, count, repeat, chain, islice, starmap, product
from math import prod

data = [int(n) for n in "326519478"]

for i in range(100):
  n = data[0]
  m = data[1:4]
  data = data[4:]
  t = n-1
  while True:
    if t < 1:
      t = 9
    if t not in m:
      break
    t -= 1
  p = data.index(t)
  data[p+1:p+1] = m
  data.append(n)

p = data.index(1)
print("".join([str(n) for n in data[p+1:]+data[:p]]))

start = [int(n) for n in "326519478"]
start.append(10)
data = list(range(1, 1000002))
data[0] = 0
data[1000000] = 0
for i in range(0, len(start) - 1):
  data[start[i]] = start[i+1]

n = start[0]
end = 1000000
for i in range(10000000):
  n1 = data[n]
  n2 = data[n1]
  n3 = data[n2]
  t = n - 1
  while True:
    if t < 1:
      t = 1000000
    if t != n1 and t != n2 and t!= n3:
      break
    t -= 1
  if t == end:
    end = n3
  tn, n, data[n3], data[t] = n, data[n3], data[t], n1
  data[end] = tn
  data[tn] = 0
  end = tn

n1 = data[1]
if n1 == 0:
  n1 = n
n2 = data[n1]
if n2 == 0:
  n2 = n
print(n1 * n2)

