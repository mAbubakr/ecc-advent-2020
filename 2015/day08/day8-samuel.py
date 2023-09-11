#!/usr/bin/python3

data = open("input", "r")

lines = [line.strip() for line in data]

total = 0
mem = 0
extra = 0

for line in lines:
  total += len(line)
  mem += len(eval(line))
  extra += len(line) + line.count('"') + line.count("\\") + 2

print(total - mem)
print(extra - total)

