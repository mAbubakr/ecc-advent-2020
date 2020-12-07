#!/usr/bin/python3

data = open("input", "r")

bags = {}

for line in data:
  line = line.strip().replace(",", "").replace(".", "")
  words = line.split(" ")
  outer = f"{words[0]} {words[1]}"
  inner = []
  for n in range(4, len(words), 4):
    if words[n] == "no":
      continue
    num = int(words[n])
    name = f"{words[n+1]} {words[n+2]}"
    inner.append((num, name))
  bags[outer] = inner

valid = set(["shiny gold"])
done = False
while not done:
  done = True
  for outer, inner in bags.items():
    if outer in valid:
      continue
    for n, c in inner:
      if c in valid:
        valid.add(outer)
        done = False
        break

print(len(valid) - 1)

def check_bag(color):
  count = 1
  if not color in bags:
    return count
  for n, c in bags[color]:
    count += n * check_bag(c)
  return count

print(check_bag("shiny gold") - 1)

