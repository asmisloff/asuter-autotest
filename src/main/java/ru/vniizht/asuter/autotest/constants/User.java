package ru.vniizht.asuter.autotest.constants;

public class User {

    public final String login;
    public final String password;

    private User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    // TODO возможно не хардкодить, а тащить из файла properties
    public static final User NSI = new User("testnsi", "Testtest2020)");
    public static final User READ = new User("testread", "Testtest2020)");
    public static final User CALC = new User("testcalc", "Testtest2020)");
}
