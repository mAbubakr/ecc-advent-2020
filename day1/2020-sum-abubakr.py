# Simple and inefficient
def multiply_sum1(numbers, target):
    for first in numbers:
        for second in numbers:
            if first + second == target:
                return first * second
    return None

# A bit faster as we don't go over each element multiple times and simply look for x & x - target.
def multiply_sum2(numbers, target):
    for i in range(len(numbers)):
        first = numbers[i]
        if (target - first in numbers[i:]):
            return first * (target - first)
    return None

# Simple and inefficient
def multiply_triple_sum1(numbers, target):
    for first in numbers:
        for second in numbers:
            for third in numbers:
                if first + second + third == target:
                    return first * second * third
    return False

if __name__ == "__main__":
    input = open("day1/input").readlines()
    numbers = [int(x) for x in input]

    print(multiply_sum1(numbers, 2020))
    print(multiply_sum2(numbers, 2020))


    print(multiply_triple_sum1(numbers, 2020))