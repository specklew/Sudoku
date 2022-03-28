package pl.cp.sudoku;

public class SudokuField {

    private int value;

    public SudokuField() {
        this.value = 0;
    }

    public int getFieldValue() {
        return this.value;
    }

    public void setFieldValue(int value) {
        this.value = value;
    }
}
