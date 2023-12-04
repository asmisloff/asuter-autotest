package ru.vniizht.asuter.autotest.dcnetwork;

import io.qase.api.annotation.QaseId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.vniizht.asuter.autotest.BaseTest;
import ru.vniizht.asuter.autotest.pages.equipment.electrical.PageDirectNetworkList;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReference;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

@DisplayName("Тяговые сети постоянного тока: переходы и состояния")
public class TransitionsAndStatesTest extends BaseTest {

    private final Queue<String> namesToDelete = new LinkedList<>();

    @AfterEach
    public void deleteCreated() {
        if (namesToDelete.isEmpty()) return;
        PageDirectNetworkList page = open(PageDirectNetworkList.class)
                .waitTime();
        while (!namesToDelete.isEmpty()) {
            page.findRowByNetworkName(namesToDelete.poll()).delete().waitTime();
        }
    }


    @Test
    @QaseId(133)
    @DisplayName("Модальное окно не всплывает при переключении вкладок \"Питающая линия\" и \"Тяговая сеть\"")
    public void modalWindowDoesNotAppearWhenSwitchingSupplyLineAndTractionNetwork() {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .waitTime()
                .inputTrackQuantity(1)
                .selectFirstFeeder()
                .selectFirstTrack()
                .switchToTractionNetwork()
                .check(p -> p.confirmationWindow.parent().shouldHave(cssClass("unvisible"))) // ищем у родителя - так реализовано на фронте
                .switchToSupplyLine()
                .check(p -> p.confirmationWindow.parent().shouldHave(cssClass("unvisible"))); // ищем у родителя - так реализовано на фронте
    }


    @Test
    @QaseId(151)
    @DisplayName("Отмена действия в модальном окне при переключении вкладки")
    public void cancelWhenSwitching() {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .waitTime()
                .switchToTractionNetwork()
                .inputFeederWireQuantity(1)
                .inputContactWireQuantity(1)
                .inputPowerWireQuantity(1)
                .inputTrackQuantity(1)
                .pressTab()
                .selectFirstFeeder()
                .selectFirstContactWire()
                .selectFirstPowerWire()
                .selectFirstTrack()
                .switchToSupplyLine()
                .clickCancel()
                .check(p -> {
                    p.confirmationWindow.parent().shouldHave(cssClass("unvisible")); // ищем у родителя - так реализовано на фронте
                    p.buttonTractionNetwork.parent().shouldHave(cssClass("active")); // ищем у родителя - так реализовано на фронте
                    p.inputFeederQuantity.shouldNotBe(empty);
                    p.inputContactWireQuantity.shouldNotBe(empty);
                    p.inputPowerWireQuantity.shouldNotBe(empty);
                    p.inputTrackQuantity.shouldNotBe(empty);
                    element(p.dropdownMenuFeederMark.getFirstSelectedOption()).shouldNotBe(empty);
                    element(p.dropdownMenuContactWireMark.getFirstSelectedOption()).shouldNotBe(empty);
                    element(p.dropdownMenuPowerWireMark.getFirstSelectedOption()).shouldNotBe(empty);
                    element(p.dropdownMenuTrackMark.getFirstSelectedOption()).shouldNotBe(empty);
                });
    }


    @Test
    @QaseId(134)
    @DisplayName("Кнопка выбранной вкладки \"Питающая линия\" и \"Тяговая сеть\" заблокирована")
    public void activeSwitchButtonBlocked() {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .waitTime()
                .switchToSupplyLine()
                .check(p -> {
                    p.confirmationWindow.parent().shouldHave(cssClass("unvisible")); // ищем у родителя - так реализовано на фронте
                    p.buttonSupplyLine.parent().shouldHave(cssClass("active")); // ищем у родителя - так реализовано на фронте
                })
                .switchToTractionNetwork()
                .switchToTractionNetwork()
                .check(p -> {
                    p.confirmationWindow.parent().shouldHave(cssClass("unvisible")); // ищем у родителя - так реализовано на фронте
                    p.buttonTractionNetwork.parent().shouldHave(cssClass("active")); // ищем у родителя - так реализовано на фронте
                });
    }


    @Test
    @QaseId(135)
    @DisplayName("Открывается вкладка \"Питающая линия\" после сохранения тяговой сети")
    public void supplyLineIsActiveAfterSavingAndCreatingANewEntity() {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .waitTime()
                .switchToTractionNetwork()
                .inputTrackQuantity(1)
                .selectFirstFeeder()
                .selectFirstContactWire()
                .selectFirstTrack()
                .clickCalculate()
                .waitTime()
                .clickSave()
                .waitTime()
                .also(p -> namesToDelete.add(p.resolveNameByFields()));

        open(PageDirectNetworkList.class)
                .clickCreate()
                .check(p -> p.buttonSupplyLine.parent().shouldHave(cssClass("active")));
    }


