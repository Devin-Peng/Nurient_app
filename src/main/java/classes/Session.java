package classes;

public class Session {
    public static Profile currentUser = null;

    public static void endSession() {
        currentUser = null;
    }

    public static Profile getCurrentUser() {
        return currentUser;
    }
}
