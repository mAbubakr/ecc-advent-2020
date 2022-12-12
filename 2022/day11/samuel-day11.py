#!/usr/bin/python3

from math import prod

data = open("input", "r")

lines = [line.strip() for line in data]

monkeys = []

while lines:
  monkey = []
  monkey.append([int(n) for n in lines[1].split(": ")[1].split(", ")])
  line = lines[2].split(" ")
  if line[-2] == "+":
    n = int(line[-1])
    monkey.append(lambda old, x=n: old + x)
  else:
    if line[-1] == "old":
      monkey.append(lambda old: old * old)
    else:
      n = int(line[-1])
      monkey.append(lambda old, x=n: old * x)
  monkey.append(int(lines[3].split(" ")[-1]))
  monkey.append(int(lines[4].split(" ")[-1]))
  monkey.append(int(lines[5].split(" ")[-1]))
  monkey.append(0)
  monkeys.append(monkey)
  lines = lines[7:]

origitems = [m[0][:] for m in monkeys]
modulo = prod([m[2] for m in monkeys])

for _ in range(20):
  for monkey in monkeys:
    monkey[5] += len(monkey[0])
    for n in monkey[0]:
      nn = monkey[1](n) // 3
      if nn % monkey[2] == 0:
        monkeys[monkey[3]][0].append(nn)
      else:
        monkeys[monkey[4]][0].append(nn)
      monkey[0] = []

l = [m[5] for m in monkeys]
l.sort()
print(l[-1] * l[-2])

for i in range(len(monkeys)):
  monkeys[i][0] = origitems[i]
  monkeys[i][5] = 0

for _ in range(10000):
  for monkey in monkeys:
    monkey[5] += len(monkey[0])
    for n in monkey[0]:
      nn = monkey[1](n) % modulo
      if nn % monkey[2] == 0:
        monkeys[monkey[3]][0].append(nn)
      else:
        monkeys[monkey[4]][0].append(nn)
      monkey[0] = []

l = [m[5] for m in monkeys]
l.sort()
print(l[-1] * l[-2])

