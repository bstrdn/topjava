package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL_ID = START_SEQ + 2;
    public static final LocalDate DATE_FROM = LocalDate.parse("2020-10-17");
    public static final LocalDate DATE_TO = LocalDate.parse("2020-10-17");
    public static final LocalDateTime DATETIME_MEAL2 = LocalDateTime.parse("2020-10-18T23:00");

    public static final Meal meal1 = new Meal(100002, LocalDateTime.parse("2020-10-18T11:00"),
            "Завтрак", 700);
    public static final Meal meal2 = new Meal(100003, DATETIME_MEAL2,
            "Ужин", 700);
    public static final Meal meal3 = new Meal(100004, LocalDateTime.parse("2020-10-11T06:15"),
            "Завтрак", 200);
    public static final Meal meal4 = new Meal(100005, LocalDateTime.parse("2020-10-10T17:00"),
            "Ужин", 2200);
    public static final Meal meal5 = new Meal(100006, LocalDateTime.parse("2020-10-17T10:30"),
            "Завтрак", 1000);
    public static final Meal meal6 = new Meal(100007, LocalDateTime.parse("2020-10-17T20:00"),
            "Ужин", 1500);

    public static Meal getNew() {
        return new Meal(LocalDateTime.parse("2020-10-18T11:10"),
                "Breakfast", 700);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(meal1);
        updated.setDescription("Полдник");
        updated.setCalories(3000);
        return updated;
    }
}
