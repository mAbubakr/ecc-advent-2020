#!/usr/bin/python3

from itertools import combinations

boss_hp = 100
boss_dmg = 8
boss_arm = 2

weapons = [(8, 4), (10, 5), (25, 6), (40, 7), (74, 8)]
armors = [(0, 0), (13, 1), (31, 2), (53, 3), (75, 4), (102, 5)]
rings = [(0, 0, 0), (0, 0, 0), (25, 1, 0), (50, 2, 0), (100, 3, 0), (20, 0, 1), (40, 0, 2), (80, 0, 3)]

"""
Weapons:    Cost  Damage  Armor
Dagger        8     4       0
Shortsword   10     5       0
Warhammer    25     6       0
Longsword    40     7       0
Greataxe     74     8       0

Armor:      Cost  Damage  Armor
Leather      13     0       1
Chainmail    31     0       2
Splintmail   53     0       3
Bandedmail   75     0       4
Platemail   102     0       5

Rings:      Cost  Damage  Armor
Damage +1    25     1       0
Damage +2    50     2       0
Damage +3   100     3       0
Defense +1   20     0       1
Defense +2   40     0       2
Defense +3   80     0       3
"""

def fight(dmg, arm):
  Php = 100
  Pdmg = max(dmg - boss_arm, 1)
  Bhp = boss_hp
  Bdmg = max(boss_dmg - arm, 1)
  while True:
    Bhp -= Pdmg
    if Bhp <= 0:
      return True
    Php -= Bdmg
    if Php <= 0:
      return False

wins = set()
loss = set()
for wc, wdmg in weapons:
  for ac, aarm in armors:
    for (rc1, rdmg1, rarm1), (rc2, rdmg2, rarm2) in combinations(rings, 2):
      cost = wc + ac + rc1 + rc2
      if fight(wdmg + rdmg1 + rdmg2, aarm + rarm1 + rarm2):
        wins.add(cost)
      else:
        loss.add(cost)

print(min(list(wins)))
print(max(list(loss)))

