#!/usr/bin/python3

data = open("ssinput", "r")

lines = [line.strip() for line in data]

instr = []
for line in lines:
  instr.append(line.split(" "))

def execute():
  pos = 0
  while pos < len(instr):
    cmd = instr[pos]
    #print(pos, cmd, regs)
    op = cmd[0]
    if op == "inc":
      regs[cmd[1]] += 1
    elif op == "tpl":
      regs[cmd[1]] *= 3
    elif op == "hlf":
      regs[cmd[1]] //= 2
    elif op == "jmp":
      pos += int(cmd[1])
    elif op == "jie":
      if regs[cmd[1][0]] % 2 == 0:
        pos += int(cmd[2])
      else:
        pos += 1
    elif op == "jio":
      if regs[cmd[1][0]] == 1:
        pos += int(cmd[2])
      else:
        pos += 1
    if op[0] != "j":
      pos += 1
  print(regs["b"])

regs = {"a": 0, "b": 0}
execute()
regs = {"a": 1, "b": 0}
execute()

