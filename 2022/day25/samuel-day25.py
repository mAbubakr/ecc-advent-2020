#!/usr/bin/python3

data = open("input", "r")

lines = [line.strip() for line in data]

nums = {"0": 0, "1": 1, "2": 2, "=": -2, "-": -1}
digits = {-2: "=", -1: "-", 0: "0", 1: "1", 2: "2"}
total = 0

for line in lines:
  accum = 0
  mult = 1
  for c in reversed(line):
    accum += nums[c] * mult
    mult *= 5
  total += accum

mult = 1
mults = []
while mult < total:
  mults.append(mult)
  mult *= 5

places = []
for m in reversed(mults):
  places.append(total // m)
  total %= m

places.reverse()

res = []

mult = 1
for i in range(len(places)):
  n = places[i]
  if n >= -2 and n <= 2:
    res.append(n)
  elif n >= 3:
    res.append(n-5)
    places[i+1] += 1
  else:
    print(f"invalid digit {n}")

print("".join([digits[n] for n in reversed(res)]))

