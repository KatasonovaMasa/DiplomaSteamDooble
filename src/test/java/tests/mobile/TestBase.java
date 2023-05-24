package tests.mobile;

import com.codeborne.selenide.Configuration;
import drivers.BrowserstackDriver;
import drivers.LocalDriver;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.logevents.SelenideLogger.addListener;

public class TestBase {
    static String deviceHost = System.getProperty("deviceHost");
    @BeforeAll
    public static void setup() {
        if (deviceHost == null) {
            deviceHost = "local";
        }

        switch (deviceHost) {
            case "local":
                Configuration.browser = LocalDriver.class.getName();
                break;
            case "browserstack":
                Configuration.browser = BrowserstackDriver.class.getName();
                break;
        }
        Configuration.browserSize = null;
    }
    @BeforeEach
    public void startDriver() {
        open();
    }

    @AfterEach
    public void afterEach() {
        String sessionId = sessionId().toString();
        closeWebDriver();
        if ("browserstack".equals(deviceHost)) {
            Attach.addVideo(sessionId);
        }
    }
}