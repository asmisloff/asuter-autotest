package ru.vniizht.asuter.autotest.pages.transport.cars;

import com.codeborne.selenide.*;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import ru.vniizht.asuter.autotest.annotation.Url;
import ru.vniizht.asuter.autotest.pages.BasePage;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

@Url("cars")
public class PageCarsList extends BasePage<PageCarsList> {

    private static final String TBODY_XPATH = "//div[@id=\"rootContainer\"]/div/table/tbody";
    public static final String FIRST_ROW_PATH = TBODY_XPATH + "/tr[1]";
    public static final String FIRST_TD_XPATH = FIRST_ROW_PATH + "/td[1]";

    @FindBy(xpath = "//button[@title='Создать']")
    @As("Кнопка \"Создать\"")
    SelenideElement buttonCreate;

    @FindBy(xpath = "//ul[@role='menu']/li[1][@tabindex='0']")
    @As("Кнопка \"Удалить\" в контекстном меню")
    public SelenideElement deleteButtonInContextMenu;

    @FindBy(xpath = "//ul[@role='menu']/li[2][@tabindex='-1']")
    @As("Кнопка \"Копировать\" в контекстном меню")
    public SelenideElement copyButtonInContextMenu;

    @FindBy(xpath = "//li[@data-testid='OKBtn'][normalize-space(text())='Удалить']")
    @As("Кнопка подтверждения в модальном окне")
    public SelenideElement okModalDeleteButton;

    @FindBy(xpath = "//li[@data-testid='CancelBtn']")
    @As("Кнопка отмены в модальном окне")
    public SelenideElement cancelModalButton;

    @FindBy(xpath = "//div[@data-testid='ConfirmModal']/p")
    @As("Текст модального окна подтверждения")
    public SelenideElement confirmModalText;

    @FindBy(xpath = "//div[@id=\"modalContainer\"]/div[1]/div/div[2]/div[1]/ul[1]/ul[1]/li[1]/a[normalize-space(text())='20']")
    @As("Кнопка отображения 20 вагонов в списке")
    public SelenideElement display20CarsOnPageLink;

    @FindBy(xpath = "//div[@id=\"modalContainer\"]/div[1]/div/div[2]/div[1]/ul[1]/ul[1]/li[2]/a[normalize-space(text())='Все']")
    @As("Кнопка отображения всех вагонов в списке")
    public SelenideElement displayAllCarsOnPageLink;

    @FindBy(xpath = "//div[@id=\"modalContainer\"]/div[1]/div/div[2]/div[1]/ul[2]/li[4]/a")
    @As("Кнопка перехода на следующую страницу списка вагонов")
    public SelenideElement nextPageLink;

    @FindBy(xpath = "//div[@id=\"modalContainer\"]/div[1]/div/div[2]/div[1]/ul[2]/li[5]/a")
    @As("Кнопка перехода на следующую страницу списка вагонов")
    public SelenideElement lastPageLink;

    @FindBy(xpath = "//div[@id=\"rootContainer\"]/div/table/tbody/tr")
    @As("Все строки таблицы тяговых вагонов")
    public ElementsCollection allRows;

    @FindBy(xpath = "//div[@id=\"rootContainer\"]/div/table/thead/tr/th[2]")
    @As("Заголовок столбца \"Наименование\"")
    public SelenideElement nameColumn;

    @FindBy(xpath = "//div[@id=\"rootContainer\"]/div/table/thead/tr/th[3]")
    @As("Заголовок столбца \"Число осей\"")
    public SelenideElement numberOfAxlesColumn;

    @FindBy(xpath = "//div[@id=\"rootContainer\"]/div/table/thead/tr/th[4]")
    @As("Заголовок столбца \"Учетная масса брутто\"")
    public SelenideElement fullMassColumn;

    @FindBy(xpath = "//div[@id=\"rootContainer\"]/div/table/thead/tr/th[5]")
    @As("Заголовок столбца \"Длина, м\"")
    public SelenideElement lengthColumn;

