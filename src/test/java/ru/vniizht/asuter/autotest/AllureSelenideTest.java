package ru.vniizht.asuter.autotest;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;

public class AllureSelenideTest {

    @BeforeAll
    protected static void configureAllureSelenide() {
        SelenideLogger
            .addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true).savePageSource(false));
    }
}
