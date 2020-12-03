const passwords = [
    '1-3 a: abcde',
    '1-3 b: cdefg',
    '2-9 c: ccccccccc',
];

function passwordIsValid1(min, max, letter, password) {
    const occurences = Array.from(password)
                            .filter(c => c === letter)
                            .length;
    return min <= occurences && occurences <= max;
}

function passwordIsValid2(first, second, letter, password) {
    const zeroIndexedFirst = first - 1;
    const zeroIndexedSecond = second - 1;
    return (password.charAt(zeroIndexedFirst) === letter) ^
        (password.charAt(zeroIndexedSecond) === letter);
}

function countValidPasswords(passwords) {
    const entryRegex = /(\d+)-(\d+) (\w): (\w+)/;
    const validPasswords = passwords.filter(pw => {
        const entryMatch = pw.match(entryRegex);
        return passwordIsValid(
            entryMatch[1],
            entryMatch[2],
            entryMatch[3],
            entryMatch[4]);
    });
    
    return validPasswords.length;
}
