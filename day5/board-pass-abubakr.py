def binary_to_seating(bin_number):
    seat_row = bin_number[2:9].replace('1', 'B').replace('0', 'F')
    seat_column = bin_number[9:].replace('1', 'R').replace('0', 'L')
    return seat_row + seat_column


if __name__ == '__main__':
    seats = [seat.strip() for seat in open("input").readlines()]
    for i in range(1023, -1, -1):
        seat = binary_to_seating(format(i, '#012b'))
        if seat in seats:
            print(f"Highest seat found is {seat} with ID {i}")
            break
