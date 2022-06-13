package pl.cp.model.exceptions;


import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class JdbcCloseConnectionException extends JdbcDaoException {


    private static final ResourceBundle messages;

    public static final String CLOSE_FAIL = "closeFail";

    static {
        Locale locale = Locale.getDefault(Locale.Category.DISPLAY);
        messages = ResourceBundle.getBundle("exceptions", locale);
    }


    public JdbcCloseConnectionException(Throwable cause) {
        super(cause);
    }

    public JdbcCloseConnectionException(String message, Throwable cause) {
        super(message, cause);
    }


    @Override
    public String getLocalizedMessage() {
        String message;
        try {
            message = messages.getString(getMessage());
        } catch (MissingResourceException mre) {
            message = "No resource for " + getMessage() + "key";
        }
        return message;
    }
}


