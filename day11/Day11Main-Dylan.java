import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Day11Main {

    private static final String INPUT_FILE = "input-file.txt";

    private static final char EMPTY = 'L';

    private static final char OCCUPIED = '#';

    private char[][] grid;

    private char[][] grid2;

    public static void main(String[] args) throws Exception {
        Day11Main day11Main = new Day11Main();
        day11Main.run();
    }

    public Day11Main() {}

    public void run() throws Exception {
        String input = getInputFileAsString();
        processInputs(preProcessInput(input));
    }

    private String getInputFileAsString() throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(INPUT_FILE));
        return new String(encoded, StandardCharsets.UTF_8);
    }

    private void processInputs(char[][] input) {
        grid = input;
        grid2 = clone(grid);
        while (processRoundPart1()) {
        }

        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == OCCUPIED) {
                    count++;
                }
            }
        }
        System.out.println("Part 1: " + count);


        while (processRoundPart2()) {
        }

        count = 0;
        for (int i = 0; i < grid2.length; i++) {
            for (int j = 0; j < grid2[i].length; j++) {
                if (grid2[i][j] == OCCUPIED) {
                    count++;
                }
            }
        }

        System.out.println("Part 2: " + count);

    }

    private boolean processRoundPart1() {
        char[][] newGrid = clone(grid);
        boolean changed = false;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == EMPTY) {
                    if (checkEmptySeat(grid, i, j)) {
                        newGrid[i][j] = OCCUPIED;
                        changed = true;
                    }
                }
                else if (grid[i][j] == OCCUPIED) {
                    if (checkOccupiedSeat(grid, i, j)) {
                        newGrid[i][j] = EMPTY;
                        changed = true;
                    }
                }
            }
        }

        grid = newGrid;
        return changed;
    }

    private boolean processRoundPart2() {
        char[][] newGrid = clone(grid2);
        boolean changed = false;

        for (int i = 0; i < grid2.length; i++) {
            for (int j = 0; j < grid2[i].length; j++) {
                if (grid2[i][j] == EMPTY) {
                    if (checkEmptySeatPart2(grid2, i, j)) {
                        newGrid[i][j] = OCCUPIED;
                        changed = true;
                    }
                }
                else if (grid2[i][j] == OCCUPIED) {
                    if (checkOccupiedSeatPart2(grid2, i, j)) {
                        newGrid[i][j] = EMPTY;
                        changed = true;
                    }
                }
            }
        }

        grid2 = newGrid;
        return changed;
    }

    private static boolean checkEmptySeatPart2(char[][] grid, int i, int j) {
        return !checkSeesNorth(grid, i, j) && !checkSeesEast(grid, i, j) && !checkSeesSouth(grid, i, j) &&
            !checkSeesWest(grid, i, j) &&
            !checkSeesNorthWest(grid, i, j) && !checkSeesNorthEast(grid, i, j) && !checkSeesSouthEast(grid, i, j) &&
            !checkSeesSouthWest(grid, i, j);
    }

    private static boolean checkEmptySeat(char[][] grid, int i, int j) {
        return !checkNorth(grid, i, j) && !checkEast(grid, i, j) && !checkSouth(grid, i, j) && !checkWest(grid, i, j) &&
            !checkNorthWest(grid, i, j) && !checkNorthEast(grid, i, j) && !checkSouthEast(grid, i, j) &&
            !checkSouthWest(grid, i, j);
    }

    private static boolean checkOccupiedSeat(char[][] grid, int i, int j) {
        int count = 0;
        if (checkNorth(grid, i, j)) {
            count++;
        }
        if (checkEast(grid, i, j)) {
            count++;
        }
        if (checkSouth(grid, i, j)) {
            count++;
        }
        if (checkWest(grid, i, j)) {
            count++;
        }
        if (checkNorthWest(grid, i, j)) {
            count++;
        }
        if (checkNorthEast(grid, i, j)) {
            count++;
        }
        if (checkSouthEast(grid, i, j)) {
            count++;
        }
        if (checkSouthWest(grid, i, j)) {
            count++;
        }
        return count >= 4;
    }

    private static boolean checkOccupiedSeatPart2(char[][] grid, int i, int j) {
        int count = 0;
        if (checkSeesNorth(grid, i, j)) {
            count++;
        }
        if (checkSeesEast(grid, i, j)) {
            count++;
        }
        if (checkSeesSouth(grid, i, j)) {
            count++;
        }
        if (checkSeesWest(grid, i, j)) {
            count++;
        }
        if (checkSeesNorthWest(grid, i, j)) {
            count++;
        }
        if (checkSeesNorthEast(grid, i, j)) {
            count++;
        }
        if (checkSeesSouthEast(grid, i, j)) {
            count++;
        }
        if (checkSeesSouthWest(grid, i, j)) {
            count++;
        }
        return count >= 5;
    }

    private static boolean checkSeesNorth(char[][] grid, int i, int j) {
        if (i != 0) {
            i--;
            while (i >= 0) {
                if(grid[i][j] == EMPTY) {
                    return false;
                }
                if (grid[i][j] == OCCUPIED) {
                    return true;
                }
                i--;
            }
        }
        return false;
    }

    private static boolean checkSeesEast(char[][] grid, int i, int j) {
        if (j != grid[i].length - 1) {
            j++;
            while (j <= grid[i].length - 1) {
                if(grid[i][j] == EMPTY) {
                    return false;
                }
                if (grid[i][j] == OCCUPIED) {
                    return true;
                }
                j++;
            }
        }
        return false;
    }

    private static boolean checkSeesSouth(char[][] grid, int i, int j) {
        if (i != grid.length - 1) {
            i++;
            while (i <= grid.length - 1) {
                if(grid[i][j] == EMPTY) {
                    return false;
                }
                if (grid[i][j] == OCCUPIED) {
                    return true;
                }
                i++;
            }
        }
        return false;
    }

    private static boolean checkSeesWest(char[][] grid, int i, int j) {
        if (j != 0) {
            j--;
            while (j >= 0) {
                if(grid[i][j] == EMPTY) {
                    return false;
                }
                if (grid[i][j] == OCCUPIED) {
                    return true;
                }
                j--;
            }
        }
        return false;
    }

    private static boolean checkSeesNorthWest(char[][] grid, int i, int j) {
        if (i != 0 && j != 0) {
            i--;
            j--;
            while (i >= 0 && j >= 0) {
                if(grid[i][j] == EMPTY) {
                    return false;
                }
                if (grid[i][j] == OCCUPIED) {
                    return true;
                }
                i--;
                j--;
            }
        }
        return false;
    }

    private static boolean checkSeesNorthEast(char[][] grid, int i, int j) {
        if (i != 0 && j != grid[i].length - 1) {
            i--;
            j++;
            while (i >= 0 && j <= grid[i].length - 1) {
                if(grid[i][j] == EMPTY) {
                    return false;
                }
                if (grid[i][j] == OCCUPIED) {
                    return true;
                }
                i--;
                j++;
            }
        }
        return false;
    }

    private static boolean checkSeesSouthEast(char[][] grid, int i, int j) {
        if (i != grid.length - 1 && j != grid[i].length - 1) {
            i++;
            j++;
            while (i <= grid.length - 1 && j <= grid[i].length - 1) {
                if(grid[i][j] == EMPTY) {
                    return false;
                }
                if (grid[i][j] == OCCUPIED) {
                    return true;
                }
                i++;
                j++;
            }
        }
        return false;
    }

    private static boolean checkSeesSouthWest(char[][] grid, int i, int j) {
        if (i != grid.length - 1 && j != 0) {
            i++;
            j--;
            while (i <= grid.length - 1 && j >= 0) {
                if(grid[i][j] == EMPTY) {
                    return false;
                }
                if (grid[i][j] == OCCUPIED) {
                    return true;
                }
                i++;
                j--;
            }
        }
        return false;
    }

    private static boolean checkNorth(char[][] grid, int i, int j) {
        if (i != 0) {
            return grid[i - 1][j] == OCCUPIED;
        }
        return false;
    }

    private static boolean checkEast(char[][] grid, int i, int j) {
        if (j != grid[i].length - 1) {
            return grid[i][j + 1] == OCCUPIED;
        }
        return false;
    }

    private static boolean checkSouth(char[][] grid, int i, int j) {
        if (i != grid.length - 1) {
            return grid[i + 1][j] == OCCUPIED;
        }
        return false;
    }

    private static boolean checkWest(char[][] grid, int i, int j) {
        if (j != 0) {
            return grid[i][j - 1] == OCCUPIED;
        }
        return false;
    }

    private static boolean checkNorthWest(char[][] grid, int i, int j) {
        if (i != 0 && j != 0) {
            return grid[i - 1][j - 1] == OCCUPIED;
        }
        return false;
    }

    private static boolean checkNorthEast(char[][] grid, int i, int j) {
        if (i != 0 && j != grid[i].length - 1) {
            return grid[i - 1][j + 1] == OCCUPIED;
        }
        return false;
    }

    private static boolean checkSouthEast(char[][] grid, int i, int j) {
        if (i != grid.length - 1 && j != grid[i].length - 1) {
            return grid[i + 1][j + 1] == OCCUPIED;
        }
        return false;
    }

    private static boolean checkSouthWest(char[][] grid, int i, int j) {
        if (i != grid.length - 1 && j != 0) {
            return grid[i + 1][j - 1] == OCCUPIED;
        }
        return false;
    }

    private char[][] preProcessInput(String input) {
        String[] inputLines = input.split("\n");

        int numRows = inputLines.length;

        char[] firstRow = inputLines[0].toCharArray();

        int numColumns = firstRow.length;

        char[][] processedInput = new char[numRows][numColumns];

        processedInput[0] = firstRow;

        for (int i = 1; i < inputLines.length; i++) {
            processedInput[i] = inputLines[i].toCharArray();
        }

        return processedInput;
    }

    private static void printGrid(char[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            System.out.println(grid[i]);
        }
    }

    private static char[][] clone(char[][] grid) {
        char[][] newGrid = new char[grid.length][grid[0].length];
        for (int i = 0; i < newGrid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                newGrid[i][j] = grid[i][j];
            }
        }
        return newGrid;
    }

}
