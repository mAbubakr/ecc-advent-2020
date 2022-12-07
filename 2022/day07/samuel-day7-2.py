#!/usr/bin/python3

data = open("input", "r")

lines = [line.strip() for line in data]

dirs = []
stack = [0]

for line in lines:
  if line[0] == "$":
    if line[2] == "c":
      if line[5] == "/":
        while len(stack) > 1:
          n = stack.pop()
          dirs.append(n)
          stack[-1] += n
      elif line[5] == ".":
        n = stack.pop()
        dirs.append(n)
        stack[-1] += n
      else:
        stack.append(0)
    continue
  elif line[0] == "d":
    continue
  stack[-1] += int(line.split(" ")[0])

while stack:
  n = stack.pop()
  dirs.append(n)
  if stack:
    stack[-1] += n

dirs.sort()

print(sum([s for s in dirs if s <= 100000]))

avail = 70000000 - dirs[-1]
need = 30000000 - avail

for s in dirs:
  if s >= need:
    print(s)
    break

