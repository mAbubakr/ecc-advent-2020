import itertools
spots = []
steps = []

with open('input', 'r') as file1:
    for line in file1.readlines():
        line = line.strip()
        if (line == ""):
            continue
        if ',' in line:
            spots.append([int(x) for x in list(line.split(','))])
        else:
            steps.append((line.split(" "))[2])
count = 0
for step in steps:
    count += 1
    c, n = step.split('=')
    n = int(n)
    for spot in spots:
        if c == 'y':
            if spot[1] > n:
                spot[1] = spot[1] - ((spot[1] - n) * 2)                
        elif c == 'x':
            if spot[0] > n:
                spot[0] = spot[0] - ((spot[0] - n) * 2)                
    spots.sort()
    spots = list(spots for spots,_ in itertools.groupby(spots))
    if (count == 1):
        print(f"answer a: {len(spots)}")
X = 0
Y = 0
for spot in spots:
    if spot[0] > X:
        X = spot[0]
    if spot[1] > Y:
        Y = spot[1]
grid = [['.' for x in range(X+1)] for y in range(Y+1)]
for spot in spots:
    grid[spot[1]][spot[0]] = '#'
print("answer b:")
for line in grid:
    print(''.join(line))