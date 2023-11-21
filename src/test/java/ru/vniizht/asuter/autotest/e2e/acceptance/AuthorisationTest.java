package ru.vniizht.asuter.autotest.e2e.acceptance;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.vniizht.asuter.autotest.BaseTest;
import ru.vniizht.asuter.autotest.pages.login.PageLogin;
import ru.vniizht.asuter.autotest.pages.main.PageMain;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.url;

// TODO блокировка учетной записи после 5 попыток входа
// TODO восстановление пароля

// ASUTER-37
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthorisationTest extends BaseTest {

    private static final String logPassCsvPath = "/csv/logpass/basic_valid/logpass.csv";


    @ParameterizedTest
    @CsvFileSource(resources = logPassCsvPath)
    @Order(1)
    @DisplayName("Вход под логином и паролем действующего пользователя")
    void validLoginAndPassword(String login, String password) {
        open(PageLogin.class)
                .enterUsername(login)
                .enterPassword(password)
                .clickLogin();
        webdriver().shouldHave(url(urlOf(PageMain.class)));
    }

    @Test
    @Order(2)
    @DisplayName("Выход из профиля и возвращение на страницу авторизации")
    void logoutFromMainPage() {
        page(PageMain.class)
                .clickLogout();
        webdriver().shouldHave(url(urlOf(PageLogin.class)));
    }

    @Test
    @Order(3)
    @DisplayName("Попытка перейти на другую страницу возвращает на страницу авторизации")
    void notAuthorizedUnableToNavigate() {
        open(PageMain.class);
        webdriver().shouldHave(url(urlOf(PageLogin.class)));
    }

    @ParameterizedTest
    @CsvFileSource(resources = logPassCsvPath)
    @Order(4)
    @DisplayName("Вход под логином существующего пользователя и неправильным паролем")
    void validLoginInvalidPassword(String login, String password) {
        PageLogin loginPage = open(PageLogin.class);
        loginPage
                .enterUsername(login)
                .enterPassword(password.toUpperCase())
                .clickLogin();

        webdriver()
                .shouldHave(url(PageLogin.loginErrorUrl));

        element(loginPage.divErrorMessage)
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Неверная комбинация имени пользователя и пароля"));
    }

    @ParameterizedTest
    @CsvFileSource(resources = logPassCsvPath)
    @Order(5)
    @DisplayName("Вход под несуществующим логином и существующим паролем")
    void invalidLoginValidPassword(String login, String password) {
        PageLogin loginPage = open(PageLogin.class);
        loginPage
                .enterUsername(login.toUpperCase())
                .enterPassword(password)
                .clickLogin();

        webdriver()
                .shouldHave(url(PageLogin.loginErrorUrl));

        element(loginPage.divErrorMessage)
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Неверная комбинация имени пользователя и пароля"));
    }

    @ParameterizedTest
    @CsvFileSource(resources = logPassCsvPath)
    @Order(6)
    @DisplayName("Незаполненная строка логина")
    void emptyLogin(String login, String password) {
        PageLogin loginPage = open(PageLogin.class);
        loginPage
                .clearUsername()
                .enterPassword(password)
                .clickLogin();

        webdriver().shouldHave(url(urlOf(PageLogin.class)));

        element(loginPage.divInvalidFeedbackLogin)
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    @ParameterizedTest
    @CsvFileSource(resources = logPassCsvPath)
    @Order(7)
    @DisplayName("Незаполненная строка логина")
    void emptyPassword(String login, String password) {
        PageLogin loginPage = open(PageLogin.class);
        loginPage
                .clearPassword()
                .enterUsername(login)
                .clickLogin();

        webdriver().shouldHave(url(urlOf(PageLogin.class)));

        element(loginPage.divInvalidFeedbackPassword)
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

}
