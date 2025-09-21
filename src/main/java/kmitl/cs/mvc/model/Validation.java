package kmitl.cs.mvc.model;

import java.util.regex.Pattern;

public class Validation {
    private static final Pattern EMAIL = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    //for Email Validation
    public static boolean isValidEmail(String e) {
        return e != null && EMAIL.matcher(e).matches();
    }
    //for ID validation
    public static boolean isEightDigitsNoLeadingZero(String id) {
        return id != null && id.matches("^[1-9][0-9]{7}$");
    }
}
