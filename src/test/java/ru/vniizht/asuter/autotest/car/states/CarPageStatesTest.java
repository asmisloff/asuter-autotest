package ru.vniizht.asuter.autotest.car.states;

import com.codeborne.selenide.Condition;
import io.qase.api.annotation.QaseId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.vniizht.asuter.autotest.car.Car;
import ru.vniizht.asuter.autotest.car.CarBaseTest;
import ru.vniizht.asuter.autotest.constants.Color;
import ru.vniizht.asuter.autotest.pages.transport.cars.PageCarsList;

import static com.codeborne.selenide.Condition.*;

@DisplayName("Вагоны: Состояния и переходы")
public class CarPageStatesTest extends CarBaseTest {

    @QaseId(284)
    @Test
    @DisplayName("Вагон открывается в режиме создания при нажатии на Создать")
    public void testCreateState() {
        loginIfNeeded();
        open(PageCarsList.class)
                .clickCreate()
                .check(p -> {
                    p.pageHeaderText.shouldHave(text("Создание"));
                    p.nameInput.shouldBe(enabled);
                    p.typeSelect.shouldBe(enabled);
                    p.numberOfAxlesSelect.shouldBe(enabled);
                    p.containerMassInput.shouldBe(enabled);
                    p.netMassInput.shouldBe(enabled);
                    p.lengthInput.shouldBe(enabled);
                });
    }

    @QaseId(285)
    @Test
    @DisplayName("Сохраненный вагон открывается в режиме просмотра")
    public void testSavedCarState() {
        final String testCarName = CAR_NAME + "_case285";
        loginIfNeeded();
        open(PageCarsList.class)
                .clickCreate()
                .also(p -> createCar(testCarName).inputTo(p))
                .clickSave();

        open(PageCarsList.class)
                .ensureTableExists()
                .findCarRowByName(testCarName)
                .openCar()
                .check(p -> {
                    p.pageHeaderText.shouldHave(text(CAR_NAME));
                    p.nameInput.shouldBe(disabled);
                    p.typeSelect.shouldBe(disabled);
                    p.numberOfAxlesSelect.shouldBe(disabled);
                    p.containerMassInput.shouldBe(disabled);
                    p.netMassInput.shouldBe(disabled);
                    p.lengthInput.shouldBe(disabled);
                });

        deleteCarByName(testCarName);
    }

    @QaseId(286)
    @Test
    @DisplayName("Вагон переходит в режим просмотра после сохранения")
    public void testModifiedAndSavedCarState() {
        final String testCarName = CAR_NAME + "_case286";
        loginIfNeeded();
        Car car = createCar(testCarName);
        open(PageCarsList.class)
                .clickCreate()
                .also(car::inputTo)
                .clickSave();

        final String modifiedCarName = testCarName + "_2";
        open(PageCarsList.class)
                .ensureTableExists()
                .findCarRowByName(testCarName)
                .openCar()
                .clickEdit()
                .also(p -> {
                    p.inputName(modifiedCarName);
                    p.inputContainerMass("51");
                    p.inputNetMass("2");
                    p.inputLength(car.length() + "2");
                    p.pressTab();
                })
                .clickSave()
                .check(p -> {
                    p.pageHeaderText.shouldHave(text(modifiedCarName));
                    p.nameInput.shouldBe(disabled);
                    p.typeSelect.shouldBe(disabled);
                    p.numberOfAxlesSelect.shouldBe(disabled);
                    p.containerMassInput.shouldBe(disabled);
                    p.netMassInput.shouldBe(disabled);
                    p.lengthInput.shouldBe(disabled);
                });

        deleteCarByName(modifiedCarName);
    }

