package ru.netology.data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataGenerator {
    private static final LocalDate pastMonth = LocalDate.now().minusMonths(2);
    private static final LocalDate pastYear = LocalDate.now().minusYears(1);
    private static final LocalDate futureDate = LocalDate.now().plusMonths(2);
    private static final DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MM");
    private static final DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yy");

    private DataGenerator() {
    }

    public static Card generateValidCard() {
        return new Card(
                "4444 4444 4444 4441",
                futureDate.format(monthFormatter),
                futureDate.format(yearFormatter),
                "Vasya Pupkin",
                "123");
    }

    public static Card generateInvalidCard() {
        return new Card(
                "4444 4444 4444 4442",
                futureDate.format(monthFormatter),
                futureDate.format(yearFormatter),
                "John Doe",
                "123");
    }

    public static Card generateExpiredMonthCard() {
        return new Card(
                "4444 4444 4444 4441",
                pastMonth.format(monthFormatter),
                pastMonth.format(yearFormatter),
                "Vasya Pupkin",
                "123");
    }

    public static Card generateExpiredYearCard() {
        return new Card(
                "4444 4444 4444 4441",
                pastYear.format(monthFormatter),
                pastYear.format(yearFormatter),
                "Vasya Pupkin",
                "123");
    }

    public static Card generateInvalidNumberCard() {
        return new Card(
                "4444 4444 4444 4444 4441",
                futureDate.format(monthFormatter),
                futureDate.format(yearFormatter),
                "John Doe",
                "123");
    }

    public static Card generateInvalidHolderCard() {
        return new Card(
                "4444 4444 4444 4441",
                futureDate.format(monthFormatter),
                futureDate.format(yearFormatter),
                "Вася Пупкин",
                "123");
    }

    public static Card generateEmptyHolderCard() {
        return new Card(
                "4444 4444 4444 4441",
                futureDate.format(monthFormatter),
                futureDate.format(yearFormatter),
                "",
                "123");
    }

    public static Card generateInvalidCodeCard() {
        return new Card(
                "4444 4444 4444 4441",
                futureDate.format(monthFormatter),
                futureDate.format(yearFormatter),
                "John Doe",
                "");
    }
}
