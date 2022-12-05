#!/usr/bin/python3

data = open("input", "r")

lines = [line.strip('\n') for line in data]

rows = []
moves = []
for line in lines:
  if not line:
    continue
  if line[0] == 'm':
    s = line.split(' ')
    moves.append((int(s[1]), int(s[3])-1, int(s[5])-1))
  else:
    rows.append(line)

ncols = (len(rows.pop()) + 1) // 4

stacks = []
for i in range(ncols):
  stacks.append([])

trows = rows[:]
while trows:
  row = trows.pop()
  for i in range(ncols):
    c = row[i * 4 + 1] 
    if c != ' ':
      stacks[i].append(c)

for n, s, d in moves:
  for i in range(n):
    stacks[d].append(stacks[s].pop())

print("".join(s[-1] for s in stacks))

stacks = []
for i in range(ncols):
  stacks.append([])

trows = rows[:]
while trows:
  row = trows.pop()
  for i in range(ncols):
    c = row[i * 4 + 1] 
    if c != ' ':
      stacks[i].append(c)

for n, s, d in moves:
  stacks[d].extend(stacks[s][-n:])
  del stacks[s][-n:]

print("".join(s[-1] for s in stacks))

