#!/usr/bin/python3

data = open("input", "r")

lines = [line.strip() for line in data]

states = {}

y = 0
for line in lines:
  for x in range(len(line)):
    pos = (x, y, 0)
    states[pos] = line[x] == "#"
  y += 1

x1 = -1
x2 = len(lines[0])
y1 = -1
y2 = len(lines)
z1 = -1
z2 = 1

def countn(x, y, z):
  count = 0
  for tx in range(x-1, x+2):
    for ty in range(y-1, y+2):
      for tz in range(z-1, z+2):
        if tx == x and ty == y and tz == z:
          continue
        pos = (tx, ty, tz)
        if pos in states and states[pos]:
          count += 1
  return count

def update():
  newstates = {}
  for x in range(x1, x2+1):
    for y in range(y1, y2+1):
      for z in range(z1, z2+1):
        pos = (x, y, z)
        count = countn(*pos)
        if pos in states and states[pos]:
          newstates[pos] = count == 2 or count == 3
        else:
          newstates[pos] = count == 3
  return newstates

for i in range(6):
  states = update()
  x1 -= 1
  x2 += 1
  y1 -= 1
  y2 += 1
  z1 -= 1
  z2 += 1

print(list(states.values()).count(True))

states = {}

y = 0
for line in lines:
  for x in range(len(line)):
    pos = (0, x, y, 0)
    states[pos] = line[x] == "#"
  y += 1

w1 = -1
w2 = 1
x1 = -1
x2 = len(lines[0])
y1 = -1
y2 = len(lines)
z1 = -1
z2 = 1

def countn(w, x, y, z):
  count = 0
  for tx in range(x-1, x+2):
    for ty in range(y-1, y+2):
      for tz in range(z-1, z+2):
        for tw in range(w-1, w+2):
          if tw == w and tx == x and ty == y and tz == z:
            continue
          pos = (tw, tx, ty, tz)
          if pos in states and states[pos]:
            count += 1
  return count

def update():
  newstates = {}
  for x in range(x1, x2+1):
    for y in range(y1, y2+1):
      for z in range(z1, z2+1):
        for w in range(w1, w2+1):
          pos = (w, x, y, z)
          count = countn(*pos)
          if pos in states and states[pos]:
            newstates[pos] = count == 2 or count == 3
          else:
            newstates[pos] = count == 3
  return newstates

for i in range(6):
  states = update()
  w1 -= 1
  w2 += 1
  x1 -= 1
  x2 += 1
  y1 -= 1
  y2 += 1
  z1 -= 1
  z2 += 1

print(list(states.values()).count(True))

