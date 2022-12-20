#!/usr/bin/python3

data = open("input", "r")

lines = [line.strip() for line in data]

nums = list(map(int, lines))
lnums = len(nums)
pnums = list(range(lnums))

def mix():
  for op, n in enumerate(nums):
    pos = pnums[op]
    npos = (pos + n) % (lnums - 1)
    if npos == 0:
      npos = lnums - 1
    if npos > pos:
      for i in range(lnums):
        if pnums[i] > pos and pnums[i] <= npos:
          pnums[i] -= 1
    else:
      for i in range(lnums):
        if pnums[i] >= npos and pnums[i] < pos:
          pnums[i] += 1
    pnums[op] = npos

mix()

p = pnums[nums.index(0)]
n1 = nums[pnums.index((p + 1000) % lnums)]
n2 = nums[pnums.index((p + 2000) % lnums)]
n3 = nums[pnums.index((p + 3000) % lnums)]

print(n1+n2+n3)

nums = [n * 811589153 for n in nums]
pnums = list(range(lnums))

for i in range(10):
  mix()

p = pnums[nums.index(0)]
n1 = nums[pnums.index((p + 1000) % lnums)]
n2 = nums[pnums.index((p + 2000) % lnums)]
n3 = nums[pnums.index((p + 3000) % lnums)]

print(n1+n2+n3)

