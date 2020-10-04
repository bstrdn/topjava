package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealsStorage {
    Meal add(Meal meal);

    Meal get(int id);

    Meal update(Meal meal);

    void delete(int id);

    List<Meal> getList();
}
