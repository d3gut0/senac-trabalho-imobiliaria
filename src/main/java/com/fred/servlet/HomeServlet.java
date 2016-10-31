package com.fred.servlet;

import com.fred.domain.PropertyDomain;
import com.fred.repository.PropertyRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("")
public class HomeServlet extends HttpServlet {

    private static int ONE_MONTH = 30 * 24 * 60 * 60;

    protected void doGet(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws ServletException, IOException {

        // Set cookie
        if (request.getParameter("order") != null) {
            Cookie orderCookie = new Cookie("order", request.getParameter("order"));
            orderCookie.setMaxAge(ONE_MONTH);
            response.addCookie(orderCookie);
        }

        // Get order cookie
        String order = request.getParameter("order");
        if (order == null) {
            order = this.getCookie(request, "order");
        }
        request.setAttribute("order", order);

        // Get properties from database
        PropertyRepository propertyRepository = new PropertyRepository();
        try {
            List<PropertyDomain> properties = propertyRepository.findByDescription(request.getParameter("search"), order);
            request.setAttribute("properties", properties);
        } catch (SQLException e) {
            request.setAttribute("error", "Problema ao listar imóveis.");
        }

        request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
    }

    private String getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return "";
    }
}
