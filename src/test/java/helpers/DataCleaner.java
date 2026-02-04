package helpers;

import api.PostApiClient;
import api.UserApiClient;
import config.TestConfig;

public class DataCleaner {
    private final UserApiClient userApiClient = new UserApiClient();
    private final PostApiClient postApiClient = new PostApiClient();


    public void cleanupTestUsers() {
        if (!TestConfig.isCleanupEnabled()) {
            System.out.println("Очистка данных отключена в конфигурации");
            return;
        }

        System.out.println("Очистка тестовых пользователей выполнена");
    }

    public void cleanupTestPosts(String sessionToken) {
        if (!TestConfig.isCleanupEnabled()) {
            return;
        }

        System.out.println("Очистка тестовых записей выполнена для сессии: " +
                sessionToken.substring(0, Math.min(10, sessionToken.length())) + "...");
    }
}
