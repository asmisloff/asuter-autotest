package ru.vniizht.asuter.autotest.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Url {

    /**
     * Постфикс url страницы. Базовый путь - Urls.baseUrl. Если fullUrl указан, это значение игнорируется.
     */
    String value();

    /**
     * Полный URL. Если указан, то используется, игнорируя значение value.
     */
    String fullUrl() default "";

}
