package ru.vniizht.asuter.autotest.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import ru.vniizht.asuter.autotest.constants.Execution;
import ru.vniizht.asuter.autotest.utils.UrlDeterminator;

import java.util.function.Consumer;
import java.util.function.Function;

public class BasePage<P extends BasePage<P>> {

    private static final UrlDeterminator urlDeterminator = new UrlDeterminator();

    public static String urlOf(Class<? extends BasePage<?>> pageClass) {
        return urlDeterminator.urlOf(pageClass);
    }

    public static <T extends BasePage<T>> T open(Class<T> pageClass) {
        String url = urlDeterminator.urlOf(pageClass);
        Selenide.open(url);
        return Selenide.page(pageClass);
    }

    @SuppressWarnings("unchecked")
    public P also(Consumer<P> action) {
        action.accept((P) this);
        return (P) this;
    }

    /** То же самое, что и also. Для более читабельных цепочек методов. */
    @SuppressWarnings("unchecked")
    public P check(Consumer<P> action) {
        action.accept((P) this);
        return (P) this;
    }

    @SuppressWarnings("unchecked")
    public P clickOn(Function<P, WebElement> getElement) {
        getElement.apply((P) this).click();
        return (P) this;
    }

    /** Вызывает метод sendKeys(key) на текущем выделенном элементе */
    @SuppressWarnings("unchecked")
    public P pressKey(CharSequence key) {
        SelenideElement focusedElement = Selenide.getFocusedElement();
        if (focusedElement != null) focusedElement.sendKeys(key);
        return (P) this;
    }

    /** Вызывает метод sendKeys(Keys.TAB) на текущем выделенном элементе */
    public P pressTab() {
        return pressKey(Keys.TAB);
    }

    /** Запускает метод wait в synchronized блоке */
    public P waitTime() {
        return waitTime(Execution.DEFAULT_WAIT_TIME_MILLIS);
    }

    /** Запускает метод wait в synchronized блоке */
    @SuppressWarnings("unchecked")
    public P waitTime(long milliseconds) {
        synchronized (this) {
            try {
                this.wait(milliseconds);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return (P) this;
    }

    public static void typeIn(SelenideElement element, String value) {
        element.clear();
        element.sendKeys(value);
    }
}
