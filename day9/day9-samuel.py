#!/usr/bin/python3

data = open("input", "r")

nums = []
for i in range(25):
  nums.append(int(data.readline()))

for line in data:
  num = int(line)
  nums.append(num)
  done = True
  for i in range(len(nums)-26, len(nums) - 2):
    for j in range(i + 1, len(nums) - 1):
      if nums[i] + nums[j] == num:
        done = False
        break
    if not done:
      break
  if done:
    print(num)
    break

key = num

for i in range(len(nums) - 1):
  total = nums[i]
  little = total
  big = total
  for j in range(i + 1, len(nums)):
    little = min(little, nums[j])
    big = max(big, nums[j])
    total += nums[j]
    if total == key:
      print(little + big)
    if total > key:
      break

