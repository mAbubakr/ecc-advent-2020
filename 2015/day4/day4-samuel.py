#!/usr/bin/python3

from hashlib import md5
import sys

key = "bgvyzdsv"

n = 1
while True:
  res = md5(f"{key}{n}".encode("utf-8")).hexdigest()
  if res[:5] == "00000":
    print(n)
    break
  n += 1

n = 1
while True:
  res = md5(f"{key}{n}".encode("utf-8")).hexdigest()
  if res[:6] == "000000":
    print(n)
    break
  n += 1

