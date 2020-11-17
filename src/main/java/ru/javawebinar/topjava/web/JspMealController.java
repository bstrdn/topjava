package ru.javawebinar.topjava.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.AbstractMealController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class JspMealController extends AbstractMealController {

    @GetMapping("/meals")
    public String getMeals(Model model) {
        model.addAttribute("meals", super.getAll());
        return "meals";
    }

    @PostMapping("/meals")
    public String getUpdateOrCrate(HttpServletRequest request) {
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        if (!StringUtils.hasText(request.getParameter("id"))) {
            super.create(meal);
        } else {
            super.update(meal, Integer.parseInt(request.getParameter("id")));
        }
        return "redirect:/meals";
    }

    @GetMapping("meals/delete")
    public String deleteMeal(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        super.delete(id);
        return "redirect:/meals";
    }

    @GetMapping("meals/update")
    public String updateMeal(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Meal meal = service.get(id, authUserId());
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("meals/create")
    public String createMeal(HttpServletRequest request) {
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "123", 1000);
        request.setAttribute("meal", meal);
        request.setAttribute("action", "create");
        return "mealForm";
    }

    @GetMapping("meals/filter")
    public String getBetween(HttpServletRequest request, Model model) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        model.addAttribute("meals", super.getBetween(startDate, startTime, endDate, endTime));
        return "/meals";
    }
}