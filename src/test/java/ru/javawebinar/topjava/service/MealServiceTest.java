package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.assertMatch;
import static ru.javawebinar.topjava.MealTestData.getNew;
import static ru.javawebinar.topjava.MealTestData.getUpdated;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(MEAL_ID, USER_ID);
        assertMatch(meal1, meal);
    }

    @Test
    public void getAnotherMeal() {
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID, ADMIN_ID));
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
    }

    @Test
    public void delete() {
        service.delete(MEAL_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID, USER_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, USER_ID));
    }

    @Test
    public void deleteAnotherMeal() {
        assertThrows(NotFoundException.class, () -> service.delete(MEAL_ID, ADMIN_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> expected = service.getBetweenInclusive(DATE_FROM, DATE_TO.minusDays(1), USER_ID);
        assertMatch(Arrays.asList(meal5, meal6), expected);
    }

    @Test
    public void getBetweenInclusiveWithNull() {
        List<Meal> expected = service.getBetweenInclusive(DATE_FROM, null, USER_ID);
        assertMatch(Arrays.asList(meal1, meal2, meal5, meal6), expected);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER_ID);
        assertMatch(all, Arrays.asList(meal2, meal1, meal6, meal5, meal3, meal4));
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL_ID, USER_ID), updated);

    }

    @Test
    public void duplicateDateTimeUpdate() {
        Meal updated = getUpdated();
        updated.setDateTime(DATETIME_MEAL2);
        assertThrows(DuplicateKeyException.class, () -> service.update(updated, USER_ID));
    }

    @Test
    public void updateAnotherMeal() {
        assertThrows(NotFoundException.class, () -> service.update(meal1, ADMIN_ID));
    }

    @Test
    public void create() {
        Meal newMeal = getNew();
        Meal create = service.create(newMeal, USER_ID);
        Integer newId = create.getId();
        newMeal.setId(newId);
        assertMatch(service.get(newId, USER_ID), newMeal);
    }
}