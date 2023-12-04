package ru.vniizht.asuter.autotest;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import ru.vniizht.asuter.autotest.constants.User;
import ru.vniizht.asuter.autotest.pages.BasePage;
import ru.vniizht.asuter.autotest.pages.login.PageLogin;
import ru.vniizht.asuter.autotest.pages.main.PageMain;
import ru.vniizht.asuter.autotest.utils.UrlDeterminator;

public class BaseTest {

    private static final UrlDeterminator urlDeterminator = new UrlDeterminator();
    private static User currentUser = null;

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
        login(new User(login, password));
    }

    /**
     * Открыть страницу авторизации и зайти под логином и паролем указанного пользователя
     */
    public static void login(User user) {
        open(PageLogin.class)
                .enterUsername(user.login())
                .enterPassword(user.password())
                .clickLogin();
        currentUser = user;
    }

    /**
     * Открыть страницу авторизации и зайти под стандартным логином и паролем (с правом выполнения расчетов)
     */
    public static void login() {
        login(User.NSI);
    }

    public static void loginIfNeeded() {
        loginIfNeeded(User.NSI);
    }

    public static void loginIfNeeded(String login, String password) {
        loginIfNeeded(new User(login, password));
    }

    public static void loginIfNeeded(User user) {
        if (user.equals(currentUser)) return;
        if (currentUser != null) logout();
        login(user);
    }

    /** Перейти на главную страницу и нажать кнопку "Выход" */ // TODO если на выходе появится data-testid можно не ходить на главную
    public static void logout() {
        open(PageMain.class).clickLogout();
        currentUser = null;
    }

    public static void logoutIfNeeded() {
        if (currentUser != null) logout();
    }
}
