package pl.cp.model.dao;

import pl.cp.model.SudokuBoard;
import pl.cp.model.exceptions.FileReadException;
import pl.cp.model.exceptions.FileWriteException;

import java.io.*;
import java.util.ArrayList;

public class FileSudokuBoardDao implements Dao<SudokuBoard>, AutoCloseable {

    private final String path;

    public FileSudokuBoardDao(String fileName) {
        this.path = fileName;
    }

    @Override
    public SudokuBoard read() throws FileReadException {
        SudokuBoard sudokuBoard = null;
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(path))) {
            sudokuBoard = (SudokuBoard) inputStream.readObject();
        } catch (IOException exception) {
            throw new FileReadException(FileReadException.READ_FAIL, exception);
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        }
        return sudokuBoard;
    }

    @Override
    public void write(SudokuBoard object) throws FileWriteException {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(path))) {
            outputStream.writeObject(object);
        } catch (IOException exception) {
            throw new FileWriteException(FileWriteException.WRITE_FAIL, exception);
        }
    }

    @Override
    public void close() throws Exception {

    }

    @Override
    public ArrayList<String> getSudokuNames() {
        return null;
    }
}
