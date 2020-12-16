#!/usr/bin/python3

import re

fieldre = re.compile("([^:]+): (\d+)-(\d+) or (\d+)-(\d+)")

data = open("input", "r")

fields = {}

for line in data:
  line = line.strip()
  if not line:
    break
  m = fieldre.match(line)
  fields[m[1]] = [int(m[n]) for n in range(2,6)]

data.readline()
mine = [int(n) for n in data.readline().split(",")]
data.readline()

data.readline()
tickets = []
total = 0
for line in data:
  info = [int(n) for n in line.split(",")]
  bad = False
  for n in info:
    for k, (n1, n2, n3, n4) in fields.items():
      if (n >= n1 and n <= n2) or (n >= n3 and n <= n4):
        break
    else:
      bad = True
      total += n
  if not bad:
    tickets.append(info)

print(total)

fieldnames = fields.keys()
fieldmap = {}
for name in fieldnames:
  n1, n2, n3, n4 = fields[name]
  valid = set(range(len(fields)))
  for ticket in tickets:
    for i in range(len(ticket)):
      n = ticket[i]
      if (n >= n1 and n <= n2) or (n >= n3 and n <= n4):
        pass
      else:
        if i in valid:
          valid.remove(i)
  fieldmap[name] = list(valid)

changed = True
while changed:
  changed = False
  for k, v in fieldmap.items():
    if len(v) == 1:
      n = v[0]
      for k2, v2 in fieldmap.items():
        if k == k2:
          continue
        if n in v2:
          fieldmap[k2].remove(n)
          changed = True

total = 1
for field in fieldnames:
  if field.startswith("departure"):
    total *= mine[fieldmap[field][0]]
      
print(total)


