#!/usr/bin/python3

nums = [int(n) for n in open("input", "r").readline().split(",")]

dist = [sum([abs(n - i) for n in nums]) for i in range(max(nums))]

print(min(dist), dist.index(min(dist)))

cost = [(1 + i) * i / 2 for i in range(max(nums) + 1)]

dist = [sum([cost[abs(n - i)] for n in nums]) for i in range(max(nums))]

print(int(min(dist)), dist.index(min(dist)))

