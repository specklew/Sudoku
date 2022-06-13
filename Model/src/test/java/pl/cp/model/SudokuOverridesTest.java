package pl.cp.model;

import org.junit.jupiter.api.Test;
import pl.cp.model.parts.SudokuColumn;
import pl.cp.model.solver.BacktrackingSudokuSolver;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings({"ConstantConditions", "ResultOfMethodCallIgnored", "AssertBetweenInconvertibleTypes"})
public class SudokuOverridesTest {
    @Test
    public void testToStringTestForSudokuBoard() {
        SudokuBoard sb = new SudokuBoard(new BacktrackingSudokuSolver());
        sb.solveGame();
        String st = sb.toString();
        assertNotSame(null, st);
    }

    @Test
    public void testEqualsTestForSudokuBoard() {
        SudokuBoard sb = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBoard bs = new SudokuBoard(new BacktrackingSudokuSolver());

        sb.solveGame();
        bs.solveGame();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++)
                sb.set(i, j, 1);

        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++)
                bs.set(i, j, 1);
        }

        assertTrue(sb.equals(bs) && bs.equals(sb));
        assertFalse(sb.hashCode() != bs.hashCode());
        SudokuColumn sudokuColumn = new SudokuColumn();

        assertNotEquals(sb, sudokuColumn);
        assertNotEquals(null, sb);
    }

    @Test
    public void testHashCodeTestForSudokuBoard() {
        SudokuBoard sb;
        SudokuBoard bs = new SudokuBoard(new BacktrackingSudokuSolver());
        sb = bs;
        assertTrue(sb.equals(bs) && bs.equals(sb));
    }

    @Test
    public void ComparingSudokuFieldTest() {
        SudokuField field1 = new SudokuField(() -> {
        });
        SudokuField field2 = new SudokuField(() -> {
        });
        field2.setValue(5);

        assertThrows(NullPointerException.class, () -> {
            SudokuField test = null;

            field2.compareTo(test);
        });

        field1.setValue(5);

        assertEquals(0, field1.compareTo(field2));
        field1.setValue(8);
        assertTrue(field1.compareTo(field2) > 0);
        assertTrue(field2.compareTo(field1) < 0);
        field1.setValue(5);
        assertEquals(field1.compareTo(field2), 0);
    }
}
