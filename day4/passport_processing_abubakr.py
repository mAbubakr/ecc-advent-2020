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


def validate_passports(passports_to_validate, required_fields):
    valid = 0
    for passport in passports_to_validate:
        if all(key in passport for key in required_fields):
            valid += 1
        else:
            print("Failed to verify {0} because it is missing {1}"
                  .format(passport, set(required_fields) - passport.keys()))

    return valid

if __name__ == '__main__':
    required_fields = ["byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"]

    passports = read_passports_batch(open("input"))
    print("Valid passports for p1: {0}"
          .format(validate_passports(passports, required_fields)))
