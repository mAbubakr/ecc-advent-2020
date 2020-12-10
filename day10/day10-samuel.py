#!/usr/bin/python3

data = open("input", "r")

nums = [int(line) for line in data]

nums.append(0) # add the wall value
nums.sort()

one = 0
three = 0
for i in range(len(nums) - 1):
  if nums[i + 1] - nums[i] == 1:
    one += 1
  if nums[i + 1] - nums[i] == 3:
    three += 1

print(one * (three + 1))  # +1 for the phone

totals = [0] * len(nums)
totals[-1] = 1

# count the combinations starting from the end: O(n)
for i in range(len(nums) - 2, -1, -1):
  j = 1
  total = 0
  jolt = nums[i]
  while j < len(nums) and nums[j] - jolt <= 3:
    total += totals[j]
    j += 1
  totals[i] = total

print(totals[0])

