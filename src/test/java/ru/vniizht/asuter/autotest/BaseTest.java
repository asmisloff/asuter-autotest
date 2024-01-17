package ru.vniizht.asuter.autotest;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.By;
import ru.vniizht.asuter.autotest.constants.User;
import ru.vniizht.asuter.autotest.pages.BasePage;
import ru.vniizht.asuter.autotest.pages.login.PageLogin;
import ru.vniizht.asuter.autotest.pages.main.PageMain;
import ru.vniizht.asuter.autotest.pages.transport.cars.CarListRow;
import ru.vniizht.asuter.autotest.pages.transport.cars.PageCarsList;
import ru.vniizht.asuter.autotest.pages.transport.trains.PageTrainsList;
import ru.vniizht.asuter.autotest.utils.UrlDeterminator;

import static com.codeborne.selenide.Selenide.$;

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
     * Пытается без предварительной задержки открыть страницу до 5 раз
     * @param pageClass класс страницы
     * @param targetElementXPath XPath целевого элемента на странице для проверки того, что данные получены
     */
    public static <T extends BasePage> T open(Class<T> pageClass, String targetElementXPath) {
        return open(pageClass, targetElementXPath, 0, 5);
    }

    /**
     * Пытается открыть страницу заданное количество раз.
     * Для уверенности в том, что данные с бэкенда получены используется целевой элемент.
     * Если он не найден, значит данные не получены - ошибка "Failed to fetch" (в Chrome).
     * @param pageClass класс страницы
     * @param targetElementXPath XPath целевого элемента на странице для проверки того, что данные получены
     * @param delayBeforeFetch задержка перед запросом, миллисекунд
     */
    public static <T extends BasePage> T open(Class<T> pageClass, String targetElementXPath, long delayBeforeFetch, int numAttempts) {
        SelenideElement targetElement;
        int attemptCount = 0;
        BasePage page = null;
        boolean exists = false;
        while (!exists) {
            if (delayBeforeFetch > 0) {
                Selenide.sleep(delayBeforeFetch);
            }
            page = open(pageClass);
            targetElement = $(By.xpath(targetElementXPath));
            try {
                // При отсутствии целевого элемента будет ожидание до 4-х секунд, что даст задержку перед повторным запросом
                targetElement.should(Condition.exist);
                exists = true;
            } catch (ElementNotFound ex) {
                attemptCount++;
                if (attemptCount == numAttempts) {
                    throw ex;
                }
            }
        }
        return (T) page;
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

    protected static void deleteCarByName(String name) {
        loginIfNeeded();
        open(PageCarsList.class)
                .ensureTableExists()
                .findCarRowByName(name)
                .delete();
    }

    protected static void deleteCarByName(String name, long delayMilliseconds) {
        loginIfNeeded();
        boolean deleted = false;
        int attemptCount = 0;
        while (!deleted) {
            PageCarsList page = open(PageCarsList.class)
                    .waitTime(delayMilliseconds / 2);
            CarListRow row = null;
            try {
                row = page.findCarRowByName(name);
            } catch (org.openqa.selenium.NoSuchElementException ex) {
                // Если искомый элемент не найден
                // значит возникла ситуация "Failed to fetch".
                // В таком случае пробуем повторить попытку до 5 раз.
                attemptCount++;
                if (attemptCount == 5) {
                    throw ex;
                }
            }
            if (row != null) {
                row.delete();
                deleted = true;
            }
            page.waitTime(delayMilliseconds / 2);
        }
    }

    protected static void deleteTrainByName(String name) {
        loginIfNeeded();
        open(PageTrainsList.class)
                .waitTableLoading()
                .findTrainRowByName(name)
                .delete();
    }
}
