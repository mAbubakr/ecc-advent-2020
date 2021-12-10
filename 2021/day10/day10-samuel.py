#!/usr/bin/python3

lines = [line.strip() for line in open("input", "r")]

openc = "([{<"
closec = ")]}>"
values = [3, 57, 1197, 25137]

score = 0
incomplete = []
for line in lines:
  stack = []
  for c in line:
    if c in "([{<":
      stack.append(c)
    else:
      m = stack.pop()
      if openc[closec.index(c)] != m:
        score += values[closec.index(c)]
        break
  else:
    incomplete.append((line, stack))

print(score)

scores = []
for line, stack in incomplete:
  score = 0
  while stack:
    c = stack.pop()
    score = score * 5 + openc.index(c) + 1
  scores.append(score)

scores.sort()
print(scores[len(scores) // 2])
  
