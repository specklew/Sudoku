package pl.cp.model.parts;

import pl.cp.model.SudokuField;

import java.io.Serializable;
import java.util.List;

public abstract class SudokuPart implements Serializable, Cloneable {

    protected SudokuField[] fields = new SudokuField[9];

    public void setFieldValue(int position, SudokuField field) {
        this.fields[position] = field;
    }

    public SudokuField[] getFields() {
        return fields;
    }

    public final void setFields(List<SudokuField> fields) {

        for (int i = 0; i < 9; i++) {

            fields.get(i).setValue(fields.get(i).getValue());
        }
    }

    public boolean verify() {

        for (SudokuField x : fields) {

            for (SudokuField y : fields) {

                if (x != y && x.getValue() == y.getValue() && (x.getValue() != 0 | y.getValue() != 0)) {
                    return false;
                }
            }
        }
        return true;
    }
}
