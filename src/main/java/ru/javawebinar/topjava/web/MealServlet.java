package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.MealsStorage;
import ru.javawebinar.topjava.storage.MealsStorageMap;
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

public class MealServlet extends HttpServlet {
    MealsStorage mealsStorage = new MealsStorageMap();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String dataTime = req.getParameter("date");
        String calories = req.getParameter("cal");
        String description = req.getParameter("des");
        String id = req.getParameter("id");
        if (id.equals("0")) {
            mealsStorage.add(new Meal(LocalDateTime.parse(dataTime), description, Integer.parseInt(calories)));
        } else {
            mealsStorage.update(new Meal(LocalDateTime.parse(dataTime), description, Integer.parseInt(calories), Integer.parseInt(id)));
        }
        resp.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String paramAct = req.getParameter("action");
        String paramId = req.getParameter("id");

        if (!Objects.isNull(paramAct) && !Objects.isNull(paramId)) {
            int id = Integer.parseInt(paramId);
            switch (req.getParameter("action")) {
                case "delete":
                    mealsStorage.delete(id);
                    break;
                case "create":
                    req.getRequestDispatcher("mealsEdit.jsp").forward(req, resp);
                    break;
                case "update":
                    Meal meal = mealsStorage.get(id);
                    req.setAttribute("meal", meal);
                    req.getRequestDispatcher("mealsEdit.jsp").forward(req, resp);
                    break;
            }
        }

        List<MealTo> listMeal = MealsUtil.filteredByStreams(mealsStorage.getList(), LocalTime.MIN, LocalTime.MAX, 2000);
        req.setAttribute("list", listMeal);
        req.getRequestDispatcher("meals.jsp").forward(req, resp);
    }
}
