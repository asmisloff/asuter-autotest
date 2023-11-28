package ru.vniizht.asuter.autotest.pages.equipment.electrical;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import ru.vniizht.asuter.autotest.annotation.Url;
import ru.vniizht.asuter.autotest.pages.BasePage;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.page;

@Url("direct-network")
public class PageDirectNetworkList extends BasePage {

    @FindBy(xpath = "//button[@title='Создать']")
    SelenideElement buttonCreate;

    // TODO remove test field
    @FindBy(xpath = "/html/body/div/div[2]/div[1]/div/div[2]/div[2]/div/table/tbody/tr[1]/td[3]")
    SelenideElement test;
    @FindBy(xpath = "/html/body/div/div[2]/div[1]/div/div[2]/div[2]/div/table/tbody/tr[1]/td[9]")
    SelenideElement test2;

    @FindBy(xpath = "/html/body/div/div[2]/div[1]/div/div[2]/div[2]/div/table/tbody/tr")
    SelenideElement test3;

    public PageDirectNetwork clickCreate() {
        buttonCreate.click();
        return page(PageDirectNetwork.class);
    }

    /** Строка в списке тяговых сетей по индексу (начиная с 0) */
    public DirectNetworkListRow getRowByIndex(int index) {
        return getRowByVisibleIndex(index + 1);
    }

    /** Строка в списке тяговых сетей по указанному на странице индексу (начиная с 1) */
    public DirectNetworkListRow getRowByVisibleIndex(int index) {
        return new DirectNetworkListRow(this, index);
    }

    public DirectNetworkListRow findRowByNetworkName(String name) {

        System.out.println(test3);

        return null; // TODO valid return
    }

    public static class DirectNetworkListRow {

        private final PageDirectNetworkList page;

        final SelenideElement cellCount;
        final SelenideElement cellName;
        final SelenideElement cellRks;
        final SelenideElement cellRp;
        final SelenideElement cellIdop;
        final SelenideElement cellTdl;
        final SelenideElement cellIkp;
        final SelenideElement cellType;

        // TODO находить элементы по data-testid, когда айдишник добавится на фронте
        public DirectNetworkListRow(PageDirectNetworkList page, int tableLineIndex) {
            this.page = page;
            var lineXpath = "/html/body/div/div[2]/div[1]/div/div[2]/div[2]/div/table/tbody/tr[" + tableLineIndex + "]";
            cellCount = $x(lineXpath + "/td[2]");
            cellName = $x(lineXpath + "/td[3]");
            cellRks = $x(lineXpath + "/td[4]");
            cellRp = $x(lineXpath + "/td[5]");
            cellIdop = $x(lineXpath + "/td[6]");
            cellTdl = $x(lineXpath + "/td[7]");
            cellIkp = $x(lineXpath + "/td[8]");
            cellType = $x(lineXpath + "/td[9]");
        }

        public PageDirectNetworkList page() {
            return page;
        }

        public PageDirectNetwork openNetwork() {
            cellName.doubleClick();
            return Selenide.page(PageDirectNetwork.class);
        }

    }
}
