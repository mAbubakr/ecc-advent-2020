#!/usr/bin/python3

lines = [line.strip() for line in open("ssinput", "r")]

cards = []

total = 0
for line in lines:
  win, you = line.split(": ")[1].split(" | ")
  win = set([int(n) for n in win.split()])
  you = set([int(n) for n in you.split()])
  have = win.intersection(you)
  cards.append(len(have))
  if have:
    total += 1 << (len(have) - 1)

print(total)

numCards = len(cards)
counts = [1] * numCards

for i, card in enumerate(cards):
  count = counts[i]
  for j in range(i + 1, i + 1 + card):
    if j < numCards:
      counts[j] += count

print(sum(counts))

