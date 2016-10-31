package com.fred.servlet;

import com.fred.repository.PropertyRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/panel")
public class PanelServletHome extends PanelAbstractServlet {

    protected void doGet(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws ServletException, IOException {
        if (this.checkUserLogged(request, response)) {
            try {
                PropertyRepository propertyRepository = new PropertyRepository();
                request.setAttribute("properties", propertyRepository.findAll());
            } catch (SQLException e) {
                request.setAttribute("error", "Problema ao listar imóveis.");
            }
            request.getRequestDispatcher("/WEB-INF/panel/property/list.jsp").forward(request, response);
        }
    }
}
