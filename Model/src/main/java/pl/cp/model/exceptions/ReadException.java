package pl.cp.model.exceptions;


import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ReadException extends SudokuException {

    private static final ResourceBundle messages;

    public static final String READ_EXE = "readexe";

    static {
        Locale locale = Locale.getDefault(Locale.Category.DISPLAY);
        messages = ResourceBundle.getBundle("exceptions", locale);
    }



    public ReadException() {
        super(new Throwable(ReadException.READ_EXE));
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

