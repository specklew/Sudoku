package pl.cp.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.cp.model.parts.SudokuBox;
import pl.cp.model.parts.SudokuColumn;
import pl.cp.model.parts.SudokuRow;
import pl.cp.model.solver.BacktrackingSudokuSolver;
import pl.cp.model.solver.SudokuSolver;

import java.io.Serializable;

public class SudokuBoard implements Observer, Serializable, Cloneable {

    private static final Logger logger = LogManager.getLogger(SudokuBoard.class);

    private final SudokuField[][] board = new SudokuField[9][9];
    private final SudokuSolver sudokuSolver;

    public SudokuBoard(SudokuSolver solver) {
        this.sudokuSolver = solver;
    }

    @Override
    public void update() {
        if (checkBoard()) logger.info("Board correct!");
    }

    public SudokuField getField(int x, int y) {
        return board[x][y];
    }

    public int get(int x, int y) {
        return board[x][y].getValue();
    }

    public void set(int x, int y, int value) {
        board[x][y].setValue(value);
    }

    private boolean checkBoard() {
        for (int i = 0; i < 9; i++) {
            if (!getRow(i).verify() | !getColumn(i).verify()) {
                return false;
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!getBox(i * 3, j * 3).verify()) {
                    return false;
                }
            }
        }

        return true;
    }

    public void solveGame() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = new SudokuField(this);
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

    @Override
    public String toString() {
        ToStringBuilder stringBuilder = new ToStringBuilder(this);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                stringBuilder.append(board[i][j].getValue());
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        SudokuBoard sb = (SudokuBoard) object;
        return new EqualsBuilder().append(board, sb.board).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(board).toHashCode();
    }

    @Override
    public SudokuBoard clone() {
        SudokuBoard clone = new SudokuBoard(new BacktrackingSudokuSolver());
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                clone.set(i, j, get(i, j));
            }
        }

        return clone;
    }
}