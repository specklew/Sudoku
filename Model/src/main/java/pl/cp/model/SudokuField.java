package pl.cp.model;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class SudokuField extends Observable implements Serializable, Cloneable, Comparable<SudokuField> {

    private Integer value;

    public SudokuField(Observer observer) {
        super(observer);
        this.value = 0;
    }

    public Integer getValue() {
        return this.value;
    }

    public void setValue(Integer value) {
        this.value = value;
        notifyObservers();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("Value fieldName",value).toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        SudokuField sb = (SudokuField) object;
        return new EqualsBuilder().append(value,sb.value).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17,37).append(value).toHashCode();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {

            SudokuField clone = (SudokuField) super.clone();
            return clone;


    }

    @Override
    public int compareTo(SudokuField o) {
        return Integer.compare(this.value, o.value);
    }

}
