package pl.cp.model.exceptions;

import java.util.NoSuchElementException;


public class EmptyListException extends NoSuchElementException {

    public EmptyListException(String s) {
        super(s);
    }



}

