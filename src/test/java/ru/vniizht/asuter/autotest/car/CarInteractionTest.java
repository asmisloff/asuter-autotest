package ru.vniizht.asuter.autotest.car;

import com.codeborne.selenide.Condition;
import io.qase.api.annotation.QaseId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.vniizht.asuter.autotest.constants.Color;
import ru.vniizht.asuter.autotest.constants.User;
import ru.vniizht.asuter.autotest.pages.transport.cars.PageCarsList;
import ru.vniizht.asuter.autotest.pages.transport.trains.PageTrainsList;

import java.util.LinkedList;
import java.util.Queue;

import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Condition.*;

@DisplayName("Вагоны: Взаимодействие с другими страницами")
public class CarInteractionTest extends CarBaseTest {

    private final static Queue<String> carNamesToDelete = new LinkedList<>();
    private final static Queue<String> trainNamesToDelete = new LinkedList<>();

    @AfterEach
    public void deleteCreated() {
        while (!carNamesToDelete.isEmpty()) {
            deleteCarByName(carNamesToDelete.poll());
        }
        while (!trainNamesToDelete.isEmpty()) {
            deleteTrainByName(trainNamesToDelete.poll());
        }
    }

    @QaseId(321)
    @Test
    @DisplayName("Созданный вагон отображается в выпадающем списке при создании состава")
    public void whenTrainIsCreatedThenNewCarExists() {
        loginIfNeeded(User.NSI);
        // Создать новый Вагон
        Car car = createTestCarForCase(321);
        open(PageCarsList.class)
                .clickCreate()
                .also(car::inputTo)
                .clickSave()
                .also(p -> carNamesToDelete.add(car.name()));

        // Перейти на страницу Составы, нажать Создать
        // Проверить, что созданный вагон есть в списке
        open(PageTrainsList.class)
                .clickCreate()
                .getFirstCarRow().check(r -> {
                    r.carNameSelect.getOptions().shouldHave(anyMatch(
                            "Наименование вагона: " + car.name(),
                            option -> option.getText().equals(car.name()))
                    );
                });
    }

    @QaseId(322)
    @Test
    @DisplayName("Созданный вагон можно выбрать в выпадающем списке при создании состава")
    public void whenTrainIsCreatedThenNewCarCanBeSelected() {
        loginIfNeeded(User.NSI);
        // Создать новый Вагон
        Car car = createTestCarForCase(322);
        open(PageCarsList.class)
                .clickCreate()
                .also(car::inputTo)
                .clickSave()
                .also(p -> carNamesToDelete.add(car.name()));

        // Перейти на страницу Составы, нажать Создать
        // Выбрать созданный вагон в выпадающем списке Наименование вагона
        open(PageTrainsList.class)
                .clickCreate()
                .getFirstCarRow()
                .also(r -> r.carNameSelect.selectOption(car.name()))
                .check(r -> r.carNameSelect.shouldHave(selectedText(car.name())));
    }

    @QaseId(323)
    @Test
    @DisplayName("Созданный вагон отображается в выпадающем списке при редактировании состава")
    public void whenTrainIsEditedThenNewCarIsDisplayed() {
        loginIfNeeded(User.NSI);
        // Создать новый Вагон
        Car car = createTestCarForCase(323);
        open(PageCarsList.class)
                .clickCreate()
                .also(car::inputTo)
                .clickSave()
                .also(p -> carNamesToDelete.add(car.name()));

        // Создать состав
        final String trainName = "train-autotest_case323";
        open(PageTrainsList.class)
                .clickCreate()
                .inputName(trainName)
                .getFirstCarRow()
                .also(c -> {
                    c.selectCarNameByIndex(3);
                    c.inputNumCars("1");
                })
                .getPageTrain()
                .clickSave();
        trainNamesToDelete.add(trainName);

        // Открыть созданный состав, проверить наличие созданного вагона в списке
        open(PageTrainsList.class)
                .waitTableLoading()
                .findTrainRowByName(trainName)
                .openTrain()
                .getFirstCarRow().check(r -> {
                    r.carNameSelect.getOptions().shouldHave(anyMatch(
                            "Наименование вагона: " + car.name(),
                            option -> option.getText().equals(car.name()))
                    );
                });
    }

    @QaseId(324)
    @Test
    @DisplayName("Созданный вагон можно выбрать в выпадающем списке при редактировании состава")
    public void whenTrainIsEditedThenNewCarCanBeSelected() {
        loginIfNeeded(User.NSI);
        // Создать новый Вагон
        Car car = createTestCarForCase(324);
        open(PageCarsList.class)
                .clickCreate()
                .also(car::inputTo)
                .clickSave()
                .also(p -> carNamesToDelete.add(car.name()));

        // Создать состав
        final String trainName = "train-autotest_case324";
        open(PageTrainsList.class)
                .clickCreate()
                .inputName(trainName)
                .getFirstCarRow()
                .also(c -> {
                    c.selectCarNameByIndex(4);
                    c.inputNumCars("1");
                })
                .getPageTrain()
                .clickSave();
        trainNamesToDelete.add(trainName);

        // Открыть созданный состав, выбрать созданный вагон в списке
        open(PageTrainsList.class)
                .waitTableLoading()
                .findTrainRowByName(trainName)
                .openTrain()
                .clickEdit()
                .getFirstCarRow()
                .also(r -> r.carNameSelect.selectOption(car.name()))
                .check(r -> r.carNameSelect.shouldHave(selectedText(car.name())));
    }

