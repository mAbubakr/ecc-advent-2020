#!/usr/bin/python3

from collections import Counter

rows = []
for line in open("input", "r"):
  first, second = line.strip().split(" | ")
  first = first.split(" ")
  second = second.split(" ")
  rows.append((first, second))

counts = Counter()
for first, second in rows:
  counts.update([len(s) for s in second])

print(counts[2] + counts[3] + counts[4] + counts[7])

seg2num = {"abcefg": "0", "cf": "1", "acdeg": "2", "acdfg": "3", "bcdf": "4", "abdfg": "5", "abdefg": "6", "acf": "7", "abcdefg": "8", "abcdfg": "9"}

def solve(digits, nums):
  segs = [set(digit) for digit in digits]
  s2 = [seg for seg in segs if len(seg) == 2][0]
  s3 = [seg for seg in segs if len(seg) == 3][0]
  s4 = [seg for seg in segs if len(seg) == 4][0]
  s5 = [seg for seg in segs if len(seg) == 5]
  s6 = [seg for seg in segs if len(seg) == 6]
  horiz = s5[0].intersection(s5[1].intersection(s5[2]))
  a = horiz.intersection(s3).pop()
  d = horiz.intersection(s4).pop()
  g = horiz.copy()
  g.remove(a)
  g.remove(d)
  g = g.pop()
  for s in s5:
    if s.issuperset(s3):
      # s is 3
      d3 = s
      break
  for s in s6:
    if s.issuperset(d3):
      b = (s - d3).pop()
    elif d in s:
      e = (s - d3 - s4).pop()
  for s in s5:
    if b in s:
      f = (s.intersection(d3) - horiz).pop()
    elif e in s:
      c = (s.intersection(d3) - horiz).pop()
  trans = str.maketrans("".join([a,b,c,d,e,f,g]), "abcdefg")
  #print(digits, nums, [a,b,c,d,e,f,g], dict([(chr(k), chr(v)) for k, v in trans.items()]))
  nums = [num.translate(trans) for num in nums]
  n2 = []
  for n in nums:
    l = list(n)
    l.sort()
    n2.append("".join(l))
  res = "".join([seg2num[n] for n in n2])
  return res

print(sum([int(solve(*row)) for row in rows]))

