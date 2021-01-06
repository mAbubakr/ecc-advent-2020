#!/usr/bin/python3

value = 33100000

num = 1000000
houses = [0] * num

for i in range(1, num):
  v = i * 10
  for j in range(i, num, i):
    houses[j] += v

for i, v in enumerate(houses):
  if v >= value:
    print(i)
    break

houses = [0] * num

for i in range(1, num):
  v = i * 11
  for j in range(i, min(i*50, num), i):
    houses[j] += v

for i, v in enumerate(houses):
  if v >= value:
    print(i)
    break

