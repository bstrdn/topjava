package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL_ID = START_SEQ + 2;
    public static final LocalDate DATE_FROM = LocalDate.of(2020, 10, 17);
    public static final LocalDate DATE_TO = LocalDate.of(2020, 10, 17);
    public static final LocalDateTime DATETIME_MEAL2 = LocalDateTime.of(2020, 10, 18, 23, 0);

    public static final Meal meal1 = new Meal(100002, LocalDateTime.of(2020, 10, 18, 11, 0),
            "Завтрак", 700);
    public static final Meal meal2 = new Meal(100003, DATETIME_MEAL2,
            "Ужин", 700);
    public static final Meal meal3 = new Meal(100004, LocalDateTime.of(2020, 10, 11, 6, 15),
            "Завтрак", 200);
    public static final Meal meal4 = new Meal(100005, LocalDateTime.of(2020, 10, 10, 17, 0),
            "Ужин", 2200);
    public static final Meal meal5 = new Meal(100006, LocalDateTime.of(2020, 10, 17, 10, 30),
            "Завтрак", 1000);
    public static final Meal meal6 = new Meal(100007, LocalDateTime.of(2020, 10, 17, 20, 0),
            "Ужин", 1500);

    public static Meal getNew() {
        return new Meal(LocalDateTime.of(2020, 10, 18, 11, 10),
                "Breakfast", 700);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(meal1);
        updated.setDateTime(LocalDateTime.of(2020, 10, 21, 10, 47));
        updated.setDescription("Полдник");
        updated.setCalories(3000);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).isEqualTo(expected);
    }
}