    @QaseId(287)
    @Test
    @DisplayName("Вагон переходит в режим редактирования при нажатии на кнопку")
    public void testModifiedCarState() {
        final String testCarName = CAR_NAME + "_case287";
        loginIfNeeded();
        Car car = createCar(testCarName);
        open(PageCarsList.class)
                .clickCreate()
                .also(car::inputTo)
                .clickSave();

        open(PageCarsList.class)
                .ensureTableExists()
                .findCarRowByName(testCarName)
                .openCar()
                .clickEdit()
                .check(p -> {
                    p.pageHeaderText.shouldHave(text(testCarName));
                    p.nameInput.shouldBe(enabled);
                    p.typeSelect.shouldBe(enabled);
                    p.numberOfAxlesSelect.shouldBe(enabled);
                    p.containerMassInput.shouldBe(enabled);
                    p.netMassInput.shouldBe(enabled);
                    p.lengthInput.shouldBe(enabled);
                })
                .also(p -> p.clearName().pressTab())
                .check(p -> p.pageHeaderText.shouldHave(text("Редактирование")));

        deleteCarByName(testCarName);
    }

    @QaseId(288)
    @Test
    @DisplayName("Кнопка Сохранить активна при валидных данных")
    public void testSaveButtonIsActiveWhenInputIsValid() {
        final String testCarName = CAR_NAME + "_case288";
        loginIfNeeded();
        Car car = createCar(testCarName);
        PageCarsList page = open(PageCarsList.class);
        page.clickCreate()
                .also(car::inputTo)
                .check(p -> {
                    p.saveButton.shouldBe(enabled);
                    p.saveButton.shouldHave(attribute("title", "Сохранить"));
                });
    }

    @QaseId(289)
    @Test
    @DisplayName("Кнопка Сохранить заблокирована при невалидных данных")
    public void testSaveButtonIsDisabledWhenInputIsInvalid() {
        loginIfNeeded();
        PageCarsList page = open(PageCarsList.class);
        page.clickCreate()
                .check(p -> p.saveButton.shouldBe(disabled));
    }

    @QaseId(290)
    @Test
    @DisplayName("Кнопка Сохранить заменяется на Редактировать после сохранения вагона")
    public void testButtonSaveChangesToEdit() {
        loginIfNeeded();
        final String testCarName = CAR_NAME + "_case290";
        Car car = createCar(testCarName);
        open(PageCarsList.class)
                .clickCreate()
                .also(car::inputTo)
                .check(p -> {
                    p.editButton.shouldNot(exist);
                    p.saveButton.should(exist);
                    p.saveButton.shouldHave(attribute("title", "Сохранить"));
                })
                .clickSave()
                .check(p -> {
                    p.saveButton.shouldNot(exist);
                    p.editButton.should(exist);
                    p.editButton.shouldHave(attribute("title", "Редактировать"));
                });

        deleteCarByName(testCarName);
    }

    @QaseId(291)
    @Test
    @DisplayName("Кнопка Редактировать заменяется на Сохранить после нажатия")
    public void testButtonEditChangesToSave() {
        loginIfNeeded();
        final String testCarName = CAR_NAME + "_case291";
        Car car = createCar(testCarName);
        open(PageCarsList.class)
                .clickCreate()
                .also(car::inputTo)
                .clickSave();

        open(PageCarsList.class)
                .ensureTableExists()
                .findCarRowByName(testCarName)
                .openCar()
                .check(p -> {
                    p.editButton.should(exist);
                    p.editButton.shouldHave(attribute("title", "Редактировать"));
                })
                .clickEdit()
                .check(p -> {
                    p.editButton.shouldNot(exist);
                    p.saveButton.should(exist);
                    p.saveButton.shouldHave(attribute("title", "Сохранить"));
                });

        deleteCarByName(testCarName);
    }

    @QaseId(292)
    @Test
    @DisplayName("Переключение фокуса с поля ввода на другое поле ввода по 1 нажатию")
    public void testFocus() {
        loginIfNeeded();
        open(PageCarsList.class)
                .clickCreate()
                .also(p -> {
                    p.nameInput.click();
                    p.nameInput.shouldBe(focused);
                    p.containerMassInput.click();
                    p.containerMassInput.shouldBe(focused);
                    p.netMassInput.click();
                    p.netMassInput.shouldBe(focused);
                    p.lengthInput.click();
                    p.lengthInput.shouldBe(focused);
                });
    }

