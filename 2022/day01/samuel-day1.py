#!/usr/bin/python3

amounts = []
amount = 0
for line in open("input", "r"):
  line = line.strip()
  if line:
    amount += int(line)
  else:
    amounts.append(amount)
    amount = 0

amounts.append(amount)
amounts.sort(reverse=True)
print(amounts[0])
print(amounts[0] + amounts[1] + amounts[2])

