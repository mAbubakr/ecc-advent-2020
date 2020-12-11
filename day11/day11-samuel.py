#!/usr/bin/python3

data = open("input", "r")

orig = [[c for c in l] for l in [line.strip() for line in data]]

seats = [row[:] for row in orig]

width = len(seats[0])
height = len(seats)

def seatcount(x, y):
  n = 0
  if y > 0:
    if x > 0:
      if seats[y-1][x-1] == "#":
        n += 1
    if seats[y-1][x] == "#":
      n += 1
    if x < width - 1:
      if seats[y-1][x+1] == "#":
        n += 1

  if x > 0:
    if seats[y][x-1] == "#":
      n += 1
  if x < width - 1:
    if seats[y][x+1] == "#":
      n += 1

  if y < height-1:
    if x > 0:
      if seats[y+1][x-1] == "#":
        n += 1
    if seats[y+1][x] == "#":
      n += 1
    if x < width - 1:
      if seats[y+1][x+1] == "#":
        n += 1
  return n

changed = True
while changed:
  changed = False
  nseats = [[None for i in range(width)] for j in range(height)]
  for y in range(height):
    for x in range(width):
      if seats[y][x] == ".":
        nseats[y][x] = "."
      elif seats[y][x] == "L":
        if seatcount(x, y) == 0:
          nseats[y][x] = "#"
          changed = True
        else:
          nseats[y][x] = "L"
      else:
        if seatcount(x, y) >= 4:
          nseats[y][x] = "L"
          changed = True
        else:
          nseats[y][x] = "#"
  seats = nseats

total = 0
for y in range(height):
  for x in range(width):
    if seats[y][x] == "#":
      total += 1

print(total)

seats = [row[:] for row in orig]

def checkdir(x, y, dx, dy):
  while True:
    x += dx
    y += dy
    if x < 0 or x >= width or y < 0 or y >= height:
      return False
    elif seats[y][x] == "#":
      return True
    elif seats[y][x] == "L":
      return False

def seatcount2(x, y):
  n = 0
  for dx, dy in [(-1, -1), (-1, 0), (-1, 1), (0, -1), (0, 1), (1, -1), (1, 0), (1, 1)]:
    if checkdir(x, y, dx, dy):
      n += 1
  return n

changed = True
while changed:
  changed = False
  nseats = [[None for i in range(width)] for j in range(height)]
  for y in range(height):
    for x in range(width):
      if seats[y][x] == ".":
        nseats[y][x] = "."
      elif seats[y][x] == "L":
        if seatcount2(x, y) == 0:
          nseats[y][x] = "#"
          changed = True
        else:
          nseats[y][x] = "L"
      else:
        if seatcount2(x, y) >= 5:
          nseats[y][x] = "L"
          changed = True
        else:
          nseats[y][x] = "#"
  seats = nseats

total = 0
for y in range(height):
  for x in range(width):
    if seats[y][x] == "#":
      total += 1

print(total)

