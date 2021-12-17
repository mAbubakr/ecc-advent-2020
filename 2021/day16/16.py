import math
input_s = open('16.in', 'r').readline().strip()
input = str(bin(int(input_s, 16)))[2:]
while len(input) < 4*len(input_s):
    input = '0'+input
values = {}
p = 0
versiontotal = 0

def getPartString(n):
    global p
    val = input[p:p+n]
    p += n
    return val

def getPartInt(n):
    val = getPartString(n)
    return int(val, 2)

def parseNumber():
    val = ""
    keepreading = True
    while keepreading:
        current = getPartString(5)
        if current[0] == '0':
            keepreading = False
        val += current[1:]
    return int(val,2)

def parseOperator(typeid):
    lengthtype = getPartInt(1)
    numbers = []
    if lengthtype == 0:
        subpacketlength = getPartInt(15)
        end = p + subpacketlength
        while p < end:
            numbers.append(parsePacket())
    elif lengthtype == 1:
        subpacketcount = getPartInt(11)
        for i in range(subpacketcount):
            numbers.append(parsePacket())
    if typeid == 0:
        return sum(numbers)
    if typeid == 1:
        return math.prod(numbers)
    if typeid == 2:
        return min(numbers)
    if typeid == 3:
        return max(numbers)
    if typeid == 5:
        if numbers[0] > numbers[1]:
            return 1
        return 0
    if typeid == 6:
        if numbers[0] < numbers[1]:
            return 1
        return 0
    if typeid == 7:
        if numbers[0] == numbers[1]:
            return 1
        return 0

def parsePacket():
    global versiontotal
    version = getPartInt(3)
    versiontotal += version
    typeid = getPartInt(3)
    if typeid == 4:
        return parseNumber()
    else:
        return parseOperator(typeid)

answer = parsePacket()
print(f"answer a: {versiontotal}")
print(f"answer b: {answer}")