#!/usr/bin/python3

data = open("input", "r")

lines = [line.strip() for line in data]

cycles = []
x = 1

for line in lines:
  cycles.append(x)
  if line[0] == 'n':
    continue
  cycles.append(x)
  x += int(line.split(" ")[1])

total = sum([cycles[n-1] * n for n in [20, 60, 100, 140, 180, 220]])
print(total)

cycle = 0
x = 1

s = ""

def checkPixel():
  global s
  global cycle
  if abs(x - (cycle % 40)) <= 1:
    s += "#"
  else:
    s += " "
  cycle += 1
  if cycle % 40 == 0:
    print(s)
    s = ""

for line in lines:
  checkPixel()
  if line[0] == 'n':
    continue
  checkPixel()
  x += int(line.split(" ")[1])

