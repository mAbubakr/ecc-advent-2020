#!/usr/bin/python3

data = "1321131112"

def cycle(data, n):
  data += "a"
  for i in range(n):
    count = 0
    n = None
    res = ""
    for c in data:
      if c == n:
        count += 1
      else:
        if n is not None:
          res += f"{count}{n}"
        n = c
        count = 1
    #print(res)
    data = res + "a"
  return res

print(len(cycle(data, 40)))
print(len(cycle(data, 50)))

