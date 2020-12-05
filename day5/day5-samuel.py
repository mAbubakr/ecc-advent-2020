#!/usr/bin/python3

data = open("input", "r")

trans = "".maketrans("FBRL", "0110")
maxnum = 0
nums = set()
for line in data:
  line = line.strip()
  line = line.translate(trans)
  num = int(line, 2)
  nums.add(num)
  if num > maxnum:
    maxnum = num

print(maxnum)

nums = list(nums)
nums.sort()

for i in range(0, len(nums) - 1):
  if nums[i+1] - nums[i] == 2:
    print(nums[i]+1)

