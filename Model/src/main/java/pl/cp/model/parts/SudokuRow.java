package pl.cp.model.parts;

import pl.cp.model.SudokuField;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SudokuRow extends SudokuPart implements Serializable,Cloneable {

    @Override
    public Object clone() throws CloneNotSupportedException {
        List<SudokuField> fields = new ArrayList<>(List.of(getFields()));
        SudokuRow copy = new SudokuRow();
        copy.setFields(fields);
        return copy;
    }

}
