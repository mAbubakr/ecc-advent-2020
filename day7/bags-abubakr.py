def contains_target_bag(inner_bags, target):
    for child in inner_bags:
        if child == target:
            return True
        if contains_target_bag(bags[child], target):
            return True
    return False

def count_nested_bags(inner_bags):
    if inner_bags == {}:
        return 0
    count = 0
    for bag in inner_bags:
        count += (count_nested_bags(bags[bag]) + 1) * inner_bags[bag]
    return count

if __name__ == '__main__':
    target = "shiny gold"
    end = "no other bags"

    bags = {}
    with open("input", 'r') as file:
        for line in file.readlines():
            words = line.split()
            key = words[0] + ' ' + words[1]
            bags[key] = {}
            for i, word in enumerate(words):
                if words[i].isnumeric():
                    inner_bag = words[i + 1] + ' ' + words[i + 2]
                    bags[key][inner_bag] = int(word)

    found = []
    for bag in bags:
        if contains_target_bag(bags[bag], target):
            found.append(bag)

    print(f'Number of bags that may hold {target} is {len(found)}')
    print(f'Number of bags needed for a {target} bag is {count_nested_bags(bags[target])}')
