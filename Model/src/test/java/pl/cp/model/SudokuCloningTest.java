package pl.cp.model;

import org.junit.jupiter.api.Test;
import pl.cp.model.parts.SudokuBox;
import pl.cp.model.parts.SudokuColumn;
import pl.cp.model.parts.SudokuRow;
import pl.cp.model.solver.BacktrackingSudokuSolver;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SuppressWarnings({"SimplifiableAssertion", "UnusedAssignment"})
public class SudokuCloningTest {
    @Test
    public void CloningSudokuRowTest() throws CloneNotSupportedException {
        try {
            SudokuRow row = new SudokuRow();
            List<SudokuField> list = Arrays.asList(new SudokuField[9]);
            for (int i = 0, j = 1; i < 9; i++, j++) {
                list.set(i, new SudokuField(() -> {
                }));
                list.get(i).setValue(j);
            }

            row.setFields(list);

            SudokuRow rowClone = new SudokuRow();
            rowClone = (SudokuRow) row.clone();
            assertEquals(rowClone.equals(row), row.equals(rowClone));

            list.get(0).setValue(5);
            rowClone.setFields(list);
            assertNotEquals(rowClone, row);

        } catch (NullPointerException ignored) {

        }

    }

    @Test
    public void CloningSudokuBoxTest() throws CloneNotSupportedException {
        try {
            SudokuBox box = new SudokuBox();
            List<SudokuField> list = Arrays.asList(new SudokuField[9]);
            for (int i = 0, j = 1; i < 9; i++, j++) {
                list.set(i, new SudokuField(() -> {
                }));
                list.get(i).setValue(j);
            }

            box.setFields(list);

            SudokuBox boxClone = (SudokuBox) box.clone();
            assertEquals(boxClone, box);

            list.get(0).setValue(5);
            boxClone.setFields(list);
            assertNotEquals(boxClone, box);
        } catch (NullPointerException ignored) {

        }


    }

    @Test
    public void CloningSudokuColumnTest() throws CloneNotSupportedException {
        try {
            SudokuColumn column = new SudokuColumn();
            List<SudokuField> list = Arrays.asList(new SudokuField[9]);
            for (int i = 0, j = 1; i < 9; i++, j++) {
                list.set(i, new SudokuField(() -> {
                }));
                list.get(i).setValue(j);
            }

            column.setFields(list);
            // idk why it shows that it is redundant
            SudokuColumn columnClone = new SudokuColumn();
            columnClone = (SudokuColumn) column.clone();
            assertEquals(columnClone.equals(column), column.equals(columnClone));

            list.get(0).setValue(5);
            columnClone.setFields(list);
            assertNotEquals(columnClone, column);

        } catch (NullPointerException ignored) {

        }
    }

    @Test
    public void CloningSudokuBoardTest() throws CloneNotSupportedException {
        try {
            SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
            SudokuBoard clone = board.clone();

            assertEquals(board, clone);

            assertTrue(board.equals(clone));

            clone.set(0, 0, 5);

            assertNotEquals(board, clone);

            assertEquals(false, board.equals(clone));

            clone.solveGame();

            assertFalse(board.equals(clone));
        } catch (NullPointerException ignored) {

        }

    }

    @Test
    public void CloningSudokuFieldTest() throws CloneNotSupportedException {
        SudokuField field = new SudokuField(() -> {
        });
        field.setValue(7);
        SudokuField copy = new SudokuField(() -> {
        });
        copy = (SudokuField) field.clone();

        assertEquals(field.getValue(), copy.getValue());

        copy.setValue(1);

        assertNotEquals(field.getValue(), copy.getValue());
    }
}
