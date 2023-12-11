#!/usr/bin/python3

rows = []
for line in open("ssinput", "r"):
  row = [int(n) for n in line.split()]
  rows.append(row)

total1 = 0
total2 = 0
for row in rows:
  l = []
  p = row
  while True:
    l.append(p)
    if all([n == 0 for n in p]):
      break
    p = [p[i+1] - p[i] for i in range(len(p)-1)]
  l.reverse()
  n = 0
  for i in range(1, len(l)):
    n = l[i][-1] + n
  total1 += n
  n = 0
  for i in range(1, len(l)):
    n = l[i][0] - n
  total2 += n

print(total1)
print(total2)

