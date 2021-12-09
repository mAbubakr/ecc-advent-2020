#!/usr/bin/python3

import itertools

infile = open("input", "r")
nums = [int(n) for n in infile.readline().split(",")]

boards = []
while infile.readline():
  board = []
  for i in range(5):
    line = infile.readline()
    row = []
    for j in range(5):
      row.append(int(line[j*3:j*3+2]))
    board.append(row)
  boards.append(board)

def winner(board):
  for row in board:
    if sum(row) == 0:
      return True
  for i in range(5):
    if sum([row[i] for row in board]) == 0:
      return True
  return False

def total(board):
  return sum(itertools.chain(*board))

first = True
for n in nums:
  for board in boards[:]:
    for row in board:
      if n in row:
        row[row.index(n)] = 0
        if winner(board):
          if first or len(boards) == 1:
            print(n * total(board))
            first = False
          boards.remove(board)
  if not boards:
    break

