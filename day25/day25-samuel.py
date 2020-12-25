#!/usr/bin/python3

from itertools import count

code1 = 10604480
code2 = 4126658
modulo = 20201227

val = 1
for i in count():
  val *= 7
  if val >= modulo:
    val %= modulo
  if val == code1:
    loop1 = i + 1
    break

val = 1
for i in count():
  val *= 7
  if val >= modulo:
    val %= modulo
  if val == code2:
    loop2 = i + 1
    break

#print(loop1, loop2)

val = 1
for i in range(loop1):
  val = val * code2 % modulo
print(val)

val = 1
for i in range(loop2):
  val = val * code1 % modulo
#print(val)

