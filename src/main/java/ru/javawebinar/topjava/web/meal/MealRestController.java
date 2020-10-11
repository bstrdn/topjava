package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.web.SecurityUtil.getAuthUserId;

@Controller
public class MealRestController {
    protected static final Logger log = LoggerFactory.getLogger(MealRestController.class);
    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<Meal> getAll() {
        log.info("getAll");
        return service.getAll(getAuthUserId());
    }

    public List<Meal> getFiltered(LocalDate dateFrom, LocalDate dateTo, LocalTime timeFrom, LocalTime timeTo) {
        return service.getFiltered(getAuthUserId(), dateFrom, dateTo, timeFrom, timeTo);
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id, getAuthUserId());
    }

    public Meal create(Meal meal) {
        log.info("save {}", meal);
        ValidationUtil.checkNew(meal);
        return service.create(meal, getAuthUserId());
    }

    public Meal update(Meal meal) {
        log.info("update {}", meal);
        return service.create(meal, getAuthUserId());
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, getAuthUserId());
    }

    public List<MealTo> getTos() {
        return MealsUtil.getTos(getAll(), authUserCaloriesPerDay());
    }

    public List<MealTo> getFilteredTos(LocalDate dateFrom, LocalDate dateTo, LocalTime timeFrom, LocalTime timeTo) {
        return MealsUtil.getTos(service.getFiltered(getAuthUserId(), dateFrom, dateTo, timeFrom, timeTo), authUserCaloriesPerDay());
    }
}
