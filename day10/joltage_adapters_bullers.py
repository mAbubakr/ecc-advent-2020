from collections import Counter
import itertools

with open('day10/input') as f:
    input = [int(line) for line in f]

joltage_ratings = sorted([0] + input + [max(input) + 3])

difference_counts = Counter(
    [y - x for x, y in zip(joltage_ratings, joltage_ratings[1:])])

print(difference_counts[1] * difference_counts[3])

def tribonacci():
    a, b, c = 0, 1, 1
    while True:
        yield a
        a, b, c = (b, c, a + b + c)

def arrangements(ratings):
    run_len = 1
    result = 1
    for i, curr in enumerate(ratings):
        more = ratings[i + 1:]
        if not more:
            return result
        elif more[0] - curr == 1:
            run_len += 1
        else:
            nth_trib = next(itertools.islice(tribonacci(), run_len, None))
            result = result * nth_trib
            run_len = 1

print(arrangements(joltage_ratings))