    @Test
    @QaseId(136)
    @DisplayName("Открывается вкладка \"Питающая линия\" после выхода из создания тяговой сети")
    public void supplyLineIsActiveAfterExitingTractionNetworkCreation() {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .waitTime()
                .switchToTractionNetwork();

        open(PageDirectNetworkList.class)
                .clickCreate()
                .check(p -> p.buttonSupplyLine.parent().shouldHave(cssClass("active")));
    }


    @Test
    @QaseId(143)
    @DisplayName("Сброс результатов расчета при переключении вкладки на тяговую сеть")
    public void resetCalculationDataAfterSwitchingToTractionNetwork() {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .waitTime()
                .selectFirstFeeder()
                .clickCalculate()
                .waitTime()
                .switchToTractionNetwork()
                .check(p -> {
                    p.calculatedRks.shouldHave(value("-"));
                    p.calculatedRp.shouldHave(value("-"));
                    p.calculatedIdop.shouldHave(value("-"));
                    p.calculatedTdl.shouldHave(value("-"));
                    p.calculatedIkp.shouldHave(value("-"));
                });
    }


    @Test
    @QaseId(144)
    @DisplayName("Сброс результатов расчета при переключении вкладки на питающую линию")
    public void resetCalculationDataAfterSwitchingToSupplyLine() {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .waitTime()
                .switchToTractionNetwork()
                .inputFeederWireQuantity(1)
                .inputContactWireQuantity(1)
                .inputPowerWireQuantity(1)
                .inputTrackQuantity(1)
                .selectFirstFeeder()
                .selectFirstContactWire()
                .selectFirstPowerWire()
                .selectFirstTrack()
                .clickCalculate()
                .waitTime()
                .switchToSupplyLine()
                .clickConfirm()
                .check(p -> {
                    p.calculatedRks.shouldHave(value("-"));
                    p.calculatedRp.shouldHave(value("-"));
                    p.calculatedIdop.shouldHave(value("-"));
                    p.calculatedTdl.shouldHave(value("-"));
                    p.calculatedIkp.shouldHave(value("-"));
                });
    }


    @Test
    @QaseId(150)
    @DisplayName("Сброс значений контактного и усиливающего проводов при переключении вкладки на питающую линию")
    public void resetContactAndPowerWiresAfterSwitchingToSupplyLine() {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .waitTime()
                .switchToTractionNetwork()
                .inputContactWireQuantity(1)
                .inputPowerWireQuantity(1)
                .selectFirstContactWire()
                .selectFirstPowerWire()
                .switchToSupplyLine()
                .clickConfirm()
                .check(p -> {
                    p.inputContactWireQuantity.shouldBe(empty);
                    p.inputPowerWireQuantity.shouldBe(empty);
                    element(p.dropdownMenuContactWireMark.getFirstSelectedOption())
                            .shouldBe(empty)
                            .shouldBe(disabled);
                    element(p.dropdownMenuPowerWireMark.getFirstSelectedOption())
                            .shouldBe(empty)
                            .shouldBe(disabled);
                })
                .switchToTractionNetwork()
                .waitTime()
                .check(p -> {
                    p.inputContactWireQuantity.shouldHave(value("1"));
                    p.inputPowerWireQuantity.shouldHave(value("0"));
                    element(p.dropdownMenuContactWireMark.getWrappedElement()).shouldBe(enabled);
                    element(p.dropdownMenuPowerWireMark.getWrappedElement()).shouldBe(disabled);
                    element(p.dropdownMenuContactWireMark.getFirstSelectedOption()).shouldBe(empty);
                    element(p.dropdownMenuPowerWireMark.getFirstSelectedOption()).shouldBe(empty);
                });
    }


