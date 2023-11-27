package ru.vniizht.asuter.autotest.pages;

import com.codeborne.selenide.Selenide;
import ru.vniizht.asuter.autotest.utils.UrlDeterminator;

public class BasePage {

    private static final UrlDeterminator urlDeterminator = new UrlDeterminator();

    public static String urlOf(Class<? extends BasePage> pageClass) {
        return urlDeterminator.urlOf(pageClass);
    }

    public static <T extends BasePage> T open(Class<T> pageClass) {
        String url = urlDeterminator.urlOf(pageClass);
        Selenide.open(url);
        return Selenide.page(pageClass);
    }
}
