package ru.vniizht.asuter.autotest.car;

import io.qase.api.annotation.QaseId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.vniizht.asuter.autotest.pages.transport.cars.PageCarsList;

import static com.codeborne.selenide.Condition.*;
import static ru.vniizht.asuter.autotest.Messages.*;

@DisplayName("Вагоны: Вёрстка")
public class CarLayout extends CarBaseTest {

    @QaseId(340)
    @Test
    @DisplayName("Подсказки у полей Масса брутто и Масса на ось")
    public void testFullMassAndMassPerAxleTips() {
        loginIfNeeded();
        open(PageCarsList.class)
                .clickCreate()
                .check(p -> {
                    p.fullMassInput.shouldHave(attribute("title", FieldIsFilledInAutomatically));
                    p.massPerAxleInput.shouldHave(attribute("title", FieldIsFilledInAutomatically));
                });
    }
}
