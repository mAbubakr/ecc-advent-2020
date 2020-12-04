import re, logging


def read_passports_batch(batch_file):
    batch_passports = []
    passport = {}
    for line in batch_file.readlines():
        if line != "\n":
            for x in line.split():
                field, value = x.split(":")
                passport[field] = value
        else:
            batch_passports.append(passport.copy())
            passport = {}

    if passport != {}:
        batch_passports.append(passport)

    return batch_passports


def validate_passports(passports_to_validate, required_fields, rules):
    valid1 = 0
    valid2 = 0
    for passport in passports_to_validate:
        if all(key in passport for key in required_fields):
            valid1 += 1
            if all(rule(passport) for rule in rules):
                valid2 += 1
            else:
                logging.debug("Failed to verify {0} due to failing: {1}"
                              .format(passport,
                                      [rule.__name__ for rule in rules if not rule(passport)]))
        else:
            logging.debug("Failed to verify {0} because it is missing {1}"
                          .format(passport, set(required_fields) - passport.keys()))
            pass

    return valid1, valid2


def valid_byr(passport):
    return "byr" in passport and 1920 <= int(passport["byr"]) <= 2002


def valid_iyr(passport):
    return "iyr" in passport and 2010 <= int(passport["iyr"]) <= 2020


def valid_eyr(passport):
    return "eyr" in passport and 2020 <= int(passport["eyr"]) <= 2030


def valid_hgt(passport):
    if "hgt" in passport:
        hgt = passport["hgt"]
        if "cm" in hgt:
            return 150 <= int(hgt[:-2]) <= 193
        elif "in" in hgt:
            return 59 <= int(hgt[:-2]) <= 76
    return False


def valid_hcl(passport):
    return "hcl" in passport and bool(re.match(r'^#[0-9|a-f]{6}$', passport["hcl"]))


def valid_ecl(passport):
    return "ecl" in passport and bool(re.match(r'^(amb)|(blu)|(brn)|(gry)|(grn)|(hzl)|(oth)$', passport["ecl"]))


def valid_pid(passport):
    return "pid" in passport and bool(re.match(r'^\d\d\d\d\d\d\d\d\d$', passport["pid"]))


if __name__ == '__main__':
    logging.basicConfig(level=logging.DEBUG)

    required_fields = ["byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"]
    rules = [valid_byr, valid_iyr, valid_eyr, valid_hgt, valid_hcl, valid_ecl,
             valid_pid]

    passports = read_passports_batch(open("input"))
    valid = validate_passports(passports, required_fields, rules)
    print("Valid passports for p1: {0}".format(valid[0]))
    print("Valid passports for p2: {0}".format(valid[1]))
