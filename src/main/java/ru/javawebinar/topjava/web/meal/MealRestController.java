package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.List;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<Meal> getAll(int i) {
        log.info("getAll");
        return service.getAll(i);
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id, SecurityUtil.getAuthUserId());
    }

    public Meal save(Meal meal, int userId) {
        log.info("save {}", meal);
        ValidationUtil.checkNew(meal);
        return service.save(meal, userId);
    }

    public Meal update(Meal meal, int userId) {
        return service.save(meal, userId);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }
}
