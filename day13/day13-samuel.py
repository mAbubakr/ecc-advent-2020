#!/usr/bin/python3

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
busnums = []
busindex = []
busmults = []
for i in range(len(buses)):
  bus = buses[i]
  if bus != "x":
    bus = int(bus)
    busnums.append(bus)
    busindex.append(i)


def docalc(n1, m1, m2, diff):
  if m1 > m2:
    inv = pow(m2-m1, -1, m2)
    mult = inv * (diff + n1) % m2
    num1 = n1 + m1 * mult
    num2 = num1 + diff
  else:
    inv = pow(m2-m1, -1, m1)
    mult = inv * (diff + n1) % m1
    num2 = m2 * mult
    num1 = num2 - diff
  return (m1 * m2, num1, num2)

busmults = []
pindex = 0
for i in range(len(busnums)):
  index = busindex[i]
  busmults.append((busnums[i], index - pindex))
  pindex = index

m1 = busnums[0]
b1 = 0
for i in range(1, len(busmults)):
  m2, diff = busmults[i]
  m1, x, b1 = docalc(b1, m1, m2, diff)
print(b1 - busindex[-1])

