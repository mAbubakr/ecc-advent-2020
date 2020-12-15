#!/usr/bin/python3

def do_numbers(data, count):
  count -= 1
  nums = {}
  num = data[-1]
  data = data[:-1]
  for i in range(len(data)):
    nums[data[i]] = i + 1
  for i in range(len(data), count):
    if num in nums:
      nnum = i + 1 - nums[num]
    else:
      nnum = 0
    nums[num] = i + 1
    num = nnum
  return num

print(do_numbers([0,3,1,6,7,5], 2020))
print(do_numbers([0,3,1,6,7,5], 30000000))

