package ru.vniizht.asuter.autotest.car;

import io.qase.api.annotation.QaseId;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.vniizht.asuter.autotest.pages.transport.cars.PageCarsList;

import java.util.LinkedList;
import java.util.Queue;

import static com.codeborne.selenide.Condition.*;

@DisplayName("Вагоны: Изменение данных")
public class CarChangeDataTest extends CarBaseTest {

    private final static Queue<String> namesToDelete = new LinkedList<>();

    @AfterAll
    public static void deleteCreated() {
        while (!namesToDelete.isEmpty()) {
            deleteCarByName(namesToDelete.poll());
        }
    }

    @QaseId(297)
    @Test
    @DisplayName("Изменение данных в выпадающем списке Тип")
    public void testTypeSelect() {
        loginIfNeeded();
        open(PageCarsList.class)
                .clickCreate()
                .also(p -> {
                    p.typeSelect.selectOption("пассажирский");
                    p.typeSelect.shouldHave(selectedText("пассажирский"));
                    p.numberOfAxlesSelect.shouldHave(selectedText("4"));

                    p.typeSelect.selectOption("платформа");
                    p.typeSelect.shouldHave(selectedText("платформа"));
                    p.numberOfAxlesSelect.shouldHave(selectedText("4"));
                });

    }

    @QaseId(298)
    @Test
    @DisplayName("Изменение данных в выпадающем списке Число осей")
    public void testNumberOfAxlesSelect() {
        loginIfNeeded();
        open(PageCarsList.class)
                .clickCreate()
                .also(p -> {
                    p.numberOfAxlesSelect.selectOption("6");
                    p.numberOfAxlesSelect.shouldHave(selectedText("6"));
                    p.typeSelect.shouldHave(selectedText(""));

                    p.numberOfAxlesSelect.selectOption("8");
                    p.numberOfAxlesSelect.shouldHave(selectedText("8"));
                    p.typeSelect.shouldHave(selectedText(""));
                });
    }

    @QaseId(299)
    @Test
    @DisplayName("Изменение данных в выпадающем списке Тип при редактировании вагона")
    public void testTypeSelectEditing() {
        loginIfNeeded();
        Car car = createTestCarForCase(299);
        open(PageCarsList.class)
                .clickCreate()
                .also(car::inputTo)
                .clickSave()
                .also(p -> namesToDelete.add(car.name()))
                .clickEdit()
                .also(p -> {
                    p.typeSelect.selectOption("пассажирский");
                    p.typeSelect.shouldHave(selectedText("пассажирский"));
                    p.numberOfAxlesSelect.shouldHave(selectedText("4"));

                    p.typeSelect.selectOption("платформа");
                    p.typeSelect.shouldHave(selectedText("платформа"));
                    p.numberOfAxlesSelect.shouldHave(selectedText("4"));
                });
    }

    @QaseId(300)
    @Test
    @DisplayName("Изменение данных в выпадающем списке Число осей при редактировании вагона")
    public void testNumberOfAxlesSelectEditing() {
        loginIfNeeded();
        Car car = createTestCarForCase(300);
        open(PageCarsList.class)
                .clickCreate()
                .also(car::inputTo)
                .clickSave()
                .also(p -> namesToDelete.add(car.name()))
                .clickEdit()
                .also(p -> {
                    p.numberOfAxlesSelect.selectOption("10");
                    p.numberOfAxlesSelect.shouldHave(selectedText("10"));
                    p.typeSelect.shouldHave(selectedText(""));

                    p.numberOfAxlesSelect.selectOption("12");
                    p.numberOfAxlesSelect.shouldHave(selectedText("12"));
                    p.typeSelect.shouldHave(selectedText(""));
                });
    }
}
