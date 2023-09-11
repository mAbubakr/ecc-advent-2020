#!/usr/bin/python3

data = open("ssinput", "r")

dims = []

paper = 0
ribbon = 0
for line in data:
  dims = [int(n) for n in line.split("x")]
  dims.sort()
  paper += 3 * dims[0] * dims[1] + 2 * dims[1] * dims[2] + 2 * dims[0] * dims[2]
  ribbon += 2 * dims[0] + 2 * dims[1] + dims[0] * dims[1] * dims[2]

print(paper)
print(ribbon)

