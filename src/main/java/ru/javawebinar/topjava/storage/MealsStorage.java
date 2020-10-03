package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealsStorage {

    void add(Meal meal);

    Meal get(int id);

    void update(Meal meal);

    void delete(int id);

    List<Meal> getList();
}
