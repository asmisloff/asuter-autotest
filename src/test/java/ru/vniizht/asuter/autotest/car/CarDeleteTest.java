package ru.vniizht.asuter.autotest.car;

import io.qase.api.annotation.QaseId;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.vniizht.asuter.autotest.constants.User;
import ru.vniizht.asuter.autotest.pages.transport.cars.PageCarsList;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertFalse;

@DisplayName("Вагоны: Удаление")
public class CarDeleteTest extends CarBaseTest {

    private final static Queue<String> namesToDelete = new LinkedList<>();

    @AfterAll
    public static void deleteCreated() {
        while (!namesToDelete.isEmpty()) {
            deleteCarByName(namesToDelete.poll());
        }
    }

    @QaseId(301)
    @Test
    @DisplayName("Удаление вагона")
    public void testCarDelete() {
        loginIfNeeded(User.NSI);
        // Создать вагон
        Car car = createTestCarForCase(301);
        open(PageCarsList.class)
                .clickCreate()
                .also(car::inputTo)
                .clickSave();

        // Удалить вагон
        open(PageCarsList.class)
                .waitTableLoading()
                .findCarRowByName(car.name())
                .contextClick()
                .clickDelete()
                .clickModalDeleteOk();

        // Проверить что вагон удален
        assertFalse(
                open(PageCarsList.class)
                        .waitTableLoading()
                        .containsCarRowByName(car.name())
        );
    }

    @QaseId(302)
    @Test
    @DisplayName("Удаление скопированного вагона")
    public void testDeleteCopiedCar() {
        loginIfNeeded(User.NSI);
        // Создать вагон
        Car car = createTestCarForCase(302);
        open(PageCarsList.class)
                .clickCreate()
                .also(car::inputTo)
                .clickSave();
        namesToDelete.add(car.name());

        // Копировать существующий вагон
        open(PageCarsList.class)
                .waitTableLoading()
                .findCarRowByName(car.name())
                .contextClick()
                .clickCopy();

        // Удалить скопированный вагон
        final String expectedCopyName = car.name() + "(1)";
        open(PageCarsList.class)
                .waitTableLoading()
                .findCarRowByName(expectedCopyName)
                .contextClick()
                .clickDelete()
                .clickModalDeleteOk();

        // Проверить что скопированный вагон удален
        assertFalse(
                open(PageCarsList.class)
                        .waitTableLoading()
                        .containsCarRowByName(expectedCopyName)
        );
    }
}
