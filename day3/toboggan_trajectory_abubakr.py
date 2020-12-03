def count_tree_collisions(world, right, down, tree):
    collisions = 0
    x = 0
    y = 0
    start = world[x][y]

    while y < len(world) - 1:
        x += right
        if x > (len(world[y]) - 1):
            x = x - len(world[y])
        
        y += down
        if (world[y][x] == tree):
            collisions += 1

    return collisions


if __name__ == "__main__":
    input = open("day3/input")
    world = [list(row.strip()) for row in input.readlines()]
    print(len(world), len(world[0]))
    print("Collisions with trees p1: {0}".format(count_tree_collisions(world, 3, 1, '#')))

    part_2_slopes = [[1, 1], [3, 1], [5, 1], [7, 1], [1, 2]]

    result = 1
    for right, down in part_2_slopes:
        result *= count_tree_collisions(world, right, down, '#')

    print("Collisions with trees multiplied p2: {0}".format(result))