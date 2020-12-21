#!/usr/bin/python3

from itertools import chain

data = open("input", "r")

class Tile(object):
  def __init__(self, num, data):
    self.num = num
    row0 = list(data[0])
    row0r = row0[:]
    row0r.reverse()
    row1 = [row[-1] for row in data]
    row1r = row1[:]
    row1r.reverse()
    row2r = list(data[-1])
    row2 = row2r[:]
    row2.reverse()
    row3r = [row[0] for row in data]
    row3 = row3r[:]
    row3.reverse()
    self.rows = [row0, row1, row2, row3]
    self.rowsr =[row0r, row3r, row2r, row1r]
    self.pos = False
    self.next = [None] * 4
    self.data = [list(row)[1:-1] for row in data[1:-1]]
    #self.data = [list(row) for row in data]

  def getData(self):
    data = self.data
    if self.flip:
      for row in data:
        row.reverse()
    if self.rot == 0:
      return data
    if self.rot == 1:
      data = [[data[r][i] for r in range(len(data))] for i in range(len(data[0]))]
      for row in data:
        row.reverse()
      return data
    if self.rot == 2:
      data.reverse()
      for row in data:
        row.reverse()
      return data
    if self.rot == 3:
      data = [[data[r][i] for r in range(len(data))] for i in range(len(data[0]))]
      data.reverse()
      return data

  def getRow(self, side):
    side = (side - self.rot) % 4
    if self.flip:
      return self.rowsr[side]
    return self.rows[side]

  def lock(self):
    self.pos = True
    self.rot = 0
    self.flip = False

  def locked(self):
    return self.pos

  def findRow(self, row, side):
    row = row[:]
    row.reverse()
    if not self.pos:
      if row in self.rows:
        pos = self.rows.index(row)
        self.rot = (side - pos) % 4
        self.flip = False
        self.pos = True
        return True
      if row in self.rowsr:
        pos = self.rowsr.index(row)
        self.rot = (side - pos) % 4
        self.flip = True
        self.pos = True
        return True
      return False
    side = (side - self.rot) % 4
    if not self.flip:
      return self.rows[side] == row
    return self.rowsr[side] == row

  def setNext(self, side, tile):
    self.next[side] = tile

  def getNext(self, side):
    return self.next[side]

tiles = []
while True:
  line = data.readline()
  if not line:
    break
  tnum = int(line[5:-2])
  tile = []
  for line in data:
    line = line.strip()
    if not line:
      break
    tile.append(line)
  tiles.append(Tile(tnum, tile))

start = tiles[0]
start.lock()

def scanRow(start, side):
  tile = start
  mside = (side + 2) % 4
  while True:
    row = tile.getRow(side)
    for ntile in tiles:
      if not ntile.locked():
        if ntile.findRow(row, mside):
          tile.setNext(side, ntile)
          ntile.setNext(mside, tile)
          tile = ntile
          break
    else:
      break
  return tile

scanRow(start, 0)
tile = scanRow(start, 2)
start = tile

lEdge = []
rEdge = []
while tile != None:
  rEdge.insert(0, scanRow(tile, 1))
  lEdge.insert(0, scanRow(tile, 3))
  tile = tile.getNext(0)

print(lEdge[0].num * lEdge[-1].num * rEdge[0].num * rEdge[-1].num)


combined = []
for tile in lEdge:
  tData = None
  while tile is not None:
    data = tile.getData()
    if tData is None:
      tData = data
    else:
      for td, d in zip(tData, data):
        td += d
    tile = tile.getNext(1)
  combined += tData

# my data set needed reversing to scan
for row in combined:
  row.reverse()
  #print("".join(row))

count = 0
while True:
  for y in range(len(combined) - 2):
    for x in range(len(combined[y]) - 20):
      # 3 humps
      for i in range(3):
        if combined[y+1][x] != "#" or combined[y+1][x+5] != "#" or combined[y+2][x+1] != "#" or combined[y+2][x+4] != "#":
          break
        x += 6
      else:
        # check for the head
        if combined[y][x] == "#" and combined[y+1][x] == "#" and combined[y+1][x+1] == "#":
          count += 1
  if count:
    break
  combined = [[combined[r][i] for r in range(len(combined))] for i in range(len(combined[0]))]
  for row in combined:
    row.reverse()

print(list(chain(*combined)).count("#") - 15 * count)

