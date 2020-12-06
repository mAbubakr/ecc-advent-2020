def seating_to_binary(seating):
    # Since each digit can be one of two options, treat the entire seat string as a binary number
    # We know that Back is higher half of IDs and F is lower half. So with binary, this maps to 1 for B and 0 for F
    # Similarly, R is higher and L is lower, since we can see the layout starting from top left to bottom right
    return '0b' + seating.replace('B', '1').replace('F', '0').replace('R', '1').replace('L', '0')


def binary_to_seating(bin_number):
    seat_row = bin_number[2:9].replace('1', 'B').replace('0', 'F')
    seat_column = bin_number[9:].replace('1', 'R').replace('0', 'L')
    return seat_row + seat_column


if __name__ == '__main__':
    seats = [seat.strip() for seat in open("input").readlines()]
    seat_ids = [int(seat, 2) for seat in list(map(seating_to_binary, seats))]  # Convert seats to binary then to ints

    highest_ID = max(seat_ids)  # Simple to just find max now that we have all IDs as ints
    print(f"Highest seat found is {binary_to_seating(format(highest_ID, '#012b'))} with ID {highest_ID}")

    # If we follow the rules of binary but using BFRL, we know min = 0 and highest = 1111111111 for our length of 10
    # So, then we know our min is 0 and max is 1023 in decimal.
    # The missing seat is simply just one not in the list and it's next and previous seat is.
    for seat in range(1024):
        if seat not in seat_ids and (seat + 1 in seat_ids) and (seat - 1 in seat_ids):
            print(f'Your missing seat is {binary_to_seating(format(seat, "#012b"))} with ID {seat}')
