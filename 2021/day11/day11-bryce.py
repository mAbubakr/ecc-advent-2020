grid = []
total = 0
with open('input', 'r') as file1:
    for fileline in file1.readlines():
        grid.append([int(s) for s in list(fileline.strip())])
X = len(grid)
Y = len(grid[0])

def flash(x, y):
    global total
    total += 1
    grid[x][y] = -1
    for xmod in [-1, 0, 1]:
        for ymod in [-1, 0, 1]:
            xn = x + xmod
            yn = y + ymod
            if (0<=xn<X) and (0<=yn<Y) and grid[xn][yn] != -1:
                grid[xn][yn] += 1
                if grid[xn][yn] >= 10:
                    flash(xn, yn)
round = 0
while True:
    round += 1
    grid = [[x + 1 for x in row] for row in grid]
    for i in range(Y):
        for j in range(X):
            if (grid[i][j] == 10):
                flash(i,j)
    done = True
    for i in range(Y):
        for j in range(X):
            if (grid[i][j] == -1):
                grid[i][j] = 0
            else: # at least one didn't flash
                done = False
    if (round == 100):
        print(f"answer a: {total}")
    if done:
        break
print(f"answer b: {round}")