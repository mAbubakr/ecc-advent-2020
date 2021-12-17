import re
input_s = "target area: x=185..221, y=-122..-74"
# input_s = "target area: x=20..30, y=-10..-5"
pattern = "^target area: x=(?P<x1>-?\\d+?)..(?P<x2>-?\\d+?), y=(?P<y2>-?\\d+?)..(?P<y1>-?\\d+?)$"
match = re.match(pattern, input_s)
x1 = int(match.group('x1'))
x2 = int(match.group('x2'))
y2 = int(match.group('y1'))
y1 = int(match.group('y2'))
print(f"x1:{x1},x2:{x2},y1:{y1},y2:{y2}")

top = 0
hitcount = 0
for maxx in range(x2 * 2):
    for maxy in range(-1000,1000):
        hit = False
        x = 0
        y = 0
        mx = maxx
        my = maxy
        maxh = 0
        for t in range(1000):
            x += mx
            y += my 
            maxh = max(maxh, y)
            if mx > 0:
                mx -= 1
            elif mx < 0:
                mx += 1
            my -= 1
            if (x1 <= x <= x2 and y1 <= y <= y2):
                hit = True
        if hit:
            hitcount += 1
            print(f"hit with ({maxx},{maxy}), top={top}, hitcount={hitcount}")
            if maxh > top:
                top = maxh

print(f"answer a: {top}")
print(f"answer b: {hitcount}")