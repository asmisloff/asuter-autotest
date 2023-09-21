package ru.vniizht.asuter.autotest.cars;

import org.junit.jupiter.api.BeforeAll;

public class MainParametersTest extends CarTest {
    @BeforeAll
    public static void init() {
        createNewCar();
        findMainParametersGuiElements();
    }
}
