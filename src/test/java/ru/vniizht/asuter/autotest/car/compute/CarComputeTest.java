package ru.vniizht.asuter.autotest.car.compute;

import io.qase.api.annotation.QaseId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.vniizht.asuter.autotest.car.CarBaseTest;
import ru.vniizht.asuter.autotest.constants.User;
import ru.vniizht.asuter.autotest.pages.transport.cars.PageCarsList;

import java.util.stream.Stream;

import static ru.vniizht.asuter.autotest.CustomConditions.validInput;

@DisplayName("Вагоны: Расчеты")
public class CarComputeTest extends CarBaseTest {

    private static Stream<Arguments> users() {
        return Stream.of(
                Arguments.of(User.NSI),
                Arguments.of(User.CALC)
        );
    }
    @QaseId(337)
    @ParameterizedTest
    @MethodSource("users")
    @DisplayName("Правильность расчета Массы брутто и Массы на ось при q>6")
    public void testFullMassAndMassPerAxleWhenQGreaterThan6(User user) {
        loginIfNeeded(user);
        open(PageCarsList.class)
                .clickCreate()
                .also(p -> {
                    p.inputContainerMass("8,003");
                    p.inputNetMass("15,999");
                    p.pressTab();
                    p.fullMassInput.shouldHave(validInput("24,002"));
                    p.massPerAxleInput.shouldHave(validInput("6,001"));
                });
        logout();
    }

    @QaseId(338)
    @ParameterizedTest
    @MethodSource("users")
    @DisplayName("Правильность расчета Массы брутто и Массы на ось при q=6")
    public void testFullMassAndMassPerAxleWhenQEquals6(User user) {
        loginIfNeeded(user);
        open(PageCarsList.class)
                .clickCreate()
                .also(p -> {
                    p.inputContainerMass("8");
                    p.inputNetMass("15,999");
                    p.pressTab();
                    p.fullMassInput.shouldHave(validInput("23,999"));
                    p.massPerAxleInput.shouldHave(validInput("6"));
                });
        logout();
    }

    @QaseId(339)
    @ParameterizedTest
    @MethodSource("users")
    @DisplayName("Правильность расчета Массы брутто и Массы на ось при q<6")
    public void testFullMassAndMassPerAxleWhenQLessThan6(User user) {
        loginIfNeeded(user);
        open(PageCarsList.class)
                .clickCreate()
                .also(p -> {
                    p.inputContainerMass("8");
                    p.inputNetMass("15,998");
                    p.pressTab();
                    p.fullMassInput.shouldHave(validInput("23,998"));
                    p.massPerAxleInput.shouldHave(validInput("5,999"));
                });
        logout();
    }
}
