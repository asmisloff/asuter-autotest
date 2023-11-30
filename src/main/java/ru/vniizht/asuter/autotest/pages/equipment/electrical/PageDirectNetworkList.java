package ru.vniizht.asuter.autotest.pages.equipment.electrical;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import ru.vniizht.asuter.autotest.annotation.Url;
import ru.vniizht.asuter.autotest.pages.BasePage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.page;

@Url("direct-network")
public class PageDirectNetworkList extends BasePage<PageDirectNetworkList> {

    @FindBy(xpath = "//button[@title='Создать']")
    SelenideElement buttonCreate;

    @FindBy(xpath = "//li[@role='menuitem']")
    public SelenideElement deleteButtonContextMenu;

    @FindBy(xpath = "//li[@data-testid='OKBtn']")
    public SelenideElement okModalButton;

    @FindBy(xpath = "//li[@data-testid='CancelBtn']")
    public SelenideElement cancelModalButton;

    @FindBy(xpath = "/html/body/div/div[2]/div[1]/div/div[2]/div[2]/div/table/tbody/tr")
    ElementsCollection allRows;

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
        for (int i = 0; i < allRows.size(); i++) {
            var row = new DirectNetworkListRow(this, i+1);
            if (Objects.equals(row.cellName.text(), name)) return row;
        }
        return null;
    }

    public List<DirectNetworkListRow> findRowsByNetworkName(String name) {
        var results = new ArrayList<DirectNetworkListRow>();
        for (int i = 0; i < allRows.size(); i++) {
            var row = new DirectNetworkListRow(this, i+1);
            if (Objects.equals(row.cellName.text(), name)) results.add(row);
        }
        return results;
    }

    public class DirectNetworkListRow {

        private final PageDirectNetworkList page;

        public final SelenideElement cellCount;
        public final SelenideElement cellName;
        public final SelenideElement cellRks;
        public final SelenideElement cellRp;
        public final SelenideElement cellIdop;
        public final SelenideElement cellTdl;
        public final SelenideElement cellIkp;
        public final SelenideElement cellType;

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
        
        public PageDirectNetworkList delete() {
            cellName.contextClick();
            deleteButtonContextMenu.click();
            okModalButton.click();
            return page;
        }

        public PageDirectNetwork openNetwork() {
            cellName.doubleClick();
            return Selenide.page(PageDirectNetwork.class);
        }

    }
}
