package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.dao.impl.MealImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class DeleteMealServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MealImpl mealImpl = new MealImpl();
        int id = Integer.valueOf(request.getParameter("id"));
        AtomicInteger atomicId = new AtomicInteger(id);
        mealImpl.remove(atomicId);
        response.sendRedirect("meals");
    }
}
