#!/usr/bin/python3

data = open("input", "r")

lines = [line.strip() for line in data]

class Node(object):
  def __init__(self, name, parent):
    self.name = name
    self.parent = parent
    self.files = []
    self.dirs = {}

root = Node("/", None)
cd = root

for line in lines:
  if line[0] == "$":
    if line[2] == "c":
      if line[5] == "/":
        cd = root
      elif line[5] == ".":
        cd = cd.parent
      else:
        cd = cd.dirs[line[5:]]
    continue
  if line[0] == "d":
    name = line[4:]
    cd.dirs[name] = Node(name, cd)
    continue
  info = line.split(" ")
  cd.files.append((info[1], int(info[0])))

sizes = []

def dirsum(node):
  tsum = 0
  for name, d in node.dirs.items():
    tsum += dirsum(d)
  for n, size in node.files:
    tsum += size
  sizes.append((tsum, node.name))
  return tsum

total = dirsum(root)

print(sum([s for s, n in sizes if s <= 100000]))

avail = 70000000 - total
need = 30000000 - avail

sizes.sort()

for s, n in sizes:
  if s >= need:
    print(s)
    break

