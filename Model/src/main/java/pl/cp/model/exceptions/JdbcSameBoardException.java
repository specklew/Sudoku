package pl.cp.model.exceptions;


import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class JdbcSameBoardException extends JdbcExecuteQueryException {
    private static final ResourceBundle messages;

    public static final String SAME_BOARD = "sameboard";

    static {
        Locale locale = Locale.getDefault(Locale.Category.DISPLAY);
        messages = ResourceBundle.getBundle("exceptions", locale);
    }

    public JdbcSameBoardException(Throwable cause) {
        super(cause);
    }

    public JdbcSameBoardException(String message, Throwable cause) {
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
