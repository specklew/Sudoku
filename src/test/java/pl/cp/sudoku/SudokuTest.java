package pl.cp.sudoku;

import org.junit.jupiter.api.Assertions;
import java.lang.reflect.Field;

public class SudokuTest {

    @org.junit.jupiter.api.Test
    public void testSudokuBoardCorrectness() {
        SudokuBoard sudokuBoard = new SudokuBoard();
        sudokuBoard.fillBoard();
        int[][] board = new int[9][9];
        Field privateBoardField;
        try {
            privateBoardField = SudokuBoard.class.getDeclaredField("board");
            privateBoardField.setAccessible(true);
            board = (int[][]) privateBoardField.get(sudokuBoard);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        for(int row = 0; row < 9; row++) {
            for(int column = 0; column < 9; column++) {

                //Check row:
                for(int i = 0; i < board.length; i++) {
                    if(board[row][i] == board[row][column] && i != column) {
                        Assertions.fail();
                    }
                }

                //Check column:
                for(int i = 0; i < board.length; i++) {
                    if(board[i][column] == board[row][column] && i != row) {
                        Assertions.fail();
                    }
                }

                //Check sector:
                int sectionRow;
                int sectionColumn;

                sectionRow = row / 3;
                sectionColumn = column / 3;

                for (int k = 0; k < 3; k++) {
                    for (int l = 0; l < 3; l++) {
                        if (board[sectionRow * 3 + k][sectionColumn * 3 + l] == board[row][column] && sectionRow * 3 + k != row && sectionColumn * 3 + l != column) {
                            Assertions.fail();
                        }
                    }
                }
            }
        }
    }

    @org.junit.jupiter.api.Test
    public void testNoSudokuBoardRepetitiveness() {

        boolean SudokuRepetitive = true;

        SudokuBoard sudoku1 = new SudokuBoard();
        SudokuBoard sudoku2 = new SudokuBoard();
        sudoku1.fillBoard();
        sudoku2.fillBoard();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(sudoku1.getCellValue(i, j) != sudoku2.getCellValue(i, j)) {
                    SudokuRepetitive = false;
                    break;
                }
            }
        }
        Assertions.assertFalse(SudokuRepetitive);
    }
}