    @QaseId(293)
    @Test
    @DisplayName("Поля ввода вагона изменяют цвет при фокусе")
    public void testNameFieldChangesColor() {
        final Condition whiteBackground = cssValue("background-color", Color.WHITE.rgbaValue);
        final Condition redBackground = cssValue("background-color", Color.PINK.rgbaValue);
        loginIfNeeded();
        open(PageCarsList.class)
                .clickCreate()
                .also(p -> {
                    p.nameInput.shouldHave(redBackground);
                    p.nameInput.click();
                    p.nameInput.shouldHave(whiteBackground);

                    p.containerMassInput.shouldHave(redBackground);
                    p.containerMassInput.click();
                    p.containerMassInput.shouldHave(whiteBackground);

                    p.netMassInput.shouldHave(redBackground);
                    p.netMassInput.click();
                    p.netMassInput.shouldHave(whiteBackground);

                    p.lengthInput.shouldHave(redBackground);
                    p.lengthInput.click();
                    p.lengthInput.shouldHave(whiteBackground);
                });
    }

    @QaseId(294)
    @ParameterizedTest
    @ValueSource(strings = {"0,0000000001", "0,1", "0,56", "99,999", "9", "999999999999999999"})
    @DisplayName("Значения не изменяются при снятии фокуса с полей ввода")
    public void testValuesNotChanging(String v) {
        loginIfNeeded();
        open(PageCarsList.class)
                .clickCreate()
                .also(p -> {
                    p.inputContainerMass(v);
                    p.pressTab();
                    p.containerMassInput.shouldHave(value(v));
                    p.inputContainerMass(v);
                    p.pressTab();
                    p.containerMassInput.shouldHave(value(v));

                    p.inputNetMass(v);
                    p.pressTab();
                    p.netMassInput.shouldHave(value(v));
                    p.inputNetMass(v);
                    p.pressTab();
                    p.netMassInput.shouldHave(value(v));

                    p.inputLength(v);
                    p.pressTab();
                    p.lengthInput.shouldHave(value(v));
                    p.inputLength(v);
                    p.pressTab();
                    p.lengthInput.shouldHave(value(v));
                });
    }

    @QaseId(295)
    @Test
    @DisplayName("Формула отображается при нажатии на кнопку")
    public void testFormulaIsVisible() {
        loginIfNeeded();
        open(PageCarsList.class)
                .clickCreate()
                .also(p -> {
                    p.clickFormulaForCalculatingResistanceHeader();
                    p.formulaForCalculatingResistance.should(exist);
                    p.formulaForCalculatingResistance.shouldBe(visible);
                });
    }

    @QaseId(296)
    @Test
    @DisplayName("Раскрытая формула скрывается при нажатии на кнопку")
    public void testFormulaIsHidden() {
        loginIfNeeded();
        open(PageCarsList.class)
                .clickCreate()
                .also(p -> {
                    p.clickFormulaForCalculatingResistanceHeader();
                    p.formulaForCalculatingResistance.should(exist);
                    p.clickFormulaForCalculatingResistanceHeader();
                    p.formulaForCalculatingResistance.shouldNot(exist);
                });
    }

    @QaseId(347)
    @ParameterizedTest
    @CsvSource(value = {
            "8:15,999",
            "8:15,998",
            "8,003:15,999"
    }, delimiter = ':')
    @DisplayName("Значения округляются в заблокированных полях при дробных значениях в mв и mг,т")
    public void testValuesRounded(String containerMass, String netMass) {
        final String integerOr1to3Decimals = "^\\d+(,\\d{1,3})?$";
        loginIfNeeded();
        open(PageCarsList.class)
                .clickCreate()
                .also(p -> {
                    p.inputContainerMass(containerMass);
                    p.pressTab();
                    p.fullMassInput.should(attributeMatching("value", integerOr1to3Decimals));
                    p.massPerAxleInput.should(attributeMatching("value", integerOr1to3Decimals));
                    p.inputNetMass(netMass);
                    p.pressTab();
                    p.fullMassInput.should(attributeMatching("value", integerOr1to3Decimals));
                    p.massPerAxleInput.should(attributeMatching("value", integerOr1to3Decimals));
                });
    }

    private Car createCar(String name) {
        return Car.builder()
                .name(name)
                .length(VALID_LENGTH)
                .containerMass(CONTAINER_MASS_FOR_Q_GREATER_6)
                .coefficients(coefficients)
                .build();
    }
}
