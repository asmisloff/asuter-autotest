package ru.vniizht.asuter.autotest.car;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.ex.ElementNotFound;
import io.qase.api.annotation.QaseId;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.vniizht.asuter.autotest.constants.User;
import ru.vniizht.asuter.autotest.pages.transport.cars.CarListRow;
import ru.vniizht.asuter.autotest.pages.transport.cars.PageCar;
import ru.vniizht.asuter.autotest.pages.transport.cars.PageCarsList;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static com.codeborne.selenide.Condition.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.vniizht.asuter.autotest.utils.ListUtils.*;

@DisplayName("Вагоны: Фильтрация")
public class CarFilteringTest extends CarBaseTest {

    private final static Deque<String> namesToDelete = new LinkedList<>();

    @AfterEach
    public void deleteCreated() {
        if (namesToDelete.isEmpty()) return;
        while (!namesToDelete.isEmpty()) {
            // Страница должна перезагружаться перед каждым удалением
            // так как если использовать устаревшую страницу со списком вагонов, часть которых уже удалена,
            // то не получится корректно найти строку таблицы по имени вагона
            var page = openCarsListPage().ensureTableExists();
            String nameToDelete = namesToDelete.pollLast();
            boolean deleted = false;
            int numAttempts = 0;
            while (!deleted && numAttempts < 5) {
                try {
                    page.findCarRowByName(nameToDelete).delete();
                    deleted = true;
                } catch (ElementNotFound ex) {
                    // Искомое имя не отобразилось в списке - перезагрузить страницу
                    numAttempts++;
                    page = openCarsListPage().ensureTableExists();
                }
            }
            if (!deleted) throw new IllegalStateException(
                    String.format("Подлежащий удалению вагон '%s' не найден на странице вагонов", nameToDelete)
            );
        }
    }

    @QaseId(331)
    @Test
    @DisplayName("Сортировка объектов по количеству отображаемых на странице вагонов")
    public void testNumCarsInSortedPages() {
        loginIfNeeded(User.READ);
        openCarsListPage().ensureTableExists()
                .clickDisplayAllCarsOnPage()
                .waitTime(100)
                .check(p -> {
                    p.nextPageLink.shouldNot(exist);
                    p.lastPageLink.shouldNot(exist);
                    assertTrue(p.getNumberOfRows() > 20, "Должно отображаться более 20 вагонов");
                })
                .clickDisplay20CarsOnPage()
                .waitTime(100)
                .check(p -> {
                    p.nextPageLink.should(exist);
                    p.nextPageLink.parent().shouldBe(enabled);
                    p.lastPageLink.should(exist);
                    p.lastPageLink.parent().shouldBe(enabled);
                    assertEquals(20, p.getNumberOfRows(), "Должно отображаться 20 вагонов");
                });
    }

    @QaseId(332)
    @ParameterizedTest
    @ValueSource(strings = {"Наименование", "Число осей", "Учетная масса брутто", "Длина, м", "Тип вагона", "Время редактирования" })
    @DisplayName("Сортировка объектов по столбцам на странице вагонов")
    public void testSortingByColumns(String column) {
        loginIfNeeded(User.READ);
        final List<String> initialValues = new ArrayList<>();
        final long timeForFrontend = 50;
        openCarsListPage()
                .ensureTableExists()
                .also(p -> {
                    var values = p.getColumnRowTextValues(column);
                    initialValues.addAll(values);
                })
                .clickOnColumn(column)
                .waitTime(timeForFrontend)
                .check(p -> {
                    // объекты отсортировались по возрастанию
                    List<String> sortedAsc = p.getColumnRowTextValues(column);
                    assertTrue(listOfStringsIsSortedAsc(sortedAsc),
                            () -> String.format("Элементы столбца \"%s\" не отсортированы по возрастанию: %s", column, sortedAsc));
                })
                .clickOnColumn(column)
                .waitTime(timeForFrontend)
                .check(p -> {
                    // объекты остортировались по убыванию
                    List<String> sortedDesc = p.getColumnRowTextValues(column);
                    assertTrue(listOfStringsIsSortedDesc(sortedDesc),
                            () -> String.format("Элементы столбца \"%s\" не отсортированы по убыванию: %s", column, sortedDesc));
                })
                .clickOnColumn(column)
                .waitTime(timeForFrontend)
                .check(p -> {
                    // сортировка сброшена
                    List<String> notSorted = p.getColumnRowTextValues(column);
                    assertEquals(initialValues, notSorted,
                            () -> String.format(
                                    "Элементы столбца \"%s\" после сброса сортировки (%s) не соответствуют исходным элементам: %s",
                                    column, notSorted, initialValues)
                    );
                });
    }

