import copy


def exec_commands(commands):
    command = commands[0]
    status = "failed"
    acc = 0
    line = 0

    while command[1] == 0 and line != len(commands):
        command[1] += 1
        c, n = command[0].split()
        if c == 'acc':
            acc += int(n)
            line = (line + 1) % (len(commands) + 1)
        elif c == 'nop':
            line = (line + 1) % (len(commands) + 1)
        elif c == 'jmp':
            line = (line + int(n)) % (len(commands) + 1)
        command = commands[line % len(commands)]

    if line == len(commands):
        status = "success"

    return (status, acc)


if __name__ == '__main__':
    commands = [[c.strip(), 0] for c in open("input", 'r').readlines()]

    line = 0
    status = "failed"
    while status == 'failed':
        updated_commands = copy.deepcopy(commands)
        c, n = updated_commands[line][0].split()
        if (c == 'jmp'):
            updated_commands[line][0] = 'nop' + ' ' + n
        elif (c == 'nop'):
            updated_commands[line][0] = 'jmp' + ' ' + n
        line += 1
        status, acc = exec_commands(updated_commands)

    print(f'Value of acc right before infinite loop is {exec_commands(commands)}')
    print(f'Value of acc after successful execution is {acc}')
