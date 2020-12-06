def binary_to_seating(bin_number):
    seat_row = bin_number[2:9].replace('1', 'B').replace('0', 'F')
    seat_column = bin_number[9:].replace('1', 'R').replace('0', 'L')
    return seat_row + seat_column


def seating_to_binary(seating):
    return '0b' + seating.replace('B', '1').replace('F', '0').replace('R', '1').replace('L', '0')


if __name__ == '__main__':
    seats = [seat.strip() for seat in open("input").readlines()]
    highest_ID = int(max(list(map(seating_to_binary, seats))), 2)
    print(f"Highest seat found is {binary_to_seating(format(highest_ID, '#012b'))} with ID {highest_ID}")
