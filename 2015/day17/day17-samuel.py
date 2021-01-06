#!/usr/bin/python3

from collections import Counter

data = open("input", "r")

sizes = [int(n) for n in data]
sizes.sort(reverse=True)

count = 0
counts = Counter()
def search(sizes, total, num):
  global count
  if not sizes:
    return
  mine = sizes[0]
  rest = sizes[1:]
  if total + mine > 150:
    search(rest, total, num)
    return
  if total + mine == 150:
    count += 1
    counts.update([num])
    search(rest, total, num)
    return
  search(rest, total + mine, num + 1)
  search(rest, total, num)

search(sizes, 0, 0)
print(count)
nums = list(counts.keys())
nums.sort()
print(counts[nums[0]])

