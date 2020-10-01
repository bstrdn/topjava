package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "breakfast", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "lunch", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "dinner", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "breakfast", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "lunch", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "dinner", 410),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 9, 0), "dinner", 410),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 11, 0), "dinner", 41)
        );

        List<UserMealWithExcess> mealsTo1 = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo1.forEach(System.out::println);

        System.out.println("_____________________________");

        List<UserMealWithExcess> mealsTo2 = filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo2.forEach(System.out::println);

        System.out.println("_____________________________");

        List<UserMealWithExcess> mealsTo3 = filteredByCyclesOnePass(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo3.forEach(System.out::println);
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> excessDate = new HashMap<>();
        for (UserMeal meal : meals) {
            excessDate.merge(meal.getDate(), meal.getCalories(), Integer::sum);
        }

        List<UserMealWithExcess> mealsWithExcess = new ArrayList<>();
        for (UserMeal meal : meals) {
            if (TimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime)) {
                mealsWithExcess.add(userMealToUserMealWithExcess(meal, new AtomicBoolean(excessDate.get(meal.getDate()) >= caloriesPerDay)));
            }
        }

        return mealsWithExcess;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> excessDate = meals.stream()
                .collect(Collectors.groupingBy(UserMeal::getDate, Collectors.summingInt(UserMeal::getCalories)));

        return meals.stream()
                .filter(m -> TimeUtil.isBetweenHalfOpen(m.getTime(), startTime, endTime))
                .map(m -> userMealToUserMealWithExcess(m, new AtomicBoolean(excessDate.get(m.getDate()) >= caloriesPerDay)))
                .collect(Collectors.toList());
    }

    public static List<UserMealWithExcess> filteredByCyclesOnePass(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> list = new ArrayList<>();
        Map<LocalDate, AtomicBoolean> mapBool = new HashMap<>();
        Map<LocalDate, Integer> mapCalSum = new HashMap<>();

        for (UserMeal meal : meals) {
            LocalDate date = meal.getDate();
            mapCalSum.merge(date, meal.getCalories(), Integer::sum);

            if (mapBool.get(date) == null) {
                mapBool.put(date, new AtomicBoolean(false));
            }

            if (mapCalSum.get(date) > caloriesPerDay) {
                mapBool.get(date).set(true);
            }

            if (TimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime)) {
                list.add(userMealToUserMealWithExcess(meal, mapBool.get(date)));
            }
        }
        return list;
    }

    public static UserMealWithExcess userMealToUserMealWithExcess(UserMeal userMeal, AtomicBoolean excess) {
        return new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), excess);
    }
}
