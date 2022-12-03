#!/usr/bin/python3

values = {"A": 0, "B": 1, "C": 2}
rvalues = {0: "A", 1: "B", 2: "C"}
code = {"X": "A", "Y": "B", "Z": "C"}
score = 0
for line in open("input", "r"):
  opp = line[0]
  ov = values[opp]
  me = code[line[2]]
  mv = values[me]
  score += mv + 1
  if opp == me:
    score += 3
  elif mv - ov == 1 or mv - ov == -2:
    score += 6

print(score)

score = 0
for line in open("input", "r"):
  opp = line[0]
  ov = values[opp]
  res = line[2]
  if res == "X":
    me = rvalues[(ov + 2) % 3]
  elif res == "Y":
    me = opp
  else:
    me = rvalues[(ov + 1) % 3]
  mv = values[me]
  score += mv + 1
  if opp == me:
    score += 3
  elif mv - ov == 1 or mv - ov == -2:
    score += 6

print(score)