    @FindBy(xpath = "//div[@id=\"rootContainer\"]/div/table/thead/tr/th[6]")
    @As("Заголовок столбца \"Тип вагона\"")
    public SelenideElement carTypeColumn;

    @FindBy(xpath = "//div[@id=\"rootContainer\"]/div/table/thead/tr/th[7]")
    @As("Заголовок столбца \"Время редактирования\"")
    public SelenideElement changeTimeColumn;

    @FindBy(xpath = "//div[@id=\"modalContainer\"]/div[1]/div/div[2]/div[1]/ul[1]/ul[2]/li")
    @As("Кнопка поиска") // Активный элемент li
    public SelenideElement searchButton;

    @FindBy(xpath = "//div[@id=\"modalContainer\"]/div[1]/div/div[2]/div[1]/ul[1]/ul[2]/div/input")
    @As("Поле поиска")
    public SelenideElement searchInput;

    private final Map<String, Supplier<PageCarsList>> columns = Map.of(
            "Наименование", this::clickNameColumn,
            "Число осей", this::clickNumberOfAxlesColumn,
            "Учетная масса брутто", this::clickFullMassColumn,
            "Длина, м", this::clickLengthColumn,
            "Тип вагона", this::clickCarTypeColumn,
            "Время редактирования", this::clickChangeTimeColumn
    );

    private final Map<String, Function<CarListRow, String>> rowTextGetters = Map.of(
            "Наименование", CarListRow::getNameText,
            "Число осей", CarListRow::getNumberOfAxlesText,
            "Учетная масса брутто", CarListRow::getFullMassText,
            "Длина, м", CarListRow::getLengthText,
            "Тип вагона", CarListRow::getCarTypeText,
            "Время редактирования", CarListRow::getChangeTimeText
    );

    public PageCar clickCreate() {
        buttonCreate.click();
        return page(PageCar.class);
    }

    /** Убедиться что таблица существует. */
    public PageCarsList ensureTableExists() {
        // Пока условие не выполняется, будет ждать до 4-х секунд
        $x(TBODY_XPATH).should(exist);
        $x(FIRST_ROW_PATH).should(exist);
        return this;
    }

    /** Нажать кнопку "Удалить" в контекстном меню */
    public PageCarsList clickDelete() {
        deleteButtonInContextMenu.should(exist);
        deleteButtonInContextMenu.click();
        return this;
    }

    /** Нажать кнопку "Копировать" в контекстном меню */
    public PageCarsList clickCopy() {
        copyButtonInContextMenu.should(exist);
        copyButtonInContextMenu.click();
        return this;
    }

    /** Нажать кнопку "Удалить" в модальном окне */
    public PageCarsList clickModalDeleteOk() {
        okModalDeleteButton.click();
        return this;
    }

    /** Нажать кнопку "Отменить" в модальном окне */
    public PageCarsList clickModalCancel() {
        cancelModalButton.click();
        return this;
    }

    public CarListRow getFirstCarRow() {
        return new CarListRow(this, 1);
    }

    public CarListRow findCarRowByName(String name) {
        String xpathByName = String.format(TBODY_XPATH + "/tr[td[normalize-space(text()) = '%s']]", name);
        SelenideElement trByName = $(By.xpath(xpathByName));
        trByName.should(exist);
        ElementsCollection precedingSiblings = trByName.$$(By.xpath("./preceding-sibling::tr"));
        int rowNumber = precedingSiblings.size() + 1;
        return new CarListRow(this, rowNumber);
    }

    public boolean containsCarRowByName(String name) {
        ElementsCollection rows = $$(By.xpath(TBODY_XPATH + "/tr"));
        for (int i = 0; i < rows.size(); i++) {
            int rowNumber = i + 1;
            CarListRow row = new CarListRow(this, rowNumber);
            if (name.equals(row.carName.text())) {
                return true;
            }
        }
        return false;
    }

    public PageCarsList clickDisplay20CarsOnPage() {
        display20CarsOnPageLink.click();
        return this;
    }

    public PageCarsList clickDisplayAllCarsOnPage() {
        displayAllCarsOnPageLink.click();
        return this;
    }

