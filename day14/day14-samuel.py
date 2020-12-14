#!/usr/bin/python3

data = open("input", "r")

lines = [line.strip() for line in data]

maskor = 0
maskand = 0xfffffffff
mem = {}

for line in lines:
  cmd, value = line.split(" = ")
  if cmd == "mask":
    value = list(value)
    value.reverse()
    maskor = 0
    maskand = 0
    for i in range(len(value)):
     if value[i] == "0":
       maskand |= 1 << i
     elif value[i] == "1":
       maskor |= 1 << i
    maskand ^= 0xfffffffffff
  else:
    loc = int(cmd[4:-1])
    value = int(value)
    value |= maskor
    value &= maskand
    mem[loc] = value

print(sum(mem.values()))

maskor = 0
maskperms = []
mem = {}

for line in lines:
  cmd, value = line.split(" = ")
  if cmd == "mask":
    value = list(value)
    value.reverse()
    maskor = 0
    maskperms = []
    for i in range(len(value)):
     if value[i] == "X":
       maskperms.append(i)
     elif value[i] == "1":
       maskor |= 1 << i
  else:
    loc = int(cmd[4:-1])
    value = int(value)
    loc |= maskor
    for i in range(1 << len(maskperms)):
      for j in range(len(maskperms)):
        if i & (1 << j) == 0:
          loc &= ~(1 << maskperms[j])
        else:
          loc |= 1 << maskperms[j]
      mem[loc] = value

print(sum(mem.values()))



