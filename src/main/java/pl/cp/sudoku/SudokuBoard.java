package pl.cp.sudoku;

public class SudokuBoard {

    private final SudokuField[][] board = new SudokuField[9][9];
    private final SudokuSolver sudokuSolver;

    public SudokuBoard(SudokuSolver solver) {
        this.sudokuSolver = solver;
    }

    public int get(int x, int y) {
        return board[x][y].getFieldValue();
    }

    public void set(int x, int y, int value) {
        board[x][y].setFieldValue(value);
    }

    public void solveGame() {

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = new SudokuField();
            }
        }

        sudokuSolver.solve(this);
    }

    public SudokuRow getRow(int row) {
        SudokuRow sudokuRow = new SudokuRow();
        for (int i = 0; i < 9; i++) {
            sudokuRow.setFieldValue(i, board[row][i]);
        }
        return sudokuRow;
    }

    public SudokuColumn getColumn(int col) {
        SudokuColumn sudokuColumn = new SudokuColumn();
        for (int i = 0; i < 9; i++) {
            sudokuColumn.setFieldValue(i, board[i][col]);
        }
        return sudokuColumn;
    }

    public SudokuBox getBox(int row, int col) {

        SudokuBox box = new SudokuBox();

        int boxRow;
        int boxColumn;

        boxRow = row / 3;
        boxColumn = col / 3;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                box.setFieldValue(i + j * 3, board[boxRow * 3 + i][boxColumn * 3 + j]);
            }
        }

        return box;
    }
}