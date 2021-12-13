from collections import defaultdict, deque
graph = defaultdict(list)
with open('input', 'r') as file1:
    for line in file1.readlines():
        x,y = line.strip().split('-')
        graph[x].append(y)
        graph[y].append(x)

def countPaths(allowRevisit = False):
    total = 0
    start = ['start', set(['start']), None]
    tracker = deque([start])
    while tracker:
        position, small, revisit = tracker.popleft()
        if position == 'end':
            total += 1
            continue
        for next in graph[position]:
            if next not in small:
                small2 = set(small)
                if next.islower():
                    small2.add(next)
                tracker.append((next, small2, revisit))
            elif allowRevisit and next in small and revisit is None and next not in ['start', 'end']:
                tracker.append((next, small, next))
    return total
print(f"answer a: {countPaths()}")
print(f"answer b: {countPaths(True)}")