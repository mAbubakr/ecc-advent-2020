#!/usr/bin/python3

import math

data = open("input", "r").readline().strip()
pos = 0
bit = 4
nibble = 0
nbits = 0

def getbits(n):
  global pos, bit, nibble, nbits
  out = 0
  for i in range(n):
    if bit == 4:
      nibble = int(data[pos], 16)
      pos += 1
    bit -= 1
    out = (out << 1) + ((nibble >> bit) & 1)
    nbits += 1
    if bit == 0:
      bit = 4
  return out

vtotal = 0

def parseNumber():
  ret = 0
  while True:
    n = getbits(5)
    ret = (ret << 4) + (n & 0xf)
    if n & 0x10 == 0:
      break
  return ret

def parseOperator(t):
  lt = getbits(1)
  if lt == 0:
    n = getbits(15)
    endbits = nbits + n
    nums = []
    while nbits < endbits:
      nums.append(parsePacket())
  else:
    n = getbits(11)
    nums = [parsePacket() for i in range(n)]
  if t == 0:
    return sum(nums)
  if t == 1:
    return math.prod(nums)
  if t == 2:
    return min(nums)
  if t == 3:
    return max(nums)
  if t == 5:
    if nums[0] > nums[1]:
      return 1
    return 0
  if t == 6:
    if nums[0] < nums[1]:
      return 1
    return 0
  if t == 7:
    if nums[0] == nums[1]:
      return 1
    return 0

def parsePacket():
  global vtotal
  v = getbits(3)
  vtotal += v
  t = getbits(3)
  if t == 4:
    return parseNumber()
  else:
    return parseOperator(t)

val = parsePacket()

print(vtotal)
print(val)