    @QaseId(333)
    @Test
    @DisplayName("Страницы пагинации работают корректно на странице вагонов")
    public void testSecondPageAfterAdding21Cars() {
        loginIfNeeded(User.NSI);
        AtomicReference<String> originalFirstRowName = new AtomicReference<>();
        openCarsListPage()
                .ensureTableExists()
                .also(p -> originalFirstRowName.set(p.getFirstCarRow().getNameText()))
                .getFirstCarRow()
                .also(r -> {
                    // Добавить 21 объект на страницу копированием
                    for (int i = 0; i < 21; i++) {
                        var page = r.getPage();
                        copyFirstCar(page);
                        Selenide.refresh();
                        var nameOfCopyToDelete = page.getFirstCarRow().getNameText();
                        namesToDelete.add(nameOfCopyToDelete);
                    }
                });
        // перейти на 2-ю страницу
        openCarsListPage()
                .ensureTableExists()
                .clickNextPageLink()
                .ensureTableExists()
                .check(p -> {
                    // объекты отображаются корректно
                    String expectedSecondRowName = originalFirstRowName.get();
                    String expectedFirstRowName = expectedSecondRowName + "(1)";
                    var firstRow = p.getCarListRow(1);
                    firstRow.carName.shouldHave(text(expectedFirstRowName));
                    var secondRow = p.getCarListRow(2);
                    secondRow.carName.shouldHave(text(expectedSecondRowName));
                });
    }

    @QaseId(334)
    @ParameterizedTest
    @ValueSource(strings = {"0", "9", "1", "=", "композитный", "А" })
    @DisplayName("Поиск по объектам работает корректно на странице вагонов")
    public void testSearch(String searchValue) {
        loginIfNeeded(User.READ);
        List<String> expectedCars = new ArrayList<>();
        final long delay = 50;
        openCarsListPage()
                .ensureTableExists()
                .clickDisplayAllCarsOnPage()
                .waitTime(delay)
                .also(p -> {
                    // удостовериться, что существуют ряды содержащие указанное значение поиска
                    // если нет, то результат фильтрации не должен содержать ни одного ряда
                    expectedCars.addAll(
                            p.findRowsBySearchValue(searchValue)
                                    .stream()
                                    .map(CarListRow::toString)
                                    .sorted()
                                    .toList()
                    );
                });

        // Ввести значение для поиска и проверить результат
        openCarsListPage()
                .ensureTableExists()
                .clickSearch()
                .inputSearch(searchValue)
                .waitTime(500)  // задержка против "Failed to fetch"
                .pressTab()
                .clickDisplayAllCarsOnPage()
                .waitTime(delay)
                .check(p -> {
                    // Данные на странице отфильтрованы по введенному параметру
                    List<String> carsOnPage = p.getCarsAsStrings();
                    if (expectedCars.isEmpty()) {
                        assertTrue(carsOnPage.isEmpty(),
                                () -> String.format(
                                        "Для значения поиска \"%s\" ожидается отсутствие отфильтрованных данных",
                                        searchValue
                                ));
                    } else {
                        Collections.sort(carsOnPage);
                        assertEquals(expectedCars, carsOnPage);
                    }
                });
    }

    @QaseId(335)
    @Test
    @DisplayName("Нумерация объектов на страницах пагинации работает корректно на странице вагонов")
    public void testPaginationNumeration() {
        loginIfNeeded(User.NSI);
        openCarsListPage()
                .ensureTableExists()
                .getFirstCarRow()
                .also(r -> {
                    // Добавить 21 объект на страницу копированием
                    for (int i = 0; i < 21; i++) {
                        var page = r.getPage();
                        copyFirstCar(page);
                        Selenide.refresh();
                        var name = page.getFirstCarRow().tryToGetNameText();
                        namesToDelete.add(name);
                    }
                });
        // перейти на 2-ю страницу
        openCarsListPage()
                .ensureTableExists()
                .clickNextPageLink()
                .ensureTableExists()
                .check(p -> {
                    //Объекты на 2-й странице имеют номера 21-40
                    List<String> carNumbers = p.getCarAbsoluteNumbers();
                    for (int i = 0; i < 20; i++) {
                        assertEquals(Integer.toString((i + 21)), carNumbers.get(i));
                    }
                });
    }

    @QaseId(336)
    @Test
    @DisplayName("Возможность открыть объекты со второй страницы пагинации на странице вагонов")
    public void testOpenCarsOnSecondPage() {
        loginIfNeeded(User.NSI);
        openCarsListPage()
                .ensureTableExists()
                .getFirstCarRow()
                .also(r -> {
                    // Добавить 21 объект на страницу копированием
                    for (int i = 0; i < 21; i++) {
                        var page = r.getPage();
                        copyFirstCar(page);
                        Selenide.refresh();
                        var name = page.getFirstCarRow().tryToGetNameText();
                        namesToDelete.add(name);
                    }
                });
        // перейти на 2-ю страницу
        openCarsListPage()
                .ensureTableExists()
                .clickNextPageLink()
                .ensureTableExists()
                .check(p -> {
                    var carRow = p.getFirstCarRow();
                    String name = carRow.getNameText();
                    PageCar pageCar = carRow.openCar();
                    pageCar.nameInput.shouldHave(value(name));
                });
    }

    private void copyFirstCar(PageCarsList page) {
        page.getFirstCarRow()
                .copy()
                .waitTime(50)
                .ensureTableExists();
    }

    @NotNull
    private static PageCarsList openCarsListPage() {
        return open(PageCarsList.class, PageCarsList.FIRST_TD_XPATH);
    }
}
