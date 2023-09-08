package ru.vniizht.asuter.autotest;

import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

public class ValidationHelper {

    private static final Logger logger = LoggerFactory.getLogger(ValidationHelper.class);

    public static void checkValue(WebElement element, String expected) {
        logger.info(String.format("Проверка вводимого значения (%s)", expected));
        assertEquals(
                "Проверка введенного значения",
                "1200",
                element.getAttribute("value")
        );
    }

    public static void checkTitle(WebElement element, String expected) {
        logger.info(String.format("Проверка title-а (%s)", expected));
        assertEquals(
                "Title должен содержать сообщение об ошибке",
                " Должно быть не более 1100",
                element.getAttribute("title")
        );
    }
}
