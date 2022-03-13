package pl.cp.sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class SudokuBoard {

    private final int [][] board = new int[9][9];

    public void fillBoard() {

        for (int[] row : board) {
            Arrays.fill(row, 0);
        }
        solve(0,0);
    }

    public int getCellValue(int cellX, int cellY) {
        return board[cellX][cellY];
    }

    private void solve(int row, int col) {

        Random generator = new Random();
        ArrayList<Integer> numbers = new ArrayList<>();

        for (int i = 1; i < 10; i++) {
            numbers.add(i);
        }

        while (!numbers.isEmpty()) {

            int num = numbers.get(generator.nextInt(numbers.size()));

            if (checkRow(num, row) && checkColumn(num, col) && checkSection(num, row, col)) {
                board[row][col] = num;
                break;
            } else {
                numbers.remove(Integer.valueOf(num));
            }
        }

        //If no number is available then clear the column, row and section.
        if (numbers.isEmpty()) {
            clearObstructions(row, col);
        }

        //Find next zero and solve it.
        int[] zeros = nextZeroPosition();
        if (zeros[0] == 10) {
            return;
        }
        solve(zeros[0], zeros[1]);
    }

    private boolean checkRow(int number, int row) {
        for (int i = 0; i < board.length; i++) {
            if (board[row][i] == number) {
                return false;
            }
        }
        return true;
    }

    private boolean checkColumn(int number, int column) {
        for (int[] ints : board) {
            if (ints[column] == number) {
                return false;
            }
        }
        return true;
    }

    private boolean checkSection(int number, int row, int column) {
        int sectionRow;
        int sectionColumn;

        sectionRow = row / 3;
        sectionColumn = column / 3;

        for (int k = 0; k < 3; k++) {
            for (int l = 0; l < 3; l++) {
                if (board[sectionRow * 3 + k][sectionColumn * 3 + l] == number) {
                    return false;
                }
            }
        }

        return true;
    }

    private int[] nextZeroPosition() {
        int[] position = {10, 10};
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 0) {
                    position[0] = i;
                    position[1] = j;
                    return position;
                }
            }
        }
        return position;
    }

    private void clearObstructions(int row, int column) {
        int sectionRow;
        int sectionColumn;

        sectionRow = row / 3;
        sectionColumn = column / 3;

        //Clear section.
        for (int k = 0; k < 3; k++) {
            for (int l = 0; l < 3; l++) {
                board[sectionRow * 3 + k][sectionColumn * 3 + l] = 0;
            }
        }

        //Clear row and column.
        for (int i = 0; i < 9; i++) {
            board[row][i] = 0;
            board[i][row] = 0;
        }
    }
}