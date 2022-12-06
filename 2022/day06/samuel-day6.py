#!/usr/bin/python3

data = open("input", "r").read().strip()

for i in range(4, len(data) + 1):
  if len(set(data[i-4:i])) == 4:
    print(i)
    break

for i in range(14, len(data) + 1):
  if len(set(data[i-14:i])) == 14:
    print(i)
    break

