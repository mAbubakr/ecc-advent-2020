#!/usr/bin/python3

#x=135..155, y=-102..-78
x1 = 135
x2 = 155
y1 = -78
y2 = -102

maxh = 0
count = 0

for idy in range(y2, -y2+1):
  for idx in range(x2+1):
    x, y = 0, 0
    dx, dy = idx, idy
    tmaxh = 0
    while x <= x2 and y >= y2:
      if x >= x1 and y <= y1:
        if tmaxh > maxh:
          maxh = tmaxh
        count += 1
        break
      if y > tmaxh:
        tmaxh = y
      x += dx
      y += dy
      if dx:
        dx -= 1
      dy -= 1

print(maxh)
print(count)

