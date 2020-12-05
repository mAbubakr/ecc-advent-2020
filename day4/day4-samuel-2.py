#!/usr/bin/python3

import re

data = open("input", "r").read()

fields1 = [re.compile(r) for r in ["byr:", "iyr:", "eyr:", "hgt:", "hcl:", "ecl:", "pid:"]]
fields2 = [re.compile(r) for r in [
    "byr:(19[2-9]\d|200[0-2])\s",
    "iyr:20(1\d|20)\s",
    "eyr:20(2\d|30)\s",
    "hgt:((1[5-8]\d|19[0-3])cm|(59|6\d|7[0-6])in)\s",
    "hcl:#[0-9a-f]{6}\s",
    "ecl:(amb|blu|brn|gry|grn|hzl|oth)\s",
    "pid:\d{9}\s"
    ]]

valid1 = 0
valid2 = 0
for passport in re.split("^$", data, flags=re.M):
  if all([r.search(passport) for r in fields1]):
    valid1 += 1
    if all([r.search(passport) for r in fields2]):
      valid2 += 1

print(f"{valid1} {valid2}")