    public ElementsCollection getAllRows() {
        return allRows;
    }

    public int getNumberOfRows() {
        return allRows.size();
    }

    /**
     * Возвращает ряд страницы Вагонов
     * @param rowNumber номер ряда, начиная с 1
     */
    public CarListRow getCarListRow(int rowNumber) {
        return new CarListRow(this, rowNumber);
    }

    public PageCarsList clickNextPageLink() {
        nextPageLink.click();
        return this;
    }

    public PageCarsList clickLastPageLink() {
        lastPageLink.click();
        return this;
    }

    /** Клик на заголовке столбца Наименование */
    public PageCarsList clickNameColumn() {
        nameColumn.click();
        return this;
    }

    /** Клик на заголовке столбца Число осей */
    public PageCarsList clickNumberOfAxlesColumn() {
        numberOfAxlesColumn.click();
        return this;
    }

    /** Клик на заголовке столбца Учетная масса брутто */
    public PageCarsList clickFullMassColumn() {
        fullMassColumn.click();
        return this;
    }

    /** Клик на заголовке столбца Длина */
    public PageCarsList clickLengthColumn() {
        lengthColumn.click();
        return this;
    }

    /** Клик на заголовке столбца Тип вагона */
    public PageCarsList clickCarTypeColumn() {
        carTypeColumn.click();
        return this;
    }

    /** Клик на заголовке столбца Время редактирования */
    public PageCarsList clickChangeTimeColumn() {
        changeTimeColumn.click();
        return this;
    }

    /** Клик на заголовке столбца с указанным именем */
    public PageCarsList clickOnColumn(String columnName) {
        return columns.get(columnName).get();
    }

    /**
     * Возвращает список текстовых значений всех ячеек указанного столбца.
     * @param columnName имя столбца
     */
    public List<String> getColumnRowTextValues(String columnName) {
        List<String> textValues = new ArrayList<>();
        final int numRows = getNumberOfRows();
        for (int rowNumber = 1; rowNumber <= numRows; rowNumber++) {
            CarListRow listRow = getCarListRow(rowNumber);
            String textValue = rowTextGetters.get(columnName).apply(listRow);
            textValues.add(textValue);
        }
        return textValues;
    }

    /** Клик на кнопке Поиск */
    public PageCarsList clickSearch() {
        searchButton.click();
        return this;
    }

    /** Ввести значение в поле поиска */
    public PageCarsList inputSearch(String value) {
        searchInput.clear();
        searchInput.sendKeys(value);
        return this;
    }

    /** Найти вагоны по поисковому значению */
    public List<CarListRow> findRowsBySearchValue(String searchValue) {
        searchValue = searchValue.toLowerCase();
        List<CarListRow> rows = new ArrayList<>();
        final int numRows = getNumberOfRows();
        for (int rowNumber = 1; rowNumber <= numRows; rowNumber++) {
            var row = getCarListRow(rowNumber);
            if (row.getNameText().toLowerCase().contains(searchValue)
                    || row.getNumberOfAxlesText().contains(searchValue)
                    || row.getFullMassText().contains(searchValue)
                    || row.getLengthText().contains(searchValue)
                    || row.getCarTypeText().toLowerCase().contains(searchValue)
                    || row.getChangeTimeText().contains(searchValue)) {
                rows.add(row);
            }
        }
        return rows;
    }

    /** Получить строковое представление всех вагонов на странице */
    public List<String> getCarsAsStrings() {
        final List<String> list = new ArrayList<>();
        final int numRows = getNumberOfRows();
        for (int rowNumber = 1; rowNumber <= numRows; rowNumber++) {
            list.add(getCarListRow(rowNumber).toString());
        }
        return list;
    }

    public List<String> getCarAbsoluteNumbers() {
        final List<String> list = new ArrayList<>();
        final int numRows = getNumberOfRows();
        for (int rowNumber = 1; rowNumber <= numRows; rowNumber++) {
            list.add(getCarListRow(rowNumber).getAbsoluteRowNumber());
        }
        return list;
    }
}
