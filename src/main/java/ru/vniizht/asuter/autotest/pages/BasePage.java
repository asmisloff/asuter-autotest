package ru.vniizht.asuter.autotest.pages;

import com.codeborne.selenide.Selenide;
import ru.vniizht.asuter.autotest.annotation.Url;

public class BasePage {

    public static String urlOf(Class<? extends BasePage> pageClass) {
        Url annotation = pageClass.getAnnotation(Url.class);
        if (annotation != null) {
            return annotation.value();
        }
        else throw new IllegalStateException("Методу urlOf необходима аннотация Url над классом страницы.");
    }

    public static <T extends BasePage> T open(Class<T> pageClass) {
        Url annotation = pageClass.getAnnotation(Url.class);
        if (annotation != null) {
            var url = annotation.value();
            Selenide.open(url);
            return Selenide.page(pageClass);
        }
        else throw new IllegalArgumentException("у класса " + pageClass + " нет аннотации Url");
    }
}
