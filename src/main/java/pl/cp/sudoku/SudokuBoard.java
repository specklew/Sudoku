package pl.cp.sudoku;

import java.util.Arrays;

public class SudokuBoard {

    private final int[][] board = new int[9][9];
    private final SudokuSolver sudokuSolver;

    public SudokuBoard(SudokuSolver solver) {
        this.sudokuSolver = solver;
    }

    public int get(int x, int y) {
        return board[x][y];
    }

    public void set(int x, int y, int value) {
        board[x][y] = value;
    }

    public void solveGame() {

        for (int[] row : board) {
            Arrays.fill(row, 0);
        }

        sudokuSolver.solve(this);
    }
}