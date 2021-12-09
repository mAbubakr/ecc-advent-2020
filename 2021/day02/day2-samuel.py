#!/usr/bin/python3

x = 0
y = 0

for line in open("input", "r"):
  cmd, n = line.split(" ")
  n = int(n)
  if cmd == "forward":
    x += n
  elif cmd == "up":
    y -= n
  elif cmd == "down":
    y += n

print(x * y)

x = 0
y = 0
aim = 0

for line in open("input", "r"):
  cmd, n = line.split(" ")
  n = int(n)
  if cmd == "forward":
    x += n
    y += n * aim
  elif cmd == "up":
    aim -= n
  elif cmd == "down":
    aim += n

print(x * y)

