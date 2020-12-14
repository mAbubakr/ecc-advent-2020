def find_first_fail(code, step):
    for i in range(step, len(code)):
        if not sum_in(code[i - step:i], code[i]):
            return code[i]
    return -1


def sum_in(numbers, target_sum):
    for c, n in enumerate(numbers):
        if abs(n - target_sum) in (numbers[c:]):
            return True

    return False


def find_contiguous_sum(numbers, target):
    for c, n in enumerate(numbers):
        sum = n
        for c1, n1 in enumerate(numbers[c+1:]):
            sum += n1
            if sum == target:
                return numbers[c:c + c1]
            elif sum > target:
                break
    return []


def find_encryption_weakness(numbers, fail):
    contiguous_sum = find_contiguous_sum(numbers, fail)
    return min(contiguous_sum) + max(contiguous_sum)


if __name__ == '__main__':
    code = [int(i) for i in open("input", 'r').readlines()]

    fail = find_first_fail(code, 25)
    print(f'First number to fail preamble check is {fail}.')

    print(f'Encryption weakness using {fail} is {find_encryption_weakness(code, fail)}')
