package ru.vniizht.asuter.autotest.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.regex.Pattern;

public class ListUtils {

    private static final Comparator<String> stringComparator = new NumberDateTimeStringComparator();

    /** Проверяет что список является неубывающим */
    public static <T extends Comparable<T>> boolean listIsSortedAsc(List<T> list) {
        return listIsSorted(list, (t1, t2) -> t1.compareTo(t2) <= 0);
    }

    /** Проверяет что список является невозрастающим */
    public static <T extends Comparable<T>> boolean listIsSortedDesc(List<T> list) {
        return listIsSorted(list, (t1, t2) -> t1.compareTo(t2) >= 0);
    }

    /** Проверяет что список строк является неубывающим с учетом того что строки могут представлять натуральные числа */
    public static boolean listOfStringsIsSortedAsc(List<String> list) {
        return listIsSorted(list, (s1, s2) -> stringComparator.compare(s1, s2) <= 0);
    }

    /** Проверяет что список строк является невозрастающим с учетом того что строки могут представлять натуральные числа */
    public static boolean listOfStringsIsSortedDesc(List<String> list) {
        return listIsSorted(list, (s1, s2) -> stringComparator.compare(s1, s2) >= 0);
    }

    /**
     * Проверяет что список является отсортированным согласно переданному предикату.
     * @param list проверяемый список
     * @param predicateOfSort предикат возвращающий true в том случае если условие упорядоченности соблюдается
     */
    public static <T extends Comparable<T>> boolean listIsSorted(List<T> list, BiPredicate<T, T> predicateOfSort) {
        if (list.isEmpty() || list.size() == 1) {
            return true;
        }
        T previous = list.get(0);
        BiPredicate<T, T> predicateOfNotSorted = predicateOfSort.negate();
        for (int i = 1; i < list.size(); i++) {
            T current = list.get(i);
            if (predicateOfNotSorted.test(previous, current)) {
                System.out.printf("%s breaks %s", previous, current);
                return false;
            }
            previous = current;
        }
        return true;
    }

    /**
     * Если обе строки являются строковыми представлением натуральных чисел, то для сравнения преобразует их в числа,
     * если обе строки являются представлением даты (dd-MM-yyyy HH:mm:ss), то для сравнения преобразует их в LocalDateTime,
     * иначе сравнивает как обычные строки (lexicographically).
     */
    private static class NumberDateTimeStringComparator implements Comparator<String> {
        private static final Pattern naturalNumberPattern = Pattern.compile("^\\d+$");
        private static final Pattern datePattern = Pattern.compile("^\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2}$");

        private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        @Override
        public int compare(String s1, String s2) {
            if (naturalNumberPattern.matcher(s1).matches() && naturalNumberPattern.matcher(s2).matches()) {
                return Integer.compare(Integer.parseInt(s1), Integer.parseInt(s2));
            } else if (datePattern.matcher(s1).matches() && datePattern.matcher(s2).matches()) {
                LocalDateTime dt1 = LocalDateTime.parse(s1, dateTimeFormatter);
                LocalDateTime dt2 = LocalDateTime.parse(s2, dateTimeFormatter);
                return dt1.compareTo(dt2);
            }
            return s1.compareTo(s2);
        }
    }
}
