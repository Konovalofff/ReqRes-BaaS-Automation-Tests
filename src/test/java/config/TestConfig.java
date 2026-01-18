package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestConfig {
    private static final Properties properties = new Properties();

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try (InputStream input = TestConfig.class
                .getClassLoader()
                .getResourceAsStream("application.properties")) {
            if (input != null) {
                properties.load(input);
                System.out.println("Загружено из application.properties");
            }

            overrideFromEnv();

        } catch (IOException e) {
            throw new RuntimeException("Ошибка загрузки", e);
        }
    }

    private static void overrideFromEnv() {
        String apiKey = System.getenv("REQRES_API_KEY");
        if (apiKey != null && !apiKey.isEmpty()) {
            properties.setProperty("api.key", apiKey);
        }

        String baseUrl = System.getenv("REQRES_BASE_URL");
        if (baseUrl != null && !baseUrl.isEmpty()) {
            properties.setProperty("api.base.url", baseUrl);
        }
    }

    public static String getApiBaseUrl() {
        return properties.getProperty("api.base.url", "https://reqres.in/api/custom/");
    }

    public static String getApiKey() {
        return properties.getProperty("api.key", "");
    }

    public static String getPublicKey() {
        return properties.getProperty("public.key", "");
    }

    public static String getProjectId() {
        return properties.getProperty("project.id", "1832");
    }

    public static boolean isCleanupEnabled() {
        return Boolean.parseBoolean(properties.getProperty("cleanup.enabled", "true"));
    }

    public static int getDefaultTimeout() {
        return Integer.parseInt(properties.getProperty("default.wait.timeout", "30"));
    }

}
