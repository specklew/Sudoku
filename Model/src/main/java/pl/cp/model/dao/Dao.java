package pl.cp.model.dao;

import pl.cp.model.exceptions.DaoException;
import pl.cp.model.exceptions.ReadException;
import pl.cp.model.exceptions.WriteException;

import java.util.ArrayList;

public interface Dao<T> extends AutoCloseable {
    T read() throws DaoException, ReadException;

    void write(T object) throws DaoException, WriteException;

    ArrayList<String> getSudokuNames();
}
