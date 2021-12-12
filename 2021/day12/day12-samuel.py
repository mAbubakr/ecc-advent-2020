#!/usr/bin/python3

lines = [line.strip().split("-") for line in open("input", "r")]

rooms = {}
starts = []

for end1, end2 in lines:
  if end1 == "start":
    starts.append(end2)
    continue
  if end2 == "start":
    starts.append(end1)
    continue
  if not end1 in rooms:
    rooms[end1] = []
  if not end2 in rooms:
    rooms[end2] = []
  rooms[end1].append(end2)
  rooms[end2].append(end1)

def path(room, visited):
  global count
  if room == "end":
    count += 1
    return
  if room.islower():
    visited.add(room)
  for nroom in rooms[room]:
    if nroom not in visited:
      path(nroom, visited)
  if room.islower():
    visited.remove(room)

count = 0
for start in starts:
  path(start, set())

print(count)

def path2(room, visited, dup):
  global count
  if room == "end":
    count += 1
    return
  if room.islower() and not dup == room:
    visited.add(room)
  for nroom in rooms[room]:
    if nroom not in visited:
      path2(nroom, visited, dup)
    elif not dup:
      path2(nroom, visited, nroom)
  if room.islower() and not dup == room:
    visited.remove(room)

count = 0
for start in starts:
  path2(start, set(), "")

print(count)

