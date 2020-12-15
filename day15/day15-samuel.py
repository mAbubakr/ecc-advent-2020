#!/usr/bin/python3

nums = {}
data = [0,3,1,6,7]
num = 5
for i in range(len(data)):
  nums[data[i]] = i + 1

for i in range(len(data), 2019):
  if num in nums:
    nnum = i + 1 - nums[num]
  else:
    nnum = 0
  data.append(num)
  nums[num] = i + 1
  num = nnum

print(num)

nums = {}
data = [0,3,1,6,7]
num = 5
for i in range(len(data)):
  nums[data[i]] = i + 1

for i in range(len(data), 29999999):
  if num in nums:
    nnum = i + 1 - nums[num]
  else:
    nnum = 0
  data.append(num)
  nums[num] = i + 1
  num = nnum

print(num)

