#!/usr/bin/python3

data = open("input", "r")

data.readline()
deck1 = []
for line in data:
  line = line.strip()
  if not line:
    break
  deck1.append(int(line))

data.readline()
deck2 = []
for line in data:
  deck2.append(int(line))

decks = [deck1[:], deck2[:]]

while deck1 and deck2:
  n1 = deck1[0]
  deck1.remove(n1)
  n2 = deck2[0]
  deck2.remove(n2)
  if n1 > n2:
    deck1.append(n1)
    deck1.append(n2)
  else:
    deck2.append(n2)
    deck2.append(n1)

if deck1:
  deck = deck1
else:
  deck = deck2

deck.reverse()
print(sum([i * n for i, n in enumerate(deck, 1)]))

def play(deck1, deck2):
  history = []
  while deck1 and deck2:
    entry = (deck1[:], deck2[:])
    if entry in history:
      return 1, deck1
    history.append(entry)
    n1 = deck1[0]
    deck1.remove(n1)
    n2 = deck2[0]
    deck2.remove(n2)
    if len(deck1) >= n1 and len(deck2) >= n2:
      win, deck = play(deck1[:n1], deck2[:n2])
    else:
      if n1 > n2:
        win = 1
      else:
        win = 2
    if win == 1:
      deck1.append(n1)
      deck1.append(n2)
    else:
      deck2.append(n2)
      deck2.append(n1)
  if deck1:
    return 1, deck1
  return 2, deck2

win, deck = play(*decks)
deck.reverse()
print(sum([i * n for i, n in enumerate(deck, 1)]))

