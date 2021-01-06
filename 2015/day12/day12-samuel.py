#!/usr/bin/python3

import re

data = open("input", "r").read()

num = re.compile("(-?\d+)")

total = 0
for m in num.finditer(data):
  total += int(m[1])

print(total)

data = eval(data)

def search(chunk):
  sub = 0
  if type(chunk) == dict:
    for k, v in chunk.items():
      if v == "red":
        return 0
      if type(v) == int:
        sub += v
      elif type(v) == str:
        continue
      else:
        sub += search(v)
  elif type(chunk) == list:
    for v in chunk:
      if type(v) == int:
        sub += v
      elif type(v) == str:
        continue
      else:
        sub += search(v)
  else:
    print(f"unknown type: {type(chunk)}")
  return sub

print(search(data))

