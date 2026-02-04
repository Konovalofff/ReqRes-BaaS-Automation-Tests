package helpers;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {
    private static SessionManager instance;
    private final Map<String, String> emailToToken = new HashMap<>();
    private final Map<String, String> tokenToEmail = new HashMap<>();

    private SessionManager() {
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void saveSession(String email, String sessionToken) {
        emailToToken.put(email, sessionToken);
        tokenToEmail.put(sessionToken, email);
        System.out.println("Сессия сохранена для: " + email);
    }

    public String getTokenByEmail(String email) {
        return emailToToken.get(email);
    }

    public String getEmailByToken(String sessionToken) {
        return tokenToEmail.get(sessionToken);
    }

    public void removeSession(String sessionToken) {
        String email = tokenToEmail.remove(sessionToken);
        if (email != null) {
            emailToToken.remove(email);
            System.out.println("Сессия очищена для: " + email);
        }
    }

    public boolean hasSession(String email) {
        return emailToToken.containsKey(email);
    }

    public void clearAllSessions() {
        emailToToken.clear();
        tokenToEmail.clear();
        System.out.println("Все сессии очищены");
    }
}
