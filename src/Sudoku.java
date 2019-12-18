import java.util.ArrayList;
import java.util.Arrays;

public class Sudoku {
    private static char[] trueList = {'1', '2', '3', '4', '5', '6', '7', '8', '9'};

    public static void main(String[] args) {
        // get a puzzle board
        char[][] puzzle = SudokuP.puzzle();
        char[][] test = {{'1', '.', '.', '6', '.', '7', '5', '.', '3'},
                {'7', '.', '8', '.', '.', '4', '2', '1', '.'}, {'.', '3', '5', '.', '2', '1', '8', '7', '6'},
                {'9', '.', '1', '5', '.', '.', '4', '3', '2'}, {'6', '5', '7', '.', '.', '3', '1', '.', '8'},
                {'.', '2', '4', '8', '1', '9', '.', '6', '5'}, {'.', '7', '9', '4', '6', '5', '3', '2', '.'},
                {'6', '4', '.', '1', '.', '2', '9', '.', '.'}, {'.', '1', '.', '.', '9', '8', '.', '5', '4'}};
        // solve
        solve(puzzle);
    }

    // print out one solution of the given puzzle
    // accepted parameter(s): 2D char array representing a sudoku board
    public static void solve(char[][] puzzle) {
        if (puzzle == null || puzzle.length == 0)
            return;
        if (solveSudoku(puzzle) && postCheck(puzzle)) {
            for (int i = 0; i < puzzle.length; i++) {
                for (int j = 0; j < puzzle[0].length; j++) {
                    if (j == puzzle.length - 1) {
                        System.out.print(puzzle[i][j]);
                    } else
                        System.out.print(puzzle[i][j] + ", ");
                }
                System.out.println();
            }
        } else {
            System.out.print("This puzzle is not solvable.");
        }
    }

    // solve a given sudoku puzzle board
    // additionally, return true if solvable; otherwise return false
    // accepted parameter(s): a 2D char array representing a sudoku board
    // return type: boolean
    // NOTE: you can assume that only valid sudoku board will be given as parameters
    // to this method
    public static boolean solveSudoku(char[][] puzzle) {
        if (check(puzzle))
            return (solveSudoku(puzzle, 0, 0));
        return false;

    }

    // recursive helper method
    public static boolean solveSudoku(char[][] puzzle, int row, int col) {
        if (row == puzzle.length) {
            return true;
        }
        if (col < puzzle[0].length) {
            if (puzzle[row][col] != '.')
                solveSudoku(puzzle, row, col + 1);
            else {
                for (int number = 1; number <= 9; number++) {
                    if (isSpotValid(puzzle, row, col, (char) (number + '0'))) {
                        puzzle[row][col] = (char) (number + '0');
                        if (solveSudoku(puzzle, row, col + 1))
                            return true;
                        // backtrack
                        puzzle[row][col] = '.';
                    }
                }
            }
        } else {
            // move to next row
            solveSudoku(puzzle, row + 1, 0);
        }

        return isCompletelyFilled(puzzle);
    }

    // check if a given sudoku puzzle board is valid or not
    // return true if valid; otherwise return false
    // accepted parameter(s): a 2D char array representing a sudoku board
    // return type: boolean
    // call isInitiallyValid()
    public static boolean check(char[][] puzzle) {
        // Iterates through running isParticallyValid for each row column and 3x3 area
        for (int i = 0; i < 9; i++) {
            if (!isInitiallyValid(puzzle, i, 0, i, 8) || !isInitiallyValid(puzzle, 0, i, 8, i)) {
                return false;
            }
        }

        if (!isInitiallyValid(puzzle, 0, 0, 2, 2) || !isInitiallyValid(puzzle, 0, 3, 2, 5)
                || !isInitiallyValid(puzzle, 0, 6, 2, 8) || !isInitiallyValid(puzzle, 3, 0, 5, 2)
                || !isInitiallyValid(puzzle, 3, 3, 5, 5) || !isInitiallyValid(puzzle, 3, 6, 5, 8)
                || !isInitiallyValid(puzzle, 6, 0, 8, 2) || !isInitiallyValid(puzzle, 6, 3, 8, 5)
                || !isInitiallyValid(puzzle, 6, 6, 8, 8)) {
            return false;
        }

        return true;
    }

    // call by check method to validate the board before it is filled
    // if true, proceed to solveSudoku, not then return false
    public static boolean isInitiallyValid(char[][] puzzle, int x1, int y1, int x2, int y2) {
        ArrayList<Integer> testArr1 = new ArrayList<>();
        ArrayList<Integer> testArr2 = new ArrayList<>();

        // check 3x3 box
        if ((x1 == 0 && x2 == 2) || (x1 == 3 && x2 == 5) || (x1 == 6 && x2 == 8)) {
            for (int i = x1; i <= x2; i++) {
                for (int j = y1; j <= y2; j++) {
                    if (puzzle[i][j] != '.') {
                        testArr1.add(Character.getNumericValue(puzzle[i][j]));
                    }
                }
            }

            int i = 0;
            while (i < testArr1.size()) {
                if (!testArr2.contains(testArr1.get(i)))
                    testArr2.add(testArr1.get(i));
                i++;
            }
            return (testArr1.size() == testArr2.size());
        }

        // check left to right (column)
        if (x1 == x2) {
            for (int i = 0; i < 9; i++) {
                if (puzzle[x1][i] != '.')
                    testArr1.add(Character.getNumericValue(puzzle[x1][i]));
            }

            int i = 0;
            while (i < testArr1.size()) {
                if (!testArr2.contains(testArr1.get(i)))
                    testArr2.add(testArr1.get(i));
                i++;
            }
            return (testArr1.size() == testArr2.size());
        }

        // check top to bottom (row)
        if (y1 == y2) {
            for (int i = 0; i < 9; i++) {
                if (puzzle[i][y1] != '.')
                    testArr1.add(Character.getNumericValue(puzzle[i][y1]));
            }

            int i = 0;
            while (i < testArr1.size()) {
                if (!testArr2.contains(testArr1.get(i)))
                    testArr2.add(testArr1.get(i));
                i++;
            }
            return (testArr1.size() == testArr2.size());
        }

        return false;
    }

