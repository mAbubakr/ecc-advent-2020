#!/usr/bin/python3

from itertools import product
from math import prod

data = open("input", "r")

ts = int(data.readline())
busline = data.readline().strip()
buses = [int(n) for n in busline.split(",") if n != "x"]
buses.sort()

diffs = [n - (ts % n) for n in buses]

wait = min(diffs)
pos = diffs.index(wait)

print(buses[pos] * wait)

buses = busline.split(",")
busdata = [(i, int(bus)) for i, bus in enumerate(buses) if bus != "x"]
allmult = prod([b[1] for b in busdata])
total = 0
for i, bus in busdata:
  tmult = allmult // bus
  inv = pow(tmult, -1, bus)
  mult = (inv * (bus - i)) % bus
  total += tmult * mult

total %= allmult
print(total)