    @Test
    @QaseId(152)
    @DisplayName("Питающая линия открывается в режиме просмотра с кнопкой \"Создать новую на основе текущей\"")
    public void supplyLineOpensNonEditableWithCopyButton() {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .waitTime()
                .findFirstSupplyLineRow()
                .openNetwork()
                .check(p -> {
                    // вкладки заблокированы
                    p.buttonSupplyLine.parent().shouldHave(cssClass("active")); // ищем у родителя - так реализовано на фронте
                    p.buttonTractionNetwork.parent().shouldHave(cssClass("disabled")); // ищем у родителя - так реализовано на фронте
                    // поля ввода заблокированы
                    p.inputFeederQuantity.shouldBe(disabled);
                    p.inputContactWireQuantity.shouldBe(disabled);
                    p.inputPowerWireQuantity.shouldBe(disabled);
                    p.inputTrackQuantity.shouldBe(disabled);
                    // выпадающие списки заблокированы
                    element(p.dropdownMenuFeederMark.getWrappedElement()).shouldBe(disabled);
                    element(p.dropdownMenuContactWireMark.getWrappedElement()).shouldBe(disabled);
                    element(p.dropdownMenuPowerWireMark.getWrappedElement()).shouldBe(disabled);
                    element(p.dropdownMenuTrackMark.getWrappedElement()).shouldBe(disabled);
                    // кнопка расчета заблокирована
                    p.buttonCalculate.shouldBe(disabled);
                    // кнопка "Создать на основе текущей" активна
                    p.buttonCopy.shouldBe(enabled);
                });
    }


    @Test
    @QaseId(153)
    @DisplayName("Тяговая сеть открывается в режиме просмотра с кнопкой \"Создать новую на основе текущей\"")
    public void tractiveNetworkOpensNonEditableWithCopyButton() {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .waitTime()
                .findFirstTractiveNetworkRow()
                .openNetwork()
                .check(p -> {
                    // вкладки заблокированы
                    p.buttonSupplyLine.parent().shouldHave(cssClass("disabled")); // ищем у родителя - так реализовано на фронте
                    p.buttonTractionNetwork.parent().shouldHave(cssClass("active")); // ищем у родителя - так реализовано на фронте
                    // поля ввода заблокированы
                    p.inputFeederQuantity.shouldBe(disabled);
                    p.inputContactWireQuantity.shouldBe(disabled);
                    p.inputPowerWireQuantity.shouldBe(disabled);
                    p.inputTrackQuantity.shouldBe(disabled);
                    // выпадающие списки заблокированы
                    element(p.dropdownMenuFeederMark.getWrappedElement()).shouldBe(disabled);
                    element(p.dropdownMenuContactWireMark.getWrappedElement()).shouldBe(disabled);
                    element(p.dropdownMenuPowerWireMark.getWrappedElement()).shouldBe(disabled);
                    element(p.dropdownMenuTrackMark.getWrappedElement()).shouldBe(disabled);
                    // кнопка расчета заблокирована
                    p.buttonCalculate.shouldBe(disabled);
                    // кнопка "Создать на основе текущей" активна
                    p.buttonCopy.shouldBe(enabled);
                });
    }


    @Test
    @QaseId(154)
    @DisplayName("Выпадающие списки \"Марка\" разблокируются при вводе валидных значений в поле количество")
    public void markDropdownsUnlockingWhenQuantityEntered() {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .check(p -> element(p.dropdownMenuFeederMark.getWrappedElement()).shouldBe(enabled))
                .inputTrackQuantity(1)
                .pressTab()
                .check(p -> element(p.dropdownMenuTrackMark.getWrappedElement()).shouldBe(enabled))
                .switchToTractionNetwork()
                .check(p -> {
                    element(p.dropdownMenuFeederMark.getWrappedElement()).shouldBe(enabled);
                    element(p.dropdownMenuContactWireMark.getWrappedElement()).shouldBe(enabled);
                    element(p.dropdownMenuPowerWireMark.getWrappedElement()).shouldBe(disabled);
                    element(p.dropdownMenuTrackMark.getWrappedElement()).shouldBe(enabled);
                })
                .inputPowerWireQuantity(1)
                .pressTab()
                .check(p -> {
                    element(p.dropdownMenuPowerWireMark.getWrappedElement()).shouldBe(enabled);
                });
    }


    @ParameterizedTest()
    @QaseId(155)
    @DisplayName("Выпадающий список \"Марка\" питающего провода блокируется при вводе невалидных значений в поле \"Количество\"")
    @ValueSource(strings = {"", "  ", "0", "-1", "21", "19,99"})
    public void blockFeederDropdownOnInvalidQuantity(String invalidValue) {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .inputFeederWireQuantity(invalidValue)
                .pressTab()
                .check(p -> element(p.dropdownMenuFeederMark.getWrappedElement()).shouldBe(disabled))
                .inputFeederWireQuantity(invalidValue)
                .pressTab()
                .check(p -> element(p.dropdownMenuFeederMark.getWrappedElement()).shouldBe(disabled))
                .switchToTractionNetwork()
                .inputFeederWireQuantity(invalidValue)
                .pressTab()
                .check(p -> element(p.dropdownMenuFeederMark.getWrappedElement()).shouldBe(disabled))
                .inputFeederWireQuantity(invalidValue)
                .pressTab()
                .check(p -> element(p.dropdownMenuFeederMark.getWrappedElement()).shouldBe(disabled));
    }