    // check the board's validity after the board is completely filled
    // call isParticallyValid()
    public static boolean postCheck(char[][] puzzle) {
        // Iterates through running isParticallyValid for each row column and 3x3 area
        for (int i = 0; i < 9; i++) {
            if (!isParticallyValid(puzzle, i, 0, i, 8) || !isParticallyValid(puzzle, 0, i, 8, i)) {
                return false;
            }
        }
        return isParticallyValid(puzzle, 0, 0, 2, 2) && isParticallyValid(puzzle, 0, 3, 2, 5)
                && isParticallyValid(puzzle, 0, 6, 2, 8) && isParticallyValid(puzzle, 3, 0, 5, 2)
                && isParticallyValid(puzzle, 3, 3, 5, 5) && isParticallyValid(puzzle, 3, 6, 5, 8)
                && isParticallyValid(puzzle, 6, 0, 8, 2) && isParticallyValid(puzzle, 6, 3, 8, 5)
                && isParticallyValid(puzzle, 6, 6, 8, 8);
    }

    // check if the specified area of the given sudoku board is valid
    // valid - following the 3 rules of sudoku
    // accepted parameters: puzzle - standing for a sudoku board in the
    // representation of a 2D char array
    // four integers x1, y1, x2, y2
    // (x1, y1) stands for the top left corner of the area (inclusive)
    // x1 - row index; y1 - column index
    // (x2, y2) stands for the bottom right corner of the area (inclusive)
    // x2 - row index; y2 - colum index
    // return data type: boolean
    // if the specified area is valid, return true; otherwise false
    // e.g.1, isParticallyValid(puzzle,0,0,0,8) is used to check the 1st row of
    // puzzle
    // e.g.2, isParticallyValid(puzzle,0,0,8,0) is used to check the 1st column of
    // puzzle
    // e.g.3, isParticallyValid(puzzle,0,0,2,2) is used to check the top left 3*3
    // area
    // NOTE that this method will only be applied to every row, every column, and
    // every 3*3 small areas (9 small areas in total)
    // call by postCheck method to validate the board once it is completely filled
    public static boolean isParticallyValid(char[][] puzzle, int x1, int y1, int x2, int y2) {
        char[] testArr = new char[9];
        int count = 0;
        if (check(puzzle)) {
            // check box 3x3 by sorting the array in ascending order and compare with
            // trueList array
            if ((x1 == 0 && x2 == 2) || (x1 == 3 && x2 == 5) || (x1 == 6 && x2 == 8)) {
                for (int i = x1; i <= x2; i++) {
                    for (int j = y1; j <= y2; j++) {
                        testArr[count++] = puzzle[i][j];
                    }
                }
                Arrays.sort(testArr);
                for (int i = 0; i < 9; i++) {
                    if (testArr[i] != trueList[i]) {
                        return false;
                    }
                }
            }

            // check left to right (column) by sorting the array in ascending order and
            // compare with trueList array
            if (x1 == x2) {
                for (int i = 0; i < 9; i++) {
                    testArr[count++] = puzzle[x1][i];
                }
                Arrays.sort(testArr);
                for (int i = 0; i < 9; i++) {
                    if (testArr[i] != trueList[i]) {
                        return false;
                    }
                }
            }

            // check top to bottom (row) by sorting the array in ascending order and compare
            // with trueList array
            if (y1 == y2) {
                for (int i = 0; i < 9; i++) {
                    testArr[count++] = puzzle[i][y1];
                }
                Arrays.sort(testArr);
                for (int i = 0; i < 9; i++) {
                    if (testArr[i] != trueList[i]) {
                        return false;
                    }
                }
            }
        } else
            return false;

        return true;
    }

    // check whether putting a digit c at the position (x, y) in a given sudoku
    // board
    // will make the board invalid
    // accepted parameters: puzzle - standing for a sudoku board in the
    // representation of a 2D char array
    // two integers x, y
    // x - row index; y - column index
    // c - a digit in the form of char to put at (x, y)
    // return data type: boolean
    // if putting c in puzzle is a valid move, return true; otherwise false
    public static boolean isSpotValid(char[][] puzzle, int row, int col, char c) {
        // check left to right (column)
        for (int i = 0; i < 9; i++) {
            if (puzzle[row][i] == c) {
                return false;
            }
        }
        // check top to bottom (row)
        for (int i = 0; i < 9; i++) {
            if (puzzle[i][col] == c) {
                return false;
            }
        }
        return true;
    }

    // check if the board is completely filled
    public static boolean isCompletelyFilled(char[][] puzzle) {
        for (char[] chars : puzzle) {
            for (int col = 0; col < puzzle[0].length; col++) {
                if (chars[col] == '.')
                    return false;
            }
        }
        return true;
    }

}
