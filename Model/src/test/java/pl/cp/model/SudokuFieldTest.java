package pl.cp.model;

import org.junit.jupiter.api.Test;
import pl.cp.model.solver.BacktrackingSudokuSolver;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings({"ConstantConditions", "UnnecessaryLocalVariable"})
public class SudokuFieldTest {
    @Test
    public void testToStringTestForSudokuField() {
        SudokuField sf = new SudokuField(() -> {
        });
        String st = sf.toString();
        assertNotSame(null, st);
    }

    @Test
    public void testEqualsTestForSudokuField() {
        SudokuField sb = new SudokuField(() -> {
        });
        SudokuField bs = new SudokuField(() -> {
        });

        sb.setValue(1);
        bs.setValue(1);
        assertEquals(sb, bs);
        sb.setValue(5);
        assertNotEquals(sb, bs);
        assertNotEquals(null, sb);
        assertNotEquals(null, bs);
    }

    @Test
    public void testHashCodeTestForSudokuField() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuField bs = new SudokuField(() -> {
        });
        SudokuField sb = bs;
        assertTrue(sb.equals(bs) && bs.equals(sb));
        assertNotEquals(null, sb);
        bs.setValue(1);
        //noinspection SimplifiableAssertion,EqualsBetweenInconvertibleTypes
        assertFalse(sb.equals(sudokuBoard));
    }
}
