package pl.cp.sudoku;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BacktrackingSudokuSolver implements SudokuSolver {

    @Override
    public void solve(SudokuBoard sudokuBoard) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sudokuBoard.set(i, j, 0);
            }
        }
        recursiveSolve(0, 0, sudokuBoard);
    }

    private void recursiveSolve(int row, int col, SudokuBoard board) {

        Random generator = new Random();
        ArrayList<Integer> numbers = new ArrayList<>();

        for (int i = 1; i < 10; i++) {
            numbers.add(i);
        }

        while (!numbers.isEmpty()) {

            int num = numbers.get(generator.nextInt(numbers.size()));

            board.set(row, col, num);

            SudokuRow sudokuRow = board.getRow(row);
            SudokuColumn sudokuColumn = board.getColumn(col);
            SudokuBox sudokuBox = board.getBox(row, col);

            if (sudokuRow.verify() && sudokuColumn.verify() && sudokuBox.verify()) {
                break;
            } else {
                numbers.remove(Integer.valueOf(num));
            }
        }

        //If no number is available then clear the column, row and section.
        if (numbers.isEmpty()) {
            clearObstructions(row, col, board);
        }

        //Find next zero and solve it.
        List<Integer> zerosList = new ArrayList<>(nextZeroPosition(board));
        if (zerosList.get(0) == 10) {
            return;
        }
        recursiveSolve(zerosList.get(0), zerosList.get(1), board);
    }

    private List<Integer> nextZeroPosition(SudokuBoard sudokuBoard) {
        List<Integer> position = new ArrayList<>();
        position.add(10);
        position.add(10);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (sudokuBoard.get(i, j) == 0) {
                    position.set(0,i);
                    position.set(1,j);
                    return position;
                }
            }
        }
        return position;

    }




    private void clearObstructions(int row, int column, SudokuBoard sudokuBoard) {
        int sectionRow;
        int sectionColumn;

        sectionRow = row / 3;
        sectionColumn = column / 3;

        //Clear section.
        for (int k = 0; k < 3; k++) {
            for (int l = 0; l < 3; l++) {
                sudokuBoard.set(sectionRow * 3 + k, sectionColumn * 3 + l, 0);
            }
        }

        //Clear row and column.
        for (int i = 0; i < 9; i++) {
            sudokuBoard.set(row, i, 0);
            sudokuBoard.set(i, row, 0);
        }
    }
}