    @ParameterizedTest()
    @QaseId(156)
    @DisplayName("Выпадающий список \"Марка\" путей блокируется при вводе невалидных значений в поле количество в питающей линии")
    @ValueSource(strings = {"-1", "21", "19,99"})
    public void blockSupplyLineTrackDropdownOnInvalidQuantity(String invalidValue) {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .inputTrackQuantity(invalidValue)
                .pressTab()
                .check(p -> element(p.dropdownMenuTrackMark.getWrappedElement()).shouldBe(disabled))
                .inputTrackQuantity(invalidValue)
                .pressTab()
                .check(p -> element(p.dropdownMenuTrackMark.getWrappedElement()).shouldBe(disabled));
    }


    @ParameterizedTest()
    @QaseId(237)
    @DisplayName("Выпадающий список \"Марка\" путей блокируется при вводе невалидных значений в поле количество тяговой сети")
    @ValueSource(strings = {"", "  ", "0", "-1", "21", "19,99"})
    public void blockTractionNetworkTrackDropdownOnInvalidQuantity(String invalidValue) {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .switchToTractionNetwork()
                .inputTrackQuantity(invalidValue)
                .pressTab()
                .check(p -> element(p.dropdownMenuTrackMark.getWrappedElement()).shouldBe(disabled))
                .inputTrackQuantity(invalidValue)
                .pressTab()
                .check(p -> element(p.dropdownMenuTrackMark.getWrappedElement()).shouldBe(disabled));
    }


    @ParameterizedTest()
    @QaseId(157)
    @DisplayName("Выпадающий список \"Марка\" путей блокируется при вводе невалидных значений в поле количество тяговой сети")
    @ValueSource(strings = {"", "  ", "0", "-1", "21", "19,99"})
    public void blockTractionNetworkPowerWireDropdownOnInvalidQuantity(String invalidValue) {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .switchToTractionNetwork()
                .inputPowerWireQuantity(invalidValue)
                .pressTab()
                .check(p -> element(p.dropdownMenuPowerWireMark.getWrappedElement()).shouldBe(disabled))
                .inputPowerWireQuantity(invalidValue)
                .pressTab()
                .check(p -> element(p.dropdownMenuPowerWireMark.getWrappedElement()).shouldBe(disabled));
    }


    @ParameterizedTest()
    @QaseId(158)
    @DisplayName("Выпадающий список \"Марка\" контактного провода блокируется при вводе невалидных значений в поле количество")
    @ValueSource(strings = {"", "  ", "0", "-1", "21", "19,99"})
    public void blockTractionNetworkContactWireDropdownOnInvalidQuantity(String invalidValue) {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .switchToTractionNetwork()
                .inputContactWireQuantity(invalidValue)
                .pressTab()
                .check(p -> element(p.dropdownMenuContactWireMark.getWrappedElement()).shouldBe(disabled))
                .inputContactWireQuantity(invalidValue)
                .pressTab()
                .check(p -> element(p.dropdownMenuContactWireMark.getWrappedElement()).shouldBe(disabled));
    }


    @Test
    @QaseId(160)
    @DisplayName("Cтраница создания переходит в режим просмотра после сохранения")
    public void readOnlyAfterSave() {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .selectFirstFeeder()
                .clickCalculate()
                .waitTime()
                .clickSave()
                .waitTime()
                .also(p -> namesToDelete.add(p.resolveNameByFields()))
                .check(p -> {
                    // вкладки заблокированы
                    p.buttonSupplyLine.parent().shouldHave(cssClass("active")); // ищем у родителя - так реализовано на фронте
                    p.buttonTractionNetwork.parent().shouldHave(cssClass("disabled")); // ищем у родителя - так реализовано на фронте
                    // поля ввода заблокированы
                    p.inputFeederQuantity.shouldBe(disabled);
                    p.inputContactWireQuantity.shouldBe(disabled);
                    p.inputPowerWireQuantity.shouldBe(disabled);
                    p.inputTrackQuantity.shouldBe(disabled);
                    // выпадающие списки заблокированы
                    element(p.dropdownMenuFeederMark.getWrappedElement()).shouldBe(disabled);
                    element(p.dropdownMenuContactWireMark.getWrappedElement()).shouldBe(disabled);
                    element(p.dropdownMenuPowerWireMark.getWrappedElement()).shouldBe(disabled);
                    element(p.dropdownMenuTrackMark.getWrappedElement()).shouldBe(disabled);
                    // кнопка расчета заблокирована
                    p.buttonCalculate.shouldBe(disabled);
                    // кнопка "Создать на основе текущей" активна
                    p.buttonCopy.shouldBe(enabled);
                });
    }


