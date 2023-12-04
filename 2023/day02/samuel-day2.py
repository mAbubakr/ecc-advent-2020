#!/usr/bin/python3

lines = [line.strip() for line in open("ssinput", "r")]

games = []

for line in lines:
  game = []
  for r in line.split(": ")[1].split("; "):
    res = [0, 0, 0]
    for m in r.split(", "):
      n, c = m.split(" ")
      if c == "red":
        res[0] = int(n)
      elif c == "green":
        res[1] = int(n)
      elif c == "blue":
        res[2] = int(n)
    game.append(res)
  games.append(game)

total = 0
for i, game in enumerate(games):
  for res in game:
    if res[0] > 12 or res[1] > 13 or res[2] > 14:
      break
  else:
    total += i + 1

print(total)

total = 0
for game in games:
  res = [max([r[0] for r in game]), max([r[1] for r in game]), max([r[2] for r in game])]
  total += res[0] * res[1] * res[2]

print(total)

