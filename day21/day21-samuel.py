#!/usr/bin/python3

data = open("input", "r")

lines = [line.strip() for line in data]

foods = []

allin = set()
allall = set()

for line in lines:
  ingred, allergen = line.split(" (contains ")
  ingreds = set(ingred.split(" "))
  allergens = set(allergen[:-1].split(", "))
  foods.append([ingreds, allergens])
  allin.update(ingreds)
  allall.update(allergens)

allergens = {}

for allergen in allall:
  allset = set()
  for ing, aller in foods:
    if allergen in aller:
      if not allset:
        allset.update(ing)
      else:
        allset.intersection_update(ing)
  allergens[allergen] = allset

update = True
while update:
  update = False
  for allergen, ingred in allergens.items():
    if len(ingred) == 1:
      i1 = list(ingred)[0]
      for allergen2, ingred2 in allergens.items():
        if allergen != allergen2 and i1 in ingred2:
          ingred2.remove(i1)
          update = True

alist = [list(ingred)[0] for a, ingred in allergens.items()]
count = 0
for ing, a in foods:
  for al in alist:
    ing.discard(al)
  count += len(ing)

print(count)

alist = list(allergens.keys())
alist.sort()
print(",".join([list(allergens[a])[0] for a in alist]))