    @Test
    @QaseId(161)
    @DisplayName("Cтраница создания остается в режиме редактирования при попытке сохранения с невалидными данными")
    public void editableAfterUnSuccessFullSave() {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .clickCalculate()
                .clickCloseAlert()
                .clickSave()
                .clickCloseAlert()
                .check(p -> p.inputFeederQuantity.shouldBe(enabled));
    }


    @Test
    @QaseId(162)
    @DisplayName("Выпадающие списки и поля ввода заблокированы у контактного и усиливающего проводов в питающей линии")
    public void contactAndPowerWireFieldsDisabledInSupplyLine() {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .check(p -> {
                    p.inputContactWireQuantity.shouldBe(disabled);
                    p.inputPowerWireQuantity.shouldBe(disabled);
                    element(p.dropdownMenuContactWireMark.getWrappedElement()).shouldBe(disabled);
                    element(p.dropdownMenuPowerWireMark.getWrappedElement()).shouldBe(disabled);
                });
    }


    @Test
    @QaseId(163)
    @DisplayName("Всплывает модальное окно при удалении питающей линии")
    public void modalWindowAppearsOnDeleteSupplyLine() {
        AtomicReference<String> name = new AtomicReference<>();
        loginIfNeeded();
        var row = open(PageDirectNetworkList.class)
                .waitTime()
                .findFirstSupplyLineRow();
        name.set(row.cellName.text());
        row.cellName.contextClick();
        page(PageDirectNetworkList.class)
                .also(p -> p.deleteButtonContextMenu.click())
                .check(p -> p.textWindowConfirmModal.shouldHave(text("Удалить объект " + name.get() + "?")));
    }


    @Test
    @QaseId(164)
    @DisplayName("Всплывает модальное окно при удалении тяговой сети")
    public void modalWindowAppearsOnDeleteTractionNetwork() {
        AtomicReference<String> name = new AtomicReference<>();
        loginIfNeeded();
        var row = open(PageDirectNetworkList.class)
                .waitTime()
                .findFirstTractiveNetworkRow();
        name.set(row.cellName.text());
        row.cellName.contextClick();
        page(PageDirectNetworkList.class)
                .also(p -> p.deleteButtonContextMenu.click())
                .check(p -> p.textWindowConfirmModal.shouldHave(text("Удалить объект " + name.get() + "?")));
    }


    @Test
    @QaseId(165)
    @DisplayName("Кнопка \"Сохранить\" заменяется на \"Создать новую на основе текущей\" после сохранения питающей линии")
    public void switchSaveButtonToCopyButtonAfterSavingSupplyLine() {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .selectFirstFeeder()
                .clickCalculate()
                .waitTime()
                .clickSave()
                .waitTime()
                .also(p -> namesToDelete.add(p.resolveNameByFields()))
                .check(p -> {
                    // кнопка сохранения исчезла
                    p.buttonSave.shouldNot(exist);
                    // кнопка "Создать на основе текущей" существует и активна
                    p.buttonCopy.shouldBe(enabled);
                });
    }


    @Test
    @QaseId(166)
    @DisplayName("Кнопка \"Сохранить\" заменяется на \"Создать новую на основе текущей\" после сохранения тяговой сети")
    public void switchSaveButtonToCopyButtonAfterSavingTractionNetwork() {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .switchToTractionNetwork()
                .inputFeederWireQuantity(1)
                .inputContactWireQuantity(1)
                .inputPowerWireQuantity(1)
                .inputTrackQuantity(1)
                .selectFirstFeeder()
                .selectFirstContactWire()
                .selectFirstPowerWire()
                .selectFirstTrack()
                .clickCalculate()
                .waitTime()
                .clickSave()
                .waitTime()
                .also(p -> namesToDelete.add(p.resolveNameByFields()))
                .check(p -> {
                    // кнопка сохранения исчезла
                    p.buttonSave.shouldNot(exist);
                    // кнопка "Создать на основе текущей" существует и активна
                    p.buttonCopy.shouldBe(enabled);
                });
    }


