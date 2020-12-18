#!/usr/bin/python3

data = open("input", "r")

lines = [line.strip() for line in data]

numbers = "0123456789"
opers = {"+": 1, "*": 1, "(": 0}

def parse(line):
  res = []
  ops = []
  for c in line:
    if c == " ":
      continue
    if c in numbers:
      res.append(c)
    elif c == "(":
      ops.append(c)
    elif c == ")":
      while ops:
        c = ops.pop()
        if c == "(":
          break
        res.append(c)
    else:
      while ops and opers[c] <= opers[ops[-1]]:
        res.append(ops.pop())
      ops.append(c)
  while ops:
    res.append(ops.pop())
  return res

def calc(res):
  stack = []
  for c in res:
    if c in numbers:
      stack.append(c)
    elif c == "+":
      stack.append(int(stack.pop()) + int(stack.pop()))
    elif c == "*":
      stack.append(int(stack.pop()) * int(stack.pop()))
  return stack[0]

total = 0
for line in lines:
  res = parse(line)
  total += calc(res)

print(total)

opers = {"+": 2, "*": 1, "(": 0}

total = 0
for line in lines:
  res = parse(line)
  total += calc(res)

print(total)

