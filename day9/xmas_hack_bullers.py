with open('day9/input', 'r') as f:
    nums = [int(line) for line in f]

def non_sum(nums, preamble_len):
    preamble = nums[:preamble_len]
    sums = [x + y for x in preamble for y in preamble]
    curr = nums[preamble_len]

    return curr if curr not in sums else non_sum(nums[1:], preamble_len)

print(non_sum(nums, 25))

def contiguous_nums(nums, target, end):
    potential_range = nums[:end]
    range_sum = sum(potential_range)

    if range_sum == target:
        return potential_range
    elif range_sum < target:
        return contiguous_nums(nums, target, end + 1)
    else:
        return contiguous_nums(nums[1:], target, end - 1)

weak_range = contiguous_nums(nums, non_sum(nums, 25), 1)
print(min(weak_range) + max(weak_range))
