#!/usr/bin/python3

letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"

data = open("input", "r")
lines = [line.strip() for line in data]

total = 0
for line in lines:
  line = list(line)
  l = len(line)
  s1 = line[:l//2]
  s2 = line[l//2:]
  d = list(set(s1).intersection(set(s2)))[0]
  total += letters.index(d) + 1

print(total)

total = 0
count = 0
for line in lines:
  if count == 0:
    s = set(line)
  else:
    s = s.intersection(set(line))
  count += 1
  if count == 3:
    total += letters.index(list(s)[0]) + 1
    count = 0

print(total)
  
