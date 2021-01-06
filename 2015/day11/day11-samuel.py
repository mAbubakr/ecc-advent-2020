#!/usr/bin/python3

data = "vzbxkghb"

letters = "abcdefghijklmnopqrstuvwxyz"

pwd = [letters.find(c) for c in data]

def incr(pwd):
  for i in range(len(pwd)):
    n = (pwd[i] + 1) % 26
    pwd[i] = n
    if n != 0:
      break

def process():
  pwd.reverse()
  while True:
    incr(pwd)
    for i in range(len(pwd) - 2):
      n = pwd[i]
      if pwd[i+1] == n - 1 and pwd[i + 2] == n - 2:
        break
    else:
      continue
    if any([n in pwd for n in [letters.find(c) for c in "iol"]]):
      continue
    for i in range(len(pwd) - 3):
      n = pwd[i]
      if pwd[i+1] != n:
        continue
      for j in range(i+2, len(pwd)-1):
        if pwd[j] != n and pwd[j+1] == pwd[j]:
          break
      else:
        continue
      break
    else:
      continue
    break
  pwd.reverse()

process()
print("".join([letters[i] for i in pwd]))
process()
print("".join([letters[i] for i in pwd]))

