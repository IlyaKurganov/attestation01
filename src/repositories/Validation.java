package repositories;

public class Validation {
    public static String validLogin = "^(?=.*[A-Za-z])[A-Za-z0-9_]{1,19}$";
    public static String validPassword = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z0-9_]{1,19}$";
    public static String validPartOfName = "^[A-Za-zА-Яа-яЁё]+$";

    public static boolean isValidLogin(String login) {
        return login.matches(validLogin);
    }

    public static boolean isValidPassword(String password) {
        return password.matches(validPassword);
    }

    public static boolean isValidPartOfName(String partOfName) {
        return partOfName.matches(validPartOfName);
    }

}




