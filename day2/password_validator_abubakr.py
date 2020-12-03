def count_valid_passwords_p1(rules):
    valid = 0

    for rule in rules:
        min, max = int(rule[0].split("-")[0]), int(rule[0].split("-")[1])
        search = rule[1].split(":")[0]
        password = rule[2]

        if min <= password.count(search) <= max:
            valid += 1

    return valid

def count_valid_passwords_p2(rules):
    valid = 0

    for rule in rules:
        first, second = int(rule[0].split("-")[0]), int(rule[0].split("-")[1])
        search = rule[1].split(":")[0]
        password = rule[2]

        if bool(password[first - 1] == search) ^ bool(password[second - 1] == search):
            valid += 1

    return valid


if __name__ == "__main__":
    input = open("day2/input")
    lines = input.readlines()
    password_rules = [tuple(line.split()) for line in lines]

    print("Part one count: {0}".format(count_valid_passwords_p1(password_rules)))
    print("Part two count: {0}".format(count_valid_passwords_p2(password_rules)))