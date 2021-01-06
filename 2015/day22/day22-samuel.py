#!/usr/bin/python3

boss_hp = 51
boss_dmg = 9

totals = set()

"""
    Magic Missile costs 53 mana. It instantly does 4 damage.
    Drain costs 73 mana. It instantly does 2 damage and heals you for 2 hit points.
    Shield costs 113 mana. It starts an effect that lasts for 6 turns. While it is active, your armor is increased by 7.
    Poison costs 173 mana. It starts an effect that lasts for 6 turns. At the start of each turn while it is active, it deals the boss 3 damage.
    Recharge costs 229 mana. It starts an effect that lasts for 5 turns. At the start of each turn while it is active, it gives you 101 new mana.
"""

def fight_p(dmg, Php, mana, total, Bhp, shield, poison, recharge):
  global minmana
  Php -= dmg
  if Php <= 0:
    return
  if shield:
    shield -= 1
  if poison:
    poison -= 1
    Bhp -= 3
  if Bhp <= 0:
    if minmana is None or total < minmana:
      minmana = total
    return
  if recharge:
    recharge -= 1
    mana += 101
  if mana >= 53:
    fight_b(Php, mana - 53, total + 53, Bhp - 4, shield, poison, recharge)
  if mana >= 73:
    fight_b(Php + 2, mana - 73, total + 73, Bhp - 2, shield, poison, recharge)
  if mana >= 113 and not shield:
    fight_b(Php, mana - 113, total + 113, Bhp, 6, poison, recharge)
  if mana >= 173 and not poison:
    fight_b(Php, mana - 173, total + 173, Bhp, shield, 6, recharge)
  if mana >= 229 and not recharge:
    fight_b(Php, mana - 229, total + 229, Bhp, shield, poison, 5)

def fight_b(Php, mana, total, Bhp, shield, poison, recharge):
  global minmana
  if poison:
    poison -= 1
    Bhp -= 3
  if recharge:
    recharge -= 1
    mana += 101
  if Bhp <= 0:
    if minmana is None or total < minmana:
      minmana = total
    return
  if minmana is not None and total > minmana:
    return
  if shield:
    shield -= 1
    Php -= 2
  else:
    Php -= 9
  if Php <= 0:
    return
  states.append((Php, mana, total, Bhp, shield, poison, recharge))

minmana = None
states = [(50, 500, 0, boss_hp, 0, 0, 0)]
while states:
  work = states
  states = []
  for state in work:
    fight_p(0, *state)

print(minmana)

minmana = None
states = [(50, 500, 0, boss_hp, 0, 0, 0)]
while states:
  work = states
  states = []
  for state in work:
    fight_p(1, *state)

print(minmana)


