package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.MapMealsStorage;
import ru.javawebinar.topjava.storage.MealsStorage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import static java.lang.Integer.parseInt;
import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private MealsStorage mealsStorage;
    private static final Logger log = getLogger(MealServlet.class);

    @Override
    public void init() {
        mealsStorage = new MapMealsStorage();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String dataTime = req.getParameter("date");
        String calories = req.getParameter("calories");
        String description = req.getParameter("description");
        String id = req.getParameter("id");
        if (id.equals("0")) {
            mealsStorage.add(new Meal(LocalDateTime.parse(dataTime), description, parseInt(calories)));
        } else {
            mealsStorage.update(new Meal(parseInt(id), LocalDateTime.parse(dataTime), description, parseInt(calories)));
        }
        resp.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String paramAct = req.getParameter("action");

        if (!Objects.isNull(paramAct)) {
            String paramId = req.getParameter("id");
            switch (req.getParameter("action")) {
                case "delete":
                    log.debug("action: delete meal with id: " + paramId);
                    mealsStorage.delete(parseInt(paramId));
                    resp.sendRedirect("meals");
                    return;
                case "create":
                    log.debug("action: create new meal");
                    forwardToEdit(new Meal(), req, resp);
                    break;
                case "update":
                    log.debug("action: update meal with id: " + paramId);
                    forwardToEdit(mealsStorage.get(parseInt(paramId)), req, resp);
                    break;
            }
        }

        List<MealTo> listMeal = MealsUtil.filteredByStreams(mealsStorage.getList(), LocalTime.MIN, LocalTime.MAX, MapMealsStorage.caloriesPerDay);
        req.setAttribute("list", listMeal);
        req.getRequestDispatcher("meals.jsp").forward(req, resp);
    }

    void forwardToEdit(Meal meal, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("meal", meal);
        req.getRequestDispatcher("mealsEdit.jsp").forward(req, resp);
    }
}
