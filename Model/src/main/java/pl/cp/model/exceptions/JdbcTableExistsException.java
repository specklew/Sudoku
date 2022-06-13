package pl.cp.model.exceptions;


public class JdbcTableExistsException extends JdbcDaoException {
    public JdbcTableExistsException(Throwable cause) {
        super(cause);
    }
}
