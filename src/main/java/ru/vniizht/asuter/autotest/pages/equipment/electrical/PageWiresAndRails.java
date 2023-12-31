package ru.vniizht.asuter.autotest.pages.equipment.electrical;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.vniizht.asuter.autotest.annotation.Url;
import ru.vniizht.asuter.autotest.pages.BasePage;

@Url("wires-and-rails")
public class PageWiresAndRails extends BasePage {

    @FindBy(xpath = "//button[@data-testid='editBtn']")
    public WebElement buttonEdit;

    @FindBy(xpath = "//button[@data-testid='saveBtn']")
    public WebElement buttonSave;


    public PageWiresAndRails clickEdit() {
        buttonEdit.click();
        return this;
    }

    public PageWiresAndRails clickSave() {
        buttonSave.click();
        return this;
    }

    public EditableRowOfPageWiresAndRails getEditableRow(int index) {
        if (index <= 0) index = 1;
        return new EditableRowOfPageWiresAndRails(this, index);
    }

    public EditableRowOfPageWiresAndRails getLastEditableRow() {
        return new EditableRowOfPageWiresAndRails(this);
    }

    public EditableRowOfPageWiresAndRails findEditableRowByWireName(String name) {
        SelenideElement input = EditableRowOfPageWiresAndRails.findInputNameByValue(name);
        SelenideElement parentTableRow = input.parent().parent().parent();
        ElementsCollection precedingSiblings = parentTableRow.$$(By.xpath("./preceding-sibling::tr"));
        int rowIndex = precedingSiblings.size() + 1;
        return new EditableRowOfPageWiresAndRails(this, rowIndex);
    }

}
