#!/usr/bin/python3

import re

data = open("input", "r")

code = []
for line in data:
  line = line.strip()
  instr, num = line.split(" ")
  code.append([instr, int(num)])

run = set()
pos = 0
accum = 0
while True:
  instr, num = code[pos]
  if pos in run:
    print(accum)
    break
  run.add(pos)
  if instr == "acc":
    accum += num
    pos += 1
  elif instr == "nop":
    pos += 1
  elif instr == "jmp":
    pos += num

def test():
  run = set()
  pos = 0
  accum = 0
  while True:
    if pos > len(code):
      return False
    if pos == len(code):
      print(accum)
      return True
    if pos in run:
      return False
    run.add(pos)
    instr, num = code[pos]
    if instr == "acc":
      accum += num
      pos += 1
    elif instr == "nop":
      pos += 1
    elif instr == "jmp":
      pos += num

for i in range(len(code)):
  if code[i][0] == "nop":
    code[i][0] = "jmp"
    if test():
      break
    code[i][0] = "nop"

for i in range(len(code)):
  if code[i][0] == "jmp":
    code[i][0] = "nop"
    if test():
      break
    code[i][0] = "jmp"

