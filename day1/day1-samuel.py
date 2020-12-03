#!/usr/bin/python3

data = open("input", "r")
nums = [int(n) for n in data]

for i in range(len(nums)-1):
  for j in range(i+1, len(nums)):
    if nums[i] + nums[j] == 2020:
      print(nums[i] * nums[j])

for i in range(len(nums)-2):
  for j in range(i+1, len(nums)-1):
    for k in range(j+1, len(nums)):
      if nums[i] + nums[j] + nums[k] == 2020:
        print(nums[i] * nums[j] * nums[k])

