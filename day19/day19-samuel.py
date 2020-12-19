#!/usr/bin/python3

data = open("input", "r")

rules = {}
for line in data:
  line = line.strip()
  if not line:
    break
  num, rest = line.split(": ")
  num = int(num)
  if rest[0] == '"':
    rules[num] = rest[1]
  elif "|" in rest:
    l1, l2 = rest.split(" | ")
    n1 = tuple([int(n) for n in l1.split(" ")])
    n2 = tuple([int(n) for n in l2.split(" ")])
    rules[num] = [n1, n2]
  else:
    rules[num] = tuple([int(n) for n in rest.split(" ")])

def check_sub(data, i, rule):
  if type(rule) == str:
    if i < len(data) and data[i] == rule:
      return True, i + 1
    else:
      return False, 0
  if type(rule) == list:
    for t in rule:
      done, n = check_sub(data, i, t)
      if done:
        return True, n
    return False, 0
  if type(rule) == tuple:
    n = i
    for r in rule:
      done, n = check_sub(data, n, rules[r])
      if not done:
        return False, 0
    return True, n

def check(line):
  done, n = check_sub(line, 0, rules[0])
  return done and n == len(line)

lines = [line.strip() for line in data]

total = 0
for line in lines:
  if check(line):
    total += 1

print(total)

def check2(line):
  count1 = 0
  i = 0
  while i < len(line):
    done, n = check_sub(line, i, rules[42])
    if not done:
      break
    count1 += 1
    i += 8
  count2 = 0
  i = len(line)
  while i > 0:
    done, n = check_sub(line, i - 8, rules[31])
    if not done:
      break
    count2 += 1
    i -= 8
  chunks = len(line) // 8
  return count1 and count2 and count1 > count2 and count1 + count2 == chunks

total = 0
for line in lines:
  if check2(line):
    total += 1

print(total)

