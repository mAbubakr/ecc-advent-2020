#!/usr/bin/python3

depths = [int(n) for n in open("input", "r")]

prev = 0
count = 0

for d in depths:
  if prev and d > prev:
    count += 1
  prev = d

print(count)

prev = 0
count = 0

for i in range(2, len(depths)):
  s = depths[i] + depths[i-1] + depths[i-2]
  if prev and s > prev:
    count += 1
  prev = s

print(count)

