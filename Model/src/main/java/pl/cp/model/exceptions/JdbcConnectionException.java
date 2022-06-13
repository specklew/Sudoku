package pl.cp.model.exceptions;


import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class JdbcConnectionException extends JdbcDaoException {

    private static final ResourceBundle messages;

    public static final String CON_FAIL = "conFail";

    static {
        Locale locale = Locale.getDefault(Locale.Category.DISPLAY);
        messages = ResourceBundle.getBundle("exceptions", locale);
    }


    public JdbcConnectionException(Throwable cause) {
        super(cause);
    }

    public JdbcConnectionException(String message, Throwable cause) {
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

