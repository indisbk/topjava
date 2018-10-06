package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class MealServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MealsUtil mealsUtil = new MealsUtil();
        int caloriesPerDay = 2000;
        List<MealWithExceed> mealWithExceeds = mealsUtil.getFilteredWithExceeded(mealsUtil.getMeals(), caloriesPerDay);

        request.setAttribute("mealWithExceeds", mealWithExceeds);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/meals.jsp");
        dispatcher.forward(request, response);
    }
}
