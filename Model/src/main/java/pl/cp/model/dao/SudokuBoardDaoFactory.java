package pl.cp.model.dao;

import pl.cp.model.SudokuBoard;

public class SudokuBoardDaoFactory {

    public SudokuBoardDaoFactory() {
    }

    public static Dao<SudokuBoard> getFileDao(String fileName) {
        return new FileSudokuBoardDao(fileName);
    }

    public static Dao<SudokuBoard> getDatabaseDao(String fileName) {
        return new JdbcSudokuBoardDao(fileName);
    }
}
