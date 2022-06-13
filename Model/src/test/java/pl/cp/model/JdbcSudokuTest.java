package pl.cp.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.cp.model.dao.Dao;
import pl.cp.model.dao.SudokuBoardDaoFactory;
import pl.cp.model.exceptions.JdbcConnectionException;
import pl.cp.model.solver.BacktrackingSudokuSolver;

public class JdbcSudokuTest {

    @Test
    public void testJdbcConnectionException() throws Exception {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();

        try(Dao<SudokuBoard> dao = SudokuBoardDaoFactory.getDatabaseDao("testSudoku")){
            Assertions.assertThrows(JdbcConnectionException.class, () -> dao.write(board));
        }
    }
}
