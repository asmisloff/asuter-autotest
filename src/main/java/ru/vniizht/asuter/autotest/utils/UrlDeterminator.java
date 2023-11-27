package ru.vniizht.asuter.autotest.utils;

import ru.vniizht.asuter.autotest.annotation.Url;
import ru.vniizht.asuter.autotest.constants.Urls;
import ru.vniizht.asuter.autotest.pages.BasePage;

public class UrlDeterminator {

    public String urlOf(Class<? extends BasePage> pageClass) {
        Url annotation = pageClass.getAnnotation(Url.class);
        if (annotation != null) {
            var fullUrl = annotation.fullUrl();
            if (!fullUrl.isBlank()) return fullUrl;
            else return Urls.baseUrl + annotation.value();
        }
        else throw new IllegalStateException("Методу urlOf необходима аннотация Url над классом страницы.");
    }

}
