package classes;

public class Session {
    public static Profile currentUser = null;

    public static void startSession(Profile profile) {
        currentUser = profile;
    }

    public static void endSession() {
        currentUser = null;
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    public static Profile getCurrentUser() {
        return currentUser;
    }
}
