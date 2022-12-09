#!/usr/bin/python3

data = open("input", "r")

lines = [line.strip() for line in data]

posmap = set()
head = [0, 0]
tail = [0, 0]

def calcTail():
  dx = head[0] - tail[0]
  dy = head[1] - tail[1]
  if abs(dx) > 1 or abs(dy) > 1:
    if dx > 0:
      tail[0] += 1
    elif dx < 0:
      tail[0] -= 1
    if dy > 0:
      tail[1] += 1
    elif dy < 0:
      tail[1] -= 1

for line in lines:
  direction = line[0]
  dist = int(line[2:])
  dx = 0
  dy = 0
  if direction == "R":
    dx = 1
  elif direction == "L":
    dx = -1
  elif direction == "U":
    dy = 1
  elif direction == "D":
    dy = -1

  for i in range(dist):
    head = [head[0] + dx, head[1] + dy]
    calcTail()
    posmap.add(tuple(tail))

print(len(posmap))

posmap = set()
rope = [[0, 0] for _ in range(10)]

def calcMove(one, two):
  dx = one[0] - two[0]
  dy = one[1] - two[1]
  if abs(dx) > 1 or abs(dy) > 1:
    if dx > 0:
      two[0] += 1
    elif dx < 0:
      two[0] -= 1
    if dy > 0:
      two[1] += 1
    elif dy < 0:
      two[1] -= 1

for line in lines:
  direction = line[0]
  dist = int(line[2:])
  dx = 0
  dy = 0
  if direction == "R":
    dx = 1
  elif direction == "L":
    dx = -1
  elif direction == "U":
    dy = 1
  elif direction == "D":
    dy = -1

  for i in range(dist):
    rope[0][0] += dx
    rope[0][1] += dy
    for i in range(9):
      calcMove(rope[i], rope[i+1])
    posmap.add(tuple(rope[-1]))

print(len(posmap))

