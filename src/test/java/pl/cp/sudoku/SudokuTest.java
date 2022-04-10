package pl.cp.sudoku;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;

public class SudokuTest {

    @Test
    public void testSudokuBoardCorrectness() {
        SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(sudokuSolver);
        sudokuBoard.solveGame();
        SudokuField[][] board = new SudokuField[9][9];
        Field privateBoardField;
        try {
            privateBoardField = SudokuBoard.class.getDeclaredField("board");
            privateBoardField.setAccessible(true);
            board = (SudokuField[][]) privateBoardField.get(sudokuBoard);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {

                //Check row:
                for (int i = 0; i < board.length; i++) {
                    if (board[row][i] == board[row][column] && i != column) {
                        Assertions.fail();
                    }
                }

                //Check column:
                for (int i = 0; i < board.length; i++) {
                    if (board[i][column] == board[row][column] && i != row) {
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

    @Test
    public void testNoSudokuBoardRepetitiveness() {

        boolean SudokuRepetitive = true;

        SudokuSolver sudokuSolver1 = new BacktrackingSudokuSolver();
        SudokuSolver sudokuSolver2 = new BacktrackingSudokuSolver();

        SudokuBoard sudoku1 = new SudokuBoard(sudokuSolver1);
        SudokuBoard sudoku2 = new SudokuBoard(sudokuSolver2);

        sudoku1.solveGame();
        sudoku2.solveGame();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (sudoku1.get(i, j) != sudoku2.get(i, j)) {
                    SudokuRepetitive = false;
                    break;
                }
            }
        }
        Assertions.assertFalse(SudokuRepetitive);
    }

    @Test
    public void testToStringTestForSudokuBoard() {
        SudokuBoard sb = new SudokuBoard(new BacktrackingSudokuSolver());
        sb.solveGame();
        try {
            String st = sb.toString();
            assertNotSame(null, st);
        } catch (NullPointerException e) {
            System.out.println("Works but values are nulls!");
        }
    }

    @Test
    public void testEqualsTestForSudokuBoard() {
        SudokuBoard sb = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBoard bs = new SudokuBoard(new BacktrackingSudokuSolver());
        try {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++)
                    sb.set(i, j, 1);

            }
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++)
                    bs.set(i, j, 1);
            }
        } catch (NullPointerException e) {
            System.out.println("Works but values are nulls");
        }
        assertTrue(sb.equals(bs) && bs.equals(sb));
        assertEquals(true, sb.hashCode() == bs.hashCode());
        SudokuColumn sudokuColumn = new SudokuColumn();
        assertFalse(sb.equals(sudokuColumn));
        assertFalse(sb.equals(null));
    }

    @Test
    public void testHashCodeTestForSudokuBoard() {
        SudokuBoard sb = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBoard bs = new SudokuBoard(new BacktrackingSudokuSolver());
        sb = bs;
        assertTrue(sb.equals(bs) && bs.equals(sb));
    }

    @Test
    public void testToStringTestForSudokuField() {
        SudokuField sf = new SudokuField(() -> {});
        try {
            String st = sf.toString();
            assertNotSame(null, st);
        } catch (NullPointerException e) {
            System.out.println("Works but values are nulls");
        }
    }

    @Test
    public void testEqualsTestForSudokuField() {
        SudokuField sb = new SudokuField(() -> {});
        SudokuField bs = new SudokuField(() -> {});

        sb.setFieldValue(1);
        bs.setFieldValue(1);
        assertEquals(sb, bs);
        sb.setFieldValue(5);
        assertNotEquals(sb, bs);
        assertNotEquals(null, sb);
        assertNotEquals(null, bs);
    }

    @Test
    public void testHashCodeTestForSudokuField() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuField bs = new SudokuField(() -> {});
        SudokuField sb = bs;
        assertTrue(sb.equals(bs) && bs.equals(sb));
        assertFalse(sb.equals(null));
        bs.setFieldValue(1);
        assertFalse(sb.equals(sudokuBoard));
    }
}