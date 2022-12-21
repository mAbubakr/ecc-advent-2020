#!/usr/bin/python3

data = open("input", "r")

lines = [line.strip() for line in data]

mvalues = {}

def getValue(m):
  res = mvalues[m]
  if type(res) is int:
    return res
  res = res()
  mvalues[m] = res
  return res

for line in lines:
  m, op = line.split(": ")
  op = op.split(" ")
  if len(op) == 1:
    mvalues[m] = int(op[0])
  else:
    mvalues[m] = eval(f"lambda: getValue('{op[0]}') {op[1]} getValue('{op[2]}')")

print(int(getValue("root")))

mvalues = {}

for line in lines:
  m, op = line.split(": ")
  if m == "humn":
    continue
  op = op.split(" ")
  if m == "root":
    root = (op[0], op[2])
    continue
  if len(op) == 1:
    mvalues[m] = int(op[0])
  else:
    mvalues[m] = op

def findHuman(m):
  if m == "humn":
    return True
  res = mvalues[m]
  if type(res) is int:
    return False
  if findHuman(res[0]):
    return True
  return findHuman(res[2])

def getValue2(m):
  res = mvalues[m]
  if type(res) is int:
    return res
  v1 = getValue2(res[0])
  v2 = getValue2(res[2])
  if res[1] == "*":
    res = v1 * v2
  elif res[1] == "/":
    res = v1 / v2
  elif res[1] == "+":
    res = v1 + v2
  elif res[1] == "-":
    res = v1 - v2
  mvalues[m] = res
  return res

def solveValue(value, m):
  if m == "humn":
    return value
  res = mvalues[m]
  if findHuman(res[0]):
    v1 = None
    v2 = getValue2(res[2])
  else:
    v1 = getValue2(res[0])
    v2 = None
  if res[1] == "*":
    if v1 is None:
      return solveValue(value / v2, res[0])
    return solveValue(value / v1, res[2])
  if res[1] == "/":
    if v1 is None:
      return solveValue(value * v2, res[0])
    return solveValue(v1 / value, res[2])
  if res[1] == "+":
    if v1 is None:
      return solveValue(value - v2, res[0])
    return solveValue(value - v1, res[2])
  if res[1] == "-":
    if v1 is None:
      return solveValue(value + v2, res[0])
    return solveValue(v1 - value, res[2])

if findHuman(root[0]):
  m, value = root[0], getValue2(root[1])
else:
  m, value = root[1], getValue2(root[0])

print(int(solveValue(value, m)))

