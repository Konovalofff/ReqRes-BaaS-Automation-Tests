package tests.base;

import config.TestConfig;
import helpers.DataCleaner;
import helpers.SessionManager;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static io.restassured.RestAssured.given;

public class BaseApiTest {
    protected static final DataCleaner dataCleaner = new DataCleaner();
    protected static final SessionManager sessionManager = SessionManager.getInstance();

    @BeforeAll
    public static void setUpAll() {
        RestAssured.baseURI = TestConfig.getApiBaseUrl();

        RestAssured.filters(new AllureRestAssured());

        if (System.getProperty("log.all") != null) {
            RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        }

        System.out.println("Начало тестовой сессии");
        System.out.println("Base URL: " + TestConfig.getApiBaseUrl());
        System.out.println("Project ID: " + TestConfig.getProjectId());
    }

    @BeforeEach
    public void setUp() {
        sessionManager.clearAllSessions();
        System.out.println("Подготовка к тесту");
    }

    @AfterAll
    public static void tearDownAll() {
        System.out.println("Окончание тестовой сессии");
    }


    protected boolean isApiAvailable() {
        try {
            return given()
                    .spec(config.ApiConfig.getPublicSpec())
                    .when()
                    .get("/health")
                    .statusCode() == 200;
        } catch (Exception e) {
            return false;
        }
    }
}
