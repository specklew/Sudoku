package pl.cp.model.exceptions;



public class DaoException extends Exception {

    public DaoException(Throwable cause) {
        super(cause);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException() {
        super();
    }
}

