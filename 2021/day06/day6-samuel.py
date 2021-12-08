#!/usr/bin/python3

from collections import Counter

timers = Counter([int(n) for n in open("input", "r").readline().split(",")])

for i in range(80):
  ntimers = Counter()
  for t, c in timers.items():
    if t == 0:
      ntimers[6] += c
      ntimers[8] += c
    else:
      ntimers[t - 1] += c
  timers = ntimers

print(sum(timers.values()))

for i in range(256-80):
  ntimers = Counter()
  for t, c in timers.items():
    if t == 0:
      ntimers[6] += c
      ntimers[8] += c
    else:
      ntimers[t - 1] += c
  timers = ntimers

print(sum(timers.values()))

