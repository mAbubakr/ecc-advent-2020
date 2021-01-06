#!/usr/bin/python3

import re

linematch = re.compile("(.+) => (.*)")

data = open("input", "r")

subs = {}

for line in data:
  line = line.strip()
  if not line:
    break
  m = linematch.match(line)
  f = m.group(1)
  t = m.group(2)
  if f not in subs:
    subs[f] = [t]
  else:
    subs[f].append(t)

start = data.readline().strip()

results = set()
for f, t in subs.items():
  p = 0
  while True:
    i = start.find(f, p)
    if i < 0:
      break
    for r in t:
      results.add(start[:i] + r + start[i+len(f):])
    p = i + len(f)

print(len(results))

rsubs = []
for k, v in subs.items():
  for x in v:
    rsubs.append((x, k))
rsubs.sort(key=lambda x: len(x[0]), reverse=True)

def search(mol, count):
  if mol == "e":
    print(count)
    return True
  count += 1
  for k, v in rsubs:
    pos = 0
    while True:
      pos = mol.find(k, pos)
      if pos == -1:
        break
      nmol = mol[:pos] + v + mol[pos+len(k):]
      if nmol not in history:
        history.add(nmol)
        if search(nmol, count):
          return True
      pos += len(k)
  
history = set()
search(start, 0)

