#!/usr/bin/python3

import re

data = open("input", "r")

valid1 = 0
valid2 = 0
fields = {}
for line in data:
  line = line.strip()
  if not line:
    isValid = True
    if len(fields) == 8 or (len(fields) == 7 and "cid" not in fields):
      valid1 += 1
      byr = int(fields["byr"])
      if byr < 1920 or byr > 2002:
        isValid = False
      iyr = int(fields["iyr"])
      if iyr < 2010 or iyr > 2020:
        isValid = False
      eyr = int(fields["eyr"])
      if eyr < 2020 or eyr > 2030:
        isValid = False
      height = fields["hgt"]
      unit = height[-2:]
      if unit == "cm":
        num = int(height[:-2])
        if num < 150 or num > 193:
          isValid = False
      elif unit == "in":
        num = int(height[:-2])
        if num < 59 or num > 76:
          isValid = False
      else:
        isValid = False
      if not re.fullmatch("#[0-9a-f]{6}", fields["hcl"]):
        isValid = False
      if not fields["ecl"] in ["amb", "blu", "brn", "gry", "grn", "hzl", "oth"]:
        isValid = False
      if not re.fullmatch("\d{9}", fields["pid"]):
        isValid = False
    else:
      isValid = False
    if isValid:
      valid2+= 1
    fields = {}
    continue
  for item in line.split(" "):
    info = item.split(":")
    fields[info[0]] = info[1]

print(f"{valid1} {valid2}")

