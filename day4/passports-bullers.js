const passports = batch.split(/^\s*$/m);

// same for part 1, just don't need everything after the colons
const requiredFields = [
  /byr:(19[2-9]\d|200[0-2])\s+/,
  /iyr:20(1\d|20)\s+/,
  /eyr:20(2\d|30)\s+/,
  /hgt:(1[5-8]\dcm|19[0-3]cm|59in|6\din|7[0-6]in)\s+/,
  /hcl:#[0-9a-f]{6}\s+/,
  /ecl:(amb|blu|brn|gry|grn|hzl|oth)\s+/,
  /pid:\d{9}\s+/,
];

function isValid(passport) {
  return requiredFields.every(field => passport.match(field));
}

passports.filter(isValid).length;
