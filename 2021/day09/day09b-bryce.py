grid = []
with open('input', 'r') as file1:
    for fileline in file1.readlines():
        line = [int(s) for s in list(fileline.strip())]
        grid.append(line)
xmax = len(grid)
ymax = len(grid[0])
checked = [[False for i in range(ymax)] for x in range(xmax)]

def checkSpot(x, y):
    if (checked[x][y]):
        return 0
    checked[x][y] = True
    if (grid[x][y] == 9):
        checked[x][y] = True
        return 0
    value = 1
    if (x >= 1):
        value += checkSpot(x-1,y)
    if (y >= 1):
        value += checkSpot(x,y-1)
    if (x < xmax - 1):
        value += checkSpot(x+1,y)
    if (y < ymax - 1):
        value += checkSpot(x,y+1)
    return value

basins = []
for x in range(xmax):
    for y in range(ymax):
        val = checkSpot(x, y)
        if (val > 0):
            basins.append(val)
basins = sorted(basins, reverse=True)
total = basins[0] * basins[1] * basins[2]
print(f"answer: {total}")