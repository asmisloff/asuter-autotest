package ru.vniizht.asuter.autotest;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import ru.vniizht.asuter.autotest.pages.BasePage;
import ru.vniizht.asuter.autotest.pages.login.PageLogin;
import ru.vniizht.asuter.autotest.utils.UrlDeterminator;

public class BaseTest {

    private static final UrlDeterminator urlDeterminator = new UrlDeterminator();
    private static boolean isLoggedIn = false;

    @BeforeAll
    protected static void configureAllureSelenide() {
        AllureSelenide listener = new CustomAllureSelenideListener()
                .screenshots(true)
                .savePageSource(false);
        SelenideLogger.addListener("AllureSelenide", listener);
    }


    public static String urlOf(Class<? extends BasePage> pageClass) {
        return urlDeterminator.urlOf(pageClass);
    }

    public static <T extends BasePage> T open(Class<T> pageClass) {
        String url = urlDeterminator.urlOf(pageClass);
        Selenide.open(url);
        return Selenide.page(pageClass);
    }

    /**
     * Открыть страницу авторизации и зайти под указанными логином и паролем
     */
    public static void login(String login, String password) {
        open(PageLogin.class)
                .enterUsername(login)
                .enterPassword(password)
                .clickLogin();
    }

    /**
     * Открыть страницу авторизации и зайти под стандартным логином и паролем (с правом выполнения расчетов)
     */
    public static void login() {
        String login = "testnsi"; // TODO не хардкодить, возможно тянуть из csv с тестовыми значениями
        String password = "Testtest2020)"; // TODO не хардкодить, возможно тянуть из csv с тестовыми значениями
        login(login, password);
    }

    public static void loginIfNeeded() {
        if (isLoggedIn) return;
        isLoggedIn = true;
        login();
    }
}