    @Test
    @QaseId(167)
    @DisplayName("Кнопка \"Сохранить\" заблокирована до проведения расчета питающей линии")
    public void saveButtonDisabledBeforeCalculationSupplyLine() {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .selectFirstFeeder()
                .check(p -> {
                    // кнопка сохранения заблокирована
                    p.buttonSave.shouldBe(disabled);
                });
    }


    @Test
    @QaseId(168)
    @DisplayName("Кнопка \"Сохранить\" заблокирована до проведения расчета тяговой сети")
    public void saveButtonDisabledBeforeCalculationTractionNetwork() {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .switchToTractionNetwork()
                .inputFeederWireQuantity(1)
                .inputContactWireQuantity(1)
                .inputPowerWireQuantity(1)
                .inputTrackQuantity(1)
                .selectFirstFeeder()
                .selectFirstContactWire()
                .selectFirstPowerWire()
                .selectFirstTrack()
                .check(p -> {
                    // кнопка сохранения заблокирована
                    p.buttonSave.shouldBe(disabled);
                });
    }


    @ParameterizedTest
    @QaseId(204)
    @DisplayName("Значения не изменяются при снятии фокуса с поля ввода \"Количество\" питающего провода")
    @ValueSource(strings = {"0,0000000001", "0,1", "0,56", "99,999", "9"})
    public void feederQuantityInputDoesNotChange(String value) {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .inputFeederWireQuantity(value)
                .pressTab()
                .check(p -> p.inputFeederQuantity.shouldHave(value(value)))
                .inputFeederWireQuantity(value)
                .pressTab()
                .check(p -> p.inputFeederQuantity.shouldHave(value(value)));
    }


    @ParameterizedTest
    @QaseId(205)
    @DisplayName("Значения не изменяются при снятии фокуса с поля ввода \"Количество\" у пути питающей линии")
    @ValueSource(strings = {"0,0000000001", "0,1", "0,56", "99,999", "9"})
    public void trackQuantityInputDoesNotChange(String value) {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .inputTrackQuantity(value)
                .pressTab()
                .check(p -> p.inputTrackQuantity.shouldHave(value(value)))
                .inputTrackQuantity(value)
                .pressTab()
                .check(p -> p.inputTrackQuantity.shouldHave(value(value)));
    }


    @ParameterizedTest
    @QaseId(206)
    @DisplayName("Значения не изменяются при снятии фокуса с поля ввода \"Количество\" несущего провода тяговой сети")
    @ValueSource(strings = {"0,0000000001", "0,1", "0,56", "99,999", "9"})
    public void supportWireQuantityInputDoesNotChange(String value) {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .switchToTractionNetwork()
                .inputFeederWireQuantity(value)
                .pressTab()
                .check(p -> p.inputFeederQuantity.shouldHave(value(value)))
                .inputFeederWireQuantity(value)
                .pressTab()
                .check(p -> p.inputFeederQuantity.shouldHave(value(value)));
    }


    @ParameterizedTest
    @QaseId(207)
    @DisplayName("Значения не изменяются при снятии фокуса с поля ввода \"Количество\" контактного провода тяговой сети")
    @ValueSource(strings = {"0,0000000001", "0,1", "0,56", "99,999", "9"})
    public void contactWireQuantityInputDoesNotChange(String value) {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .switchToTractionNetwork()
                .inputContactWireQuantity(value)
                .pressTab()
                .check(p -> p.inputContactWireQuantity.shouldHave(value(value)))
                .inputContactWireQuantity(value)
                .pressTab()
                .check(p -> p.inputContactWireQuantity.shouldHave(value(value)));
    }


    @ParameterizedTest
    @QaseId(208)
    @DisplayName("Значения не изменяются при снятии фокуса с поля ввода \"Количество\" усиливающего провода тяговой сети")
    @ValueSource(strings = {"0,0000000001", "0,1", "0,56", "99,999", "9"})
    public void powerWireQuantityInputDoesNotChange(String value) {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .switchToTractionNetwork()
                .inputPowerWireQuantity(value)
                .pressTab()
                .check(p -> p.inputPowerWireQuantity.shouldHave(value(value)))
                .inputPowerWireQuantity(value)
                .pressTab()
                .check(p -> p.inputPowerWireQuantity.shouldHave(value(value)));
    }


