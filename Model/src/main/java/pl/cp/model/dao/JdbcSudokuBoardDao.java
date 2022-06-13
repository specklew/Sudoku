package pl.cp.model.dao;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.cp.model.SudokuBoard;
import pl.cp.model.exceptions.*;
import pl.cp.model.solver.BacktrackingSudokuSolver;
import pl.cp.model.solver.SudokuSolver;

import java.sql.*;
import java.util.ArrayList;

public class JdbcSudokuBoardDao implements Dao<SudokuBoard>, AutoCloseable {

    private final String sudokuName;

    private Connection connection;

    private static final String url = "jdbc:postgresql://localhost:5432/test";
    private static final String user = "postgres";
    private static final String password = "12345";

    private static final Logger log = LogManager.getLogger(JdbcSudokuBoardDao.class);

    public JdbcSudokuBoardDao(String filename) {
        this.sudokuName = filename;
    }


    private Connection connect() throws DaoException {
        connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
            log.debug("Connected to database");
            return connection;
        } catch (SQLException e) {
            log.debug("Error in connecting to database");
            throw new JdbcConnectionException(JdbcConnectionException.CON_FAIL, e);
        }

    }

    @Override
    public SudokuBoard read() throws DaoException, ReadException {
        if (sudokuName.isEmpty()) {
            throw new ReadException();
        }
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(solver);

        String sql = "SELECT id "
                + "FROM public.\"SudokuTable\" "
                + "WHERE \"Name\" = ?";

        String sqlFields = "SELECT \"column\", \"row\", value "
                + "FROM public.\"SudokuTableValues\" "
                + "WHERE \"id_Sudoku\" = ?";

        try (Connection conn = connect();
             PreparedStatement pstmtFields = conn.prepareStatement(sqlFields);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, sudokuName);
            int sudokuId = 0;

            try (ResultSet results = pstmt.executeQuery()) {
                if (results.next()) {
                    sudokuId = results.getInt("id");
                }
            }

            pstmtFields.setInt(1, sudokuId);
            try (ResultSet results = pstmtFields.executeQuery()) {
                while (results.next()) {
                    int x = results.getInt(2);
                    int y = results.getInt(1);
                    int value = results.getInt(3);
                    board.set(x, y, value);
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new JdbcReadException(JdbcReadException.READ_DB, e);
        }

        return board;
    }

    @Override
    public void write(SudokuBoard obj) throws DaoException, WriteException {

        if (sudokuName.isEmpty()) {
            throw new WriteException();
        }

        String sql = "INSERT INTO public.\"SudokuTable\"(\"Name\") " + "VALUES(?)";
        String fieldSql = "INSERT INTO public.\"SudokuTableValues\"(\"id_Sudoku\", \"column\", \"row\", value) "
                + "VALUES(?,?,?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql,
                     Statement.RETURN_GENERATED_KEYS)) {
            conn.setAutoCommit(false);
            try {
                pstmt.setString(1, sudokuName);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                log.info("sudoku name already exists");
                throw new JdbcSameBoardException(JdbcSameBoardException.SAME_BOARD, e);
            }

            int sudokuId;

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    sudokuId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating sudoku failed, no ID obtained.");
                }
            }

            try (PreparedStatement pstmtField = conn.prepareStatement(fieldSql)) {
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        pstmtField.setInt(1, sudokuId);
                        pstmtField.setInt(3, i);
                        pstmtField.setInt(2, j);
                        pstmtField.setInt(4, obj.get(i, j));
                        pstmtField.executeUpdate();
                    }
                }
            } catch (SQLException ex) {
                log.error(ex.getMessage());
                throw new JdbcIdException(JdbcIdException.ID_FAIL, ex);
            }
            conn.commit();
            pstmt.close();
            connection.close();
        } catch (SQLException ex) {
            log.error(ex.getMessage());
            throw new JdbcWriteException(JdbcWriteException.WRITE_DB, ex);
        }
    }

    @Override
    public ArrayList<String> getSudokuNames() {
        String sql = "SELECT \"Name\" from public.\"SudokuTable\"";
        ArrayList<String> names = new ArrayList<>();

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            try (ResultSet results = pstmt.executeQuery()) {
                while (results.next()) {
                    names.add(results.getString("name"));
                }
            }
            pstmt.close();
            connection.close();
        } catch (SQLException | DaoException ex) {
            log.error(ex.getMessage());
        }

        return names;
    }

    @Override
    public void close() throws Exception {

    }
}
