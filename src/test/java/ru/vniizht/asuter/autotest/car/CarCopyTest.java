package ru.vniizht.asuter.autotest.car;

import io.qase.api.annotation.QaseId;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.vniizht.asuter.autotest.constants.User;
import ru.vniizht.asuter.autotest.pages.transport.cars.PageCarsList;

import java.util.LinkedList;
import java.util.Queue;

import static com.codeborne.selenide.Condition.*;

@DisplayName("Вагоны: Копирование")
public class CarCopyTest extends CarBaseTest {

    private final static Queue<String> namesToDelete = new LinkedList<>();

    @AfterAll
    public static void deleteCreated() {
        while (!namesToDelete.isEmpty()) {
            deleteCarByName(namesToDelete.poll());
        }
    }

    @QaseId(303)
    @Test
    @DisplayName("Копирование вагона")
    public void testCarCopy() {
        // Создать вагон
        loginIfNeeded(User.NSI);
        Car car = createTestCarForCase(303);
        open(PageCarsList.class)
                .clickCreate()
                .also(car::inputTo)
                .clickSave()
                .also(p -> namesToDelete.add(car.name()));

        // Копировать существующий вагон
        open(PageCarsList.class)
                .ensureTableExists()
                .findCarRowByName(car.name())
                .contextClick()
                .clickCopy();

        // Проверить имя копии вагона
        final String expectedName = car.name() + "(1)";
        open(PageCarsList.class)
                .ensureTableExists()
                .findCarRowByName(expectedName)
                .check(r -> r.carName.shouldHave(text(expectedName)))
                .also(p -> namesToDelete.add(expectedName));
    }

    @QaseId(304)
    @Test
    @DisplayName("Наименование при копировании вагона")
    public void testCarCopyNames() {
        loginIfNeeded(User.NSI);
        // Создать вагон
        Car car = createTestCarForCase(304);
        open(PageCarsList.class)
                .clickCreate()
                .also(car::inputTo)
                .clickSave()
                .also(p -> namesToDelete.add(car.name()));

        // Копировать существующий вагон
        open(PageCarsList.class)
                .ensureTableExists()
                .findCarRowByName(car.name())
                .contextClick()
                .clickCopy();

        // Проверить имя 1-й копии вагона
        final String expectedName1 = car.name() + "(1)";
        open(PageCarsList.class)
                .ensureTableExists()
                .findCarRowByName(expectedName1)
                .check(r -> r.carName.shouldHave(text(expectedName1)))
                .also(p -> namesToDelete.add(expectedName1));

        // Копировать существующий вагон еще раз
        open(PageCarsList.class)
                .ensureTableExists()
                .findCarRowByName(car.name())
                .contextClick()
                .clickCopy();

        // Проверить имя 2-й копии вагона
        final String expectedName2 = car.name() + "(2)";
        open(PageCarsList.class)
                .ensureTableExists()
                .findCarRowByName(expectedName2)
                .check(r -> r.carName.shouldHave(text(expectedName2)))
                .also(p -> namesToDelete.add(expectedName2));

        // Копировать 1-ю копию вагона
        open(PageCarsList.class)
                .ensureTableExists()
                .findCarRowByName(expectedName1)
                .contextClick()
                .clickCopy();

        // Проверить имя 3-й копии вагона
        final String expectedName3 = car.name() + "(1)(1)";
        open(PageCarsList.class)
                .ensureTableExists()
                .findCarRowByName(expectedName3)
                .check(r -> r.carName.shouldHave(text(expectedName3)))
                .also(p -> namesToDelete.add(expectedName3));
    }
}
