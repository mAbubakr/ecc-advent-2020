from collections import Counter

with open('day11/input') as f:
    seat_plan = [list(line.strip()) for line in f]

ROWS = len(seat_plan)
COLS = len(seat_plan[0])
NEIGHBOR_OFFSETS = [[x, y] for x in [-1, 0, 1] for y in [-1, 0, 1] if x or y]

# Part One

def occupied_neighbors(seats, x, y):
    neighbor_positions = [[x + dx, y + dy] for dx, dy in NEIGHBOR_OFFSETS]
    num_occupied = 0
    for neighbor_x, neighbor_y in neighbor_positions:
        if 0 <= neighbor_x < COLS and 0 <= neighbor_y < ROWS:
            neighbor = seats[neighbor_y][neighbor_x]
            if neighbor == '#':
                num_occupied += 1
    
    return num_occupied

def next_seat_state(seats, x, y):
    if seats[y][x] == '.':
        return '.'
    
    occupied_neigbors = occupied_neighbors(seats, x, y)
    if occupied_neigbors == 0:
        return '#'
    elif occupied_neigbors >= 4:
        return 'L'
    else:
        return seats[y][x]

def step(seats):
    next_seats = [row[:] for row in seats]

    for y, row in enumerate(seats):
        for x, seat in enumerate(row):
            next_seats[y][x] = next_seat_state(seats, x, y)
    
    return next_seats

def simulate(seats, prev_seats):
    if seats == prev_seats:
        return seats
    else:
        next_seats = step(seats)
        return simulate(next_seats, seats)

result = simulate(seat_plan, None)
seat_state = Counter()
for row in result:
    seat_state.update(row)
print(seat_state['#'])


# Part Two

def search_for_seat(seats, x, dx, y, dy):
    for factor in range(1, max(ROWS, COLS)):
        potential_x, potential_y = x + dx * factor, y + dy * factor
        if 0 <= potential_x < COLS and 0 <= potential_y < ROWS:
            potential_neighbor = seats[potential_y][potential_x]
            if potential_neighbor != '.':
                return potential_neighbor
        else:
            return None

def occupied_neighbors(seats, x, y):
    occupied_neighbors = 0
    for dx, dy in NEIGHBOR_OFFSETS:
        neighbor = search_for_seat(seats, x, dx, y, dy)
        if neighbor == '#':
            occupied_neighbors += 1
    
    return occupied_neighbors

def next_seat_state(seats, x, y):
    if seats[y][x] == '.':
        return '.'
    
    occupied_neigbors = occupied_neighbors(seats, x, y)
    if occupied_neigbors == 0:
        return '#'
    elif occupied_neigbors >= 5:
        return 'L'
    else:
        return seats[y][x]

def step(seats):
    next_seats = [row[:] for row in seats]

    for y, row in enumerate(seats):
        for x, seat in enumerate(row):
            next_seats[y][x] = next_seat_state(seats, x, y)
    
    return next_seats

def simulate(seats, prev_seats):
    if seats == prev_seats:
        return seats
    else:
        next_seats = step(seats)
        return simulate(next_seats, seats)

result = simulate(seat_plan, None)
seat_state = Counter()
for row in result:
    seat_state.update(row)
print(seat_state['#'])