    @ParameterizedTest
    @QaseId(209)
    @DisplayName("Значения не изменяются при снятии фокуса с поля ввода \"Количество\" у пути тяговой сети")
    @ValueSource(strings = {"0,0000000001", "0,1", "0,56", "99,999", "9"})
    public void trackQuantityTractionNetworkInputDoesNotChange(String value) {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .switchToTractionNetwork()
                .inputTrackQuantity(value)
                .pressTab()
                .check(p -> p.inputTrackQuantity.shouldHave(value(value)))
                .inputTrackQuantity(value)
                .pressTab()
                .check(p -> p.inputTrackQuantity.shouldHave(value(value)));
    }


    @Test
    @QaseId(233)
    @DisplayName("Переключение фокуса с поля ввода на другое поле ввода по 1 нажатию")
    public void focusInputInOneClick() {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .clickOn(p -> p.inputFeederQuantity)
                .check(p -> p.inputFeederQuantity.shouldBe(focused))
                .inputFeederWireQuantity(10)
                .clickOn(p -> p.inputTrackQuantity)
                .check(p -> p.inputTrackQuantity.shouldBe(focused))
                .inputTrackQuantity(10)
                .clickOn(p -> p.inputFeederQuantity)
                .check(p -> p.inputFeederQuantity.shouldBe(focused))
                .switchToTractionNetwork()
                .inputFeederWireQuantity(1)
                .clickOn(p -> p.inputContactWireQuantity)
                .check(p -> p.inputContactWireQuantity.shouldBe(focused))
                .inputContactWireQuantity(10)
                .clickOn(p -> p.inputPowerWireQuantity)
                .check(p -> p.inputPowerWireQuantity.shouldBe(focused))
                .inputPowerWireQuantity(10)
                .clickOn(p -> p.inputTrackQuantity)
                .check(p -> p.inputTrackQuantity.shouldBe(focused))
                .inputTrackQuantity(1)
                .clickOn(p -> p.inputFeederQuantity)
                .check(p -> p.inputFeederQuantity.shouldBe(focused));
    }


    @Test
    @QaseId(234)
    @DisplayName("Питающая линия созданная на основании текущей открывается в режиме редактирования")
    public void copiedSupplyLineOpensInEditMode() {
        AtomicReference<String> feederQty = new AtomicReference<>();
        AtomicReference<String> feederMark = new AtomicReference<>();
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .selectFirstFeeder()
                .clickCalculate()
                .waitTime()
                .clickSave()
                .waitTime()
                .also(p -> {
                    namesToDelete.add(p.resolveNameByFields());
                    feederQty.set(p.inputFeederQuantity.getValue());
                    feederMark.set(p.dropdownMenuFeederMark.getFirstSelectedOption().getText());
                })
                .clickCopy()
                .check(p -> {
                    p.inputFeederQuantity.shouldBe(enabled);
                    element(p.dropdownMenuFeederMark.getWrappedElement()).shouldBe(enabled);
                    p.inputFeederQuantity.shouldHave(value(feederQty.get()));
                    element(p.dropdownMenuFeederMark.getFirstSelectedOption()).shouldHave(text(feederMark.get()));
                });
    }


