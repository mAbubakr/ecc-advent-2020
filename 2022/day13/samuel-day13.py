#!/usr/bin/python3

from itertools import zip_longest
from functools import cmp_to_key

data = open("input", "r")

lines = [line.strip() for line in data]

def compare(i1, i2):
  if i1 is not None and i2 is None:
    return 1
  if i1 is None and i2 is not None:
    return -1
  if type(i1) == list and type(i2) != list:
    return compare(i1, [i2])
  if type(i1) != list and type(i2) == list:
    return compare([i1], i2)
  if type(i1) == list and type(i2) == list:
    for e1, e2 in zip_longest(i1, i2):
      res = compare(e1, e2)
      if res is None:
        continue
      return res
    return None
  if i1 < i2:
    return -1
  if i1 > i2:
    return 1
  return None

index = 1
total = 0
packets = []
while lines:
  r1 = eval(lines[0])
  r2 = eval(lines[1])
  packets.append(r1)
  packets.append(r2)
  if compare(r1, r2) < 0:
    total += index
  index += 1
  lines = lines[3:]

print(total)

packets.append([[2]])
packets.append([[6]])

packets.sort(key=cmp_to_key(compare))
print((packets.index([[2]]) + 1) * (packets.index([[6]]) + 1))

