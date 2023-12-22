package ru.vniizht.asuter.autotest.car;

import lombok.Builder;
import ru.vniizht.asuter.autotest.pages.transport.cars.PageCar;

import static ru.vniizht.asuter.autotest.CustomConditions.validInput;

/**
 * Тестовые данные вагона.
 * @param name наименование
 * @param type тип
 * @param numberOfAxles число осей
 * @param containerMass масса тары
 * @param netMass масса нетто (грузоподъемность)
 * @param length длина по осям сцепления
 */

@Builder
public record Car(
        String name,
        String type,
        String numberOfAxles,
        String containerMass,
        String netMass,
        String length,
        String[] coefficients
) {
    public Car inputTo(PageCar page) {
        if (name != null) {
            page.inputName(name);
        }
        if (type != null) {
            page.selectType(type);
        }
        if (numberOfAxles != null) {
            page.selectNumberOfAxles(numberOfAxles);
        }
        if (containerMass != null) {
            page.inputContainerMass(containerMass);
        }
        if (netMass != null) {
            page.inputNetMass(netMass);
        }
        if (length != null) {
            page.inputLength(length);
        }
        if (coefficients != null && coefficients.length == 8) {
            page.inputAllCoefficients(coefficients);
        }
        page.pressTab();
        return this;
    }

    /** Проверка полей ввода переданной страницы */
    public void checkCoefficientsEqual(PageCar p) {
        p.componentRailAInput.shouldHave(validInput(coefficients[0]));
        p.componentRailBInput.shouldHave(validInput(coefficients[1]));
        p.componentRailCInput.shouldHave(validInput(coefficients[2]));
        p.componentRailDInput.shouldHave(validInput(coefficients[3]));
        p.continuousRailAInput.shouldHave(validInput(coefficients[4]));
        p.continuousRailBInput.shouldHave(validInput(coefficients[5]));
        p.continuousRailCInput.shouldHave(validInput(coefficients[6]));
        p.continuousRailDInput.shouldHave(validInput(coefficients[7]));
    }

    public Car setComponentRailA(String value) {
        coefficients[0] = value;
        return this;
    }

    public Car setContinuousRailA(String value) {
        coefficients[4] = value;
        return this;
    }
}
