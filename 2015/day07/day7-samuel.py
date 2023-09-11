#!/usr/bin/python3

import re

assign = re.compile("([0-9a-z]+)")
notg = re.compile("NOT (.+)")
shift = re.compile("(.+) ([RL])SHIFT (\d+)")
oper = re.compile("(.+) (.+) (.+)")

digits = "0123456789"

data = open("ssinput", "r")

lines = [line.strip() for line in data]

var = {}
vals = {}
for line in lines:
  instr, v = line.split(" -> ")
  var[v] = instr

def solveVar(v):
  instr = var[v]
  m = assign.fullmatch(instr)
  if m:
    v1 = m[1]
    if v1[0] in digits:
      v1 = int(v1)
    else:
      if v1 not in vals:
        solveVar(v1)
      v1 = vals[v1]
    vals[v] = v1
    return
  m = notg.match(instr)
  if m:
    v1 = m[1]
    if v1 not in vals:
      solveVar(v1)
    vals[v] = ~vals[v1] & 0xffff
    return
  m = shift.match(instr)
  if m:
    num = int(m[3])
    v1 = m[1]
    if v1 not in vals:
      solveVar(v1)
    if m[2] == "R":
      vals[v] = vals[v1] >> num
    else:
      vals[v] = (vals[v1] << num) & 0xffff
    return
  m = oper.match(instr)
  if m:
    op = m[2]
    v1 = m[1]
    if v1[0] in digits:
      v1 = int(v1)
    else:
      if v1 not in vals:
        solveVar(v1)
      v1 = vals[v1]
    v2 = m[3]
    if v2[0] in digits:
      v2 = int(v2)
    else:
      if v2 not in vals:
        solveVar(v2)
      v2 = vals[v2]
    if op == "AND":
      vals[v] = v1 & v2
    elif op == "OR":
      vals[v] = v1 | v2
    else:
      print(f"unknown op: {op}")
    return
  print(f"unknown input: {instr}")

solveVar("a")
print(vals["a"])

newb = vals["a"]
vals = {"b": newb}
solveVar("a")
print(vals["a"])

