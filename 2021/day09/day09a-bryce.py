grid = []
total = 0
with open('input', 'r') as file1:
    for fileline in file1.readlines():
        line = [int(s) for s in list(fileline.strip())]
        grid.append(line)
xmax = len(grid)
ymax = len(grid[0])
for x in range(xmax):
    for y in range(ymax):
        current = grid[x][y]
        if (x >= 1):
            if (current >= grid[x-1][y]):
                continue
        if (y >= 1):
            if (current >= grid[x][y-1]):
                continue
        if (x < xmax - 1):
            if (current >= grid[x+1][y]):
                continue
        if (y < ymax - 1):
            if (current >= grid[x][y+1]):
                continue
        print(f"found low point at [{x}][{y}]: {grid[x][y]}")
        total += current + 1
print(f"total: {total}")