    @QaseId(325)
    @Test
    @DisplayName("Удаленный вагон не отображается в выпадающем списке при создании состава")
    public void whenTrainIsCreatedThenDeletedCarIsNotDisplayed() {
        loginIfNeeded(User.NSI);
        // Создать новый Вагон
        Car car = createTestCarForCase(325);
        open(PageCarsList.class)
                .clickCreate()
                .also(car::inputTo)
                .clickSave();
        // Удалить созданный Вагон
        deleteCarByName(car.name());

        // Перейти на страницу Составы, нажать Создать
        // Удаленный Вагон не должен отображаться в выпадающем списке Наименование вагона
        open(PageTrainsList.class)
                .clickCreate()
                .getFirstCarRow().check(r -> {
                    r.carNameSelect.getOptions().shouldHave(noneMatch(
                            "Наименование вагона: " + car.name(),
                            option -> option.getText().equals(car.name()))
                    );
                });
    }

    @QaseId(326)
    @Test
    @DisplayName("Удаленный вагон не отображается в выпадающем списке при редактировании состава")
    public void whenTrainIsEditedThenDeletedCarIsNotDisplayed() {
        loginIfNeeded(User.NSI);
        // Создать Вагон
        Car car = createTestCarForCase(326);
        open(PageCarsList.class)
                .clickCreate()
                .also(car::inputTo)
                .clickSave();
        // Удалить созданный Вагон
        deleteCarByName(car.name());

        // Создать состав
        final String trainName = "train-autotest_case326";
        open(PageTrainsList.class)
                .clickCreate()
                .inputName(trainName)
                .getFirstCarRow()
                .also(c -> {
                    c.selectCarNameByIndex(3);
                    c.inputNumCars("1");
                })
                .getPageTrain()
                .clickSave();
        trainNamesToDelete.add(trainName);

        // Открыть созданный состав
        // Удаленный Вагон не должен отображаться в выпадающем списке Наименование вагона
        open(PageTrainsList.class)
                .waitTableLoading()
                .findTrainRowByName(trainName)
                .openTrain()
                .clickEdit()
                .getFirstCarRow().check(r -> {
                    r.carNameSelect.getOptions().shouldHave(noneMatch(
                            "Наименование вагона: " + car.name(),
                            option -> option.getText().equals(car.name()))
                    );
                });
    }

    @QaseId(327)
    @Test
    @DisplayName("Отображение удаленного вагона при редактировании состава")
    public void testEditTrainWithSelectedDeletedCar() {
        loginIfNeeded(User.NSI);
        // Создать Вагон
        Car car = createTestCarForCase(327);
        open(PageCarsList.class)
                .clickCreate()
                .also(car::inputTo)
                .clickSave();

        // Создать состав с созданным вагоном
        final String trainName = "train-autotest_case327";
        open(PageTrainsList.class)
                .clickCreate()
                .inputName(trainName)
                .getFirstCarRow()
                .also(c -> {
                    c.selectCarName(car.name());
                    c.inputNumCars("1");
                })
                .getPageTrain()
                .clickSave();
        trainNamesToDelete.add(trainName);

        // Удалить используемый в составе вагон
        deleteCarByName(car.name());

        // Открыть созданный состав
        // Удаленный Вагон не должен отображаться в выпадающем списке Наименование вагона
        open(PageTrainsList.class)
                .waitTableLoading()
                .findTrainRowByName(trainName)
                .openTrain()
                .check(p -> {
                    p.originalDataChangedTitle.should(Condition.match(
                            "Текст title должен быть \"Исходные данные были изменены\"",
                            element -> "Исходные данные были изменены".equals(element.getAttribute("innerHTML")))
                    );
                })
                .clickEdit()
                .check(p -> {
                    p.getFirstCarRow()
                            .check(r -> {
                                r.carNameSelect.shouldHave(attribute(
                                        "title",
                                        "Вагон был удалён из базы данных, но может быть использован в текущем составе"));
                                r.carNameSelect.shouldNotHave(cssValue("background-color", Color.WHITE.rgbaValue));
                            });
                });
    }

    @QaseId(328)
    @Test
    @DisplayName("Отображение удаленного вагона в скопированном составе")
    public void testEditCopiedTrainWithDeletedCar() {
        loginIfNeeded(User.NSI);
        // Создать Вагон
        Car car = createTestCarForCase(328);
        open(PageCarsList.class)
                .clickCreate()
                .also(car::inputTo)
                .clickSave();

        // Создать состав с созданным вагоном
        final String trainName = "train-autotest_case328";
        open(PageTrainsList.class)
                .clickCreate()
                .inputName(trainName)
                .getFirstCarRow()
                .also(c -> {
                    c.selectCarName(car.name());
                    c.inputNumCars("1");
                })
                .getPageTrain()
                .clickSave();
        trainNamesToDelete.add(trainName);

        // Удалить используемый в составе вагон
        deleteCarByName(car.name());

        // Скопировать созданный состав
        open(PageTrainsList.class)
                .waitTableLoading()
                .findTrainRowByName(trainName)
                .copy();
        final String copiedTrainName = trainName + "(1)";

        // Открыть скопированный состав
        // Удаленный Вагон не должен отображаться в выпадающем списке Наименование вагона
        open(PageTrainsList.class)
                .waitTableLoading()
                .findTrainRowByName(copiedTrainName)
                .openTrain()
                .check(p -> {
                    p.originalDataChangedTitle.should(Condition.match(
                            "Текст title должен быть \"Исходные данные были изменены\"",
                            element -> "Исходные данные были изменены".equals(element.getAttribute("innerHTML")))
                    );
                })
                .clickEdit()
                .check(p -> {
                    p.getFirstCarRow()
                            .check(r -> {
                                r.carNameSelect.shouldHave(attribute(
                                        "title",
                                        "Вагон был удалён из базы данных, но может быть использован в текущем составе"));
                                r.carNameSelect.shouldNotHave(cssValue("background-color", Color.WHITE.rgbaValue));
                            });
                });
    }
}
