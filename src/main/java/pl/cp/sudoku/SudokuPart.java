package pl.cp.sudoku;

public abstract class SudokuPart {

    protected SudokuField[] fields = new SudokuField[9];

    public void setFieldValue(int position, SudokuField field) {
        this.fields[position] = field;
    }

    public boolean verify() {
        for (SudokuField x : fields) {
            for (SudokuField y : fields) {
                if (x != y && x.getFieldValue() == y.getFieldValue() && (x.getFieldValue() != 0 | y.getFieldValue() != 0)) {
                    return false;
                }
            }
        }
        return true;
    }
}
