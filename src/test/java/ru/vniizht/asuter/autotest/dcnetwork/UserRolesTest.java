package ru.vniizht.asuter.autotest.dcnetwork;

import io.qase.api.annotation.QaseId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.vniizht.asuter.autotest.BaseTest;
import ru.vniizht.asuter.autotest.constants.User;
import ru.vniizht.asuter.autotest.pages.equipment.electrical.PageDirectNetworkList;

import java.util.concurrent.atomic.AtomicReference;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.value;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тяговые сети постоянного тока: роли пользователей")
public class UserRolesTest extends BaseTest {

    @Test
    @QaseId(137)
    @DisplayName("Невозможность расчета питающей линии с правом просмотра данных")
    public void canNotCalculateSupplyLineWithReadOnlyRole() {
        loginIfNeeded(User.READ);
        open(PageDirectNetworkList.class)
                .clickCreate()
                .selectFirstFeeder()
                .clickCalculate()
                .waitTime()
                .check(p -> {
                    p.alertMessage.shouldHave(exactText("Запрос отклонён: действие недоступно для данной учетной записи. Для расширения прав обратитесь к администратору."));
                    p.calculatedRks.shouldHave(value("-"));
                    p.calculatedRp.shouldHave(value("-"));
                    p.calculatedIdop.shouldHave(value("-"));
                    p.calculatedTdl.shouldHave(value("-"));
                    p.calculatedIkp.shouldHave(value("-"));
                });
    }

    @Test
    @QaseId(141)
    @DisplayName("Невозможность расчета тяговой сети с правом просмотра данных")
    public void canNotCalculateTractiveNetworkWithReadOnlyRole() {
        loginIfNeeded(User.READ);
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
                .check(p -> {
                    p.alertMessage.shouldHave(exactText("Запрос отклонён: действие недоступно для данной учетной записи. Для расширения прав обратитесь к администратору."));
                    p.calculatedRks.shouldHave(value("-"));
                    p.calculatedRp.shouldHave(value("-"));
                    p.calculatedIdop.shouldHave(value("-"));
                    p.calculatedTdl.shouldHave(value("-"));
                    p.calculatedIkp.shouldHave(value("-"));
                });
    }

    @Test
    @QaseId(195)
    @DisplayName("Невозможность удаления питающей линии пользователем с правом просмотра данных")
    public void canNotDeleteSupplyLineWithReadOnlyRole() {
        AtomicReference<String> name = new AtomicReference<>();
        loginIfNeeded(User.READ);
        open(PageDirectNetworkList.class)
                .waitTime()
                .also(p -> {
                    var row = p.findFirstSupplyLineRow();
                    name.set(row.cellName.text());
                    row.delete();
                })
                .waitTime()
                .check(p -> {
                    p.alertMessage.shouldHave(exactText("Запрос отклонён: действие недоступно для данной учетной записи. Для расширения прав обратитесь к администратору."));
                    assertEquals(name.get(), p.findFirstSupplyLineRow().cellName.text());
                });
    }

    @Test
    @QaseId(196)
    @DisplayName("Невозможность удаления тяговой сети пользователем с правом просмотра данных")
    public void canNotDeleteTractionNetworkWithReadOnlyRole() {
        AtomicReference<String> name = new AtomicReference<>();
        loginIfNeeded(User.READ);
        open(PageDirectNetworkList.class)
                .waitTime()
                .also(p -> {
                    var row = p.findFirstTractiveNetworkRow();
                    name.set(row.cellName.text());
                    row.delete();
                })
                .waitTime()
                .check(p -> {
                    p.alertMessage.shouldHave(exactText("Запрос отклонён: действие недоступно для данной учетной записи. Для расширения прав обратитесь к администратору."));
                    assertEquals(name.get(), p.findFirstTractiveNetworkRow().cellName.text());
                });
    }

}
