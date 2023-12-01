package ru.vniizht.asuter.autotest.constants;

public record User(String login, String password) {

    // TODO возможно не хардкодить, а тащить из файла properties
    public static final User NSI = new User("testnsi", "Testtest2020)");
    public static final User READ = new User("testread", "Testtest2020)");
    public static final User CALC = new User("testcalc", "Testtest2020)");

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        if (!login.equals(user.login)) return false;
        return password.equals(user.password);
    }

}
