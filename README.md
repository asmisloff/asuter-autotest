```
TODO: дополнить и структурировать README
```

<h3> Особенности архитектуры и CodeStyle </h3>

* Классы, соответствующие страницам приложения, наследуются от класса BasePage
* URL соответствующей страницы в этих классах указывается с помощью аннотации @Url над классом
* Названия классов страниц начинаются с "Page"
* Методы, отвечающие за взаимодействие со страницей используют Fluent API подход (возможность строить цепочки из методов)
* Классы тестов наследуются от класса BaseTest
* Взаимодействие страницы (сущности) с другими страницами (сущностями) проверяется в тестах, относящихся
к той странице (сущности), с которой происходит изменение (удаление, создание, редактирование и пр.).
```
Пример:
Если провод используется в тяговой сети и при этом удаляется или редактируется, то проверка происходит в тестах связанных с проводами и
рельсами, а не в тестах связанных с тяговыми сетями.
```
* Статический метод ```open(pageClass)``` доступен в классах, унаследованных от BasePage и BaseTest.
Этот метод позволяет перейти на страницу, указанную в аннотации @Url над классом, тип которого передан в аргумент метода.
Чтобы получить объект страницы без перехода по её адресу, используется метод ```Selenide.page(pageClass)```
* Статический метод ```urlOf(pageClass)``` доступен в классах, унаследованных от BasePage и BaseTest. Этот метод позволяет
получить url, указанный в аннотации @Url над классом, тип которого передан в аргумент метода
* Статический метод ```login(login, password)``` класса BaseTest открывает страницу авторизации и выполняет вход
с переданными логином и паролем. Если логин и пароль не переданы, выполняет вход под учетной записью тестового
пользователя с правом редактирования НСИ (на данный момент testnsi, захардкожено)
* Статический метод ```clickLogout()``` класса BasePage нажимает кнопку "Выйти"
* Если в одном тестовом методе проверяется соответствие нескольким условиям, не забывать проверять их через ```Condition.allOf()```,
чтобы проходили все проверки, даже если некоторые из них упали
* Константы со значениями цветов для проверки цвета элементов при валидных/не валидных введенных данных хранятся в классе Colors
* Предпочтительный способ поиска элементов на странице - по аттрибуту ```data-testid```


<h3> Входные данные для тестов </h3>
Лежат в формате csv, в каталоге ресурсов, в папке "csv" в кодировке UTF-8.