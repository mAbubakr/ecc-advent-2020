#!/usr/bin/python3

import re

lines = [line.strip() for line in open("ssinput", "r")]

total = 0
for line in lines:
  nums = [int(c) for c in line if c in "123456789"]
  total += nums[0] * 10 + nums[-1]

print(total)

words = ["one", "two", "three", "four", "five", "six", "seven", "eight", "nine"]

p = re.compile('(' + "|".join(words + list("123456789")) + ')')

total = 0
for line in lines:
  n1 = p.search(line)[0]
  for i in range(len(line) - 1, -1, -1):
    m = p.search(line, i)
    if m is not None:
      break
  else:
    print("failed")
  n2 = m[0]
  if len(n1) == 1:
    n1 = int(n1)
  else:
    n1 = words.index(n1) + 1
  if len(n2) == 1:
    n2 = int(n2)
  else:
    n2 = words.index(n2) + 1
  total += n1 * 10 + n2

print(total)

