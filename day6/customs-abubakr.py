
def create_grouped_answers(answers):
    """
    :param answers:
    :return: a List of Tuples with grouped answers and the number of people who answered.
    """
    groups = []

    group = ""
    people = 0
    for answer in answers:
        if answer.strip() == "":
            groups.append((group, people))
            group = ""
            people = 0
        else:
            group += answer.strip()
            people += 1
    if group != "":
        groups.append((group, people))

    return groups


if __name__ == '__main__':
    answers = open('input').readlines()
    groups = create_grouped_answers(answers)

    sum_any = 0
    sum_all = 0
    for group in groups:
        unique_answers = set(group[0])
        sum_any += len(unique_answers)
        for answer in unique_answers:
            if list(group[0]).count(answer) == group[1]:
                sum_all += 1

    print(f'The sum of ANY YES answers from each group is {sum_any}')
    print(f'The sum of ALL YES answers from each group is {sum_all}')
