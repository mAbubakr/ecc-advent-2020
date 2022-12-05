#!/usr/bin/python3

data = open("input", "r")

lines = [line.strip() for line in data]

count = 0
for line in lines:
  pair1, pair2 = line.split(",")
  pair1 = [int(n) for n in pair1.split("-")]
  pair2 = [int(n) for n in pair2.split("-")]
  if (pair1[0] <= pair2[0] and pair1[1] >= pair2[1]) or (pair2[0] <= pair1[0] and pair2[1] >= pair1[1]):
    count += 1

print(count)

count = 0
for line in lines:
  pair1, pair2 = line.split(",")
  pair1 = [int(n) for n in pair1.split("-")]
  pair2 = [int(n) for n in pair2.split("-")]
  if (pair1[0] <= pair2[1] and pair1[1] >= pair2[0]) or (pair2[0] <= pair1[1] and pair2[1] >= pair1[0]):
    count += 1

print(count)