    @Test
    @QaseId(235)
    @DisplayName("Тяговая сеть созданная на основании текущей открывается в режиме редактирования")
    public void tractionNetworkLineOpensInEditMode() {
        AtomicReference<String> feederMark = new AtomicReference<>();
        AtomicReference<String> contactMark = new AtomicReference<>();
        AtomicReference<String> powerMark = new AtomicReference<>();
        AtomicReference<String> trackMark = new AtomicReference<>();
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .switchToTractionNetwork()
                .inputFeederWireQuantity(1)
                .inputContactWireQuantity(1)
                .inputPowerWireQuantity(1)
                .inputTrackQuantity(1)
                .selectFirstFeeder()
                .selectFirstContactWire()
                .selectFirstPowerWire()
                .selectFirstTrack()
                .clickCalculate()
                .waitTime()
                .clickSave()
                .waitTime()
                .also(p -> {
                    namesToDelete.add(p.resolveNameByFields());
                    feederMark.set(p.dropdownMenuFeederMark.getFirstSelectedOption().getText());
                    contactMark.set(p.dropdownMenuContactWireMark.getFirstSelectedOption().getText());
                    powerMark.set(p.dropdownMenuPowerWireMark.getFirstSelectedOption().getText());
                    trackMark.set(p.dropdownMenuTrackMark.getFirstSelectedOption().getText());
                })
                .clickCopy()
                .check(p -> {
                    p.inputFeederQuantity.shouldBe(enabled);
                    p.inputContactWireQuantity.shouldBe(enabled);
                    p.inputPowerWireQuantity.shouldBe(enabled);
                    p.inputTrackQuantity.shouldBe(enabled);
                    element(p.dropdownMenuFeederMark.getWrappedElement()).shouldBe(enabled);
                    element(p.dropdownMenuContactWireMark.getWrappedElement()).shouldBe(enabled);
                    element(p.dropdownMenuPowerWireMark.getWrappedElement()).shouldBe(enabled);
                    element(p.dropdownMenuTrackMark.getWrappedElement()).shouldBe(enabled);
                    p.inputFeederQuantity.shouldHave(value("1"));
                    p.inputContactWireQuantity.shouldHave(value("1"));
                    p.inputPowerWireQuantity.shouldHave(value("1"));
                    p.inputTrackQuantity.shouldHave(value("1"));
                    element(p.dropdownMenuFeederMark.getFirstSelectedOption()).shouldHave(text(feederMark.get()));
                    element(p.dropdownMenuContactWireMark.getFirstSelectedOption()).shouldHave(text(contactMark.get()));
                    element(p.dropdownMenuPowerWireMark.getFirstSelectedOption()).shouldHave(text(powerMark.get()));
                    element(p.dropdownMenuTrackMark.getFirstSelectedOption()).shouldHave(text(trackMark.get()));
                });
    }


    @Test
    @QaseId(236)
    @DisplayName("Значения питающего провода и пути сохраняются при переключении вкладок \"Питающая линия\" и \"Тяговая сеть\"")
    public void keepFeederAndTrackValuesWhenSwitchingToTractionNetwork() {
        AtomicReference<String> feederMark = new AtomicReference<>();
        AtomicReference<String> trackMark = new AtomicReference<>();
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .inputFeederWireQuantity(1)
                .inputTrackQuantity(1)
                .selectFirstFeeder()
                .selectFirstTrack()
                .also(p -> {
                    feederMark.set(p.dropdownMenuFeederMark.getFirstSelectedOption().getText());
                    trackMark.set(p.dropdownMenuTrackMark.getFirstSelectedOption().getText());
                })
                .switchToTractionNetwork()
                .check(p -> {
                    p.inputFeederQuantity.shouldHave(value("1"));
                    p.inputTrackQuantity.shouldHave(value("1"));
                    element(p.dropdownMenuFeederMark.getFirstSelectedOption()).shouldHave(text(feederMark.get()));
                    element(p.dropdownMenuTrackMark.getFirstSelectedOption()).shouldHave(text(trackMark.get()));
                })
                .switchToSupplyLine()
                .check(p -> {
                    p.inputFeederQuantity.shouldHave(value("1"));
                    p.inputTrackQuantity.shouldHave(value("1"));
                    element(p.dropdownMenuFeederMark.getFirstSelectedOption()).shouldHave(text(feederMark.get()));
                    element(p.dropdownMenuTrackMark.getFirstSelectedOption()).shouldHave(text(trackMark.get()));
                });
    }


    @Test
    @QaseId(245)
    @DisplayName("Кнопка \"Рассчитать\" заблокирована после сохранения питающей линии")
    public void calculateButtonDisabledAfterSavingSupplyLine() {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .selectFirstFeeder()
                .clickCalculate()
                .waitTime()
                .clickSave()
                .waitTime()
                .also(p -> namesToDelete.add(p.resolveNameByFields()))
                .check(p -> {
                    // кнопка рассчитать заблокирована
                    p.buttonCalculate.shouldBe(disabled);
                });
    }


    @Test
    @QaseId(246)
    @DisplayName("Кнопка \"Рассчитать\" заблокирована после сохранения тяговой сети")
    public void calculateButtonDisabledAfterSavingTractionNetwork() {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .switchToTractionNetwork()
                .inputFeederWireQuantity(1)
                .inputContactWireQuantity(1)
                .inputPowerWireQuantity(1)
                .inputTrackQuantity(1)
                .selectFirstFeeder()
                .selectFirstContactWire()
                .selectFirstPowerWire()
                .selectFirstTrack()
                .clickCalculate()
                .waitTime()
                .clickSave()
                .waitTime()
                .also(p -> namesToDelete.add(p.resolveNameByFields()))
                .check(p -> {
                    // кнопка сохранения заблокирована
                    p.buttonCalculate.shouldBe(disabled);
                });
    }
}
