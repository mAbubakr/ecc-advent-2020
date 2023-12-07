#!/usr/bin/python3

from collections import Counter
from functools import cmp_to_key

cards = "23456789TJQKA"
types = [[5], [4, 1], [3, 2], [3, 1, 1], [2, 2, 1], [2, 1, 1, 1], [1, 1, 1, 1, 1]]

data = open("ssinput", "r")

lines = [line.strip() for line in data]

hands = []

def sorter(a, b):
  ta = types.index(a[0])
  tb = types.index(b[0])
  if ta > tb:
    return -1
  if ta < tb:
    return 1
  for c1, c2 in zip(a[1], b[1]):
    cv1 = cards.index(c1)
    cv2 = cards.index(c2)
    if cv1 > cv2:
      return 1
    if cv1 < cv2:
      return -1

for line in lines:
  hand = line.split()
  hand[1] = int(hand[1])
  hands.append([sorted(list(Counter(hand[0]).values()), reverse=True), hand[0], hand[1]])

hands.sort(key=cmp_to_key(sorter))
total = 0
for n, hand in enumerate(hands):
  total += (n + 1) * hand[2]
print(total)

cards2 = "J23456789TQKA"

def sorter2(a, b):
  ta = types.index(a[0])
  tb = types.index(b[0])
  if ta > tb:
    return -1
  if ta < tb:
    return 1
  for c1, c2 in zip(a[1], b[1]):
    cv1 = cards2.index(c1)
    cv2 = cards2.index(c2)
    if cv1 > cv2:
      return 1
    if cv1 < cv2:
      return -1

def convert(t, h):
  n = h.count("J")
  if n and n != 5:
    t.remove(n)
    t[0] += n

for hand in hands:
  convert(hand[0], hand[1])

hands.sort(key=cmp_to_key(sorter2))
total = 0
for n, hand in enumerate(hands):
  total += (n + 1) * hand[2]
print(total)

