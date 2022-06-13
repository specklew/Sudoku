package pl.cp.model.exceptions;


public class JdbcDaoException extends DaoException {

    public JdbcDaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public JdbcDaoException(Throwable cause) {
        super(cause);
    }

    public JdbcDaoException() {
        super();
    }
}


