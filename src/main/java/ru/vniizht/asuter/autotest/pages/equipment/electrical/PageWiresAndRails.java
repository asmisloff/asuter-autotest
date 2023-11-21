package ru.vniizht.asuter.autotest.pages.equipment.electrical;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.vniizht.asuter.autotest.annotation.Url;
import ru.vniizht.asuter.autotest.pages.BasePage;

@Url("http://asuter-dev.vniizht.lan/wires-and-rails")
public class PageWiresAndRails extends BasePage {
    
    @FindBy(xpath = "//button[@title='Редактировать']")
    WebElement buttonEdit;


    
    
    
}
