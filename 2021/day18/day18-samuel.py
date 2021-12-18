#!/usr/bin/python3

from functools import reduce
from itertools import combinations
import math

def parse(line):
  res = []
  for c in line:
    if c == '[':
      res.append(-1)
    elif c == ']':
      res.append(-2)
    elif c == ',':
      continue
    else:
      res.append(int(c))
  return res

nums = [parse(line.strip()) for line in open("input", "r")]

def explode(n):
  level = 0
  for pos in range(len(n)):
    c = n[pos]
    if c == -1:
      level += 1
      continue
    if c == -2:
      level -= 1
      continue
    if level == 5:
      left = n[pos]
      right = n[pos + 1]
      break
  else:
    return False
  for rpos in range(pos + 3, len(n)):
    if n[rpos] >= 0:
      n[rpos] += right
      break
  for lpos in range(pos - 2, 0, -1):
    if n[lpos] >= 0:
      n[lpos] += left
      break
  n[pos-1] = 0
  del n[pos:pos+3]
  return True

def split(n):
  for pos in range(len(n)):
    if n[pos] > 9:
      n[pos:pos+1] = [-1, math.floor(n[pos] / 2), math.ceil(n[pos] / 2), -2]
      return True
  return False

def sfadd(n1, n2):
  n = [-1]
  n.extend(n1)
  n.extend(n2)
  n.append(-2)
  while True:
    if explode(n):
      continue
    if not split(n):
      break
  return n

def feedlist(l):
  for n in l:
    yield n

def relist(l):
  if type(l) == list:
    return relist(feedlist(l))
  n = next(l)
  if n == -1:
    res = [relist(l), relist(l)]
    next(l)
    return res
  return n

def mag(l):
  if type(l) == list:
    return 3 * mag(l[0]) + 2 * mag(l[1])
  return l

l = relist(reduce(sfadd, nums))
print(mag(l))

print(max([max(mag(relist(sfadd(n1, n2))), mag(relist(sfadd(n2, n1)))) for n1, n2 in combinations(nums, 2)]))

