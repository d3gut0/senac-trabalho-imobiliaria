package com.fred.servlet;

import com.fred.domain.PropertyDomain;
import com.fred.repository.PropertyRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/panel/property/delete")
public class PanelPropertyDeleteServlet extends PanelAbstractServlet {

    protected void doPost(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws ServletException, IOException {
        if (this.checkUserLogged(request, response)) {
            PropertyRepository propertyRepository = new PropertyRepository();
            try {
                String ids[] = request.getParameterValues("id[]");
                for (String id : ids) {
                    PropertyDomain property = propertyRepository.get(Integer.parseInt(id));
                    propertyRepository.delete(property);
                }
                request.setAttribute("success", "Registro(s) excluído(s) com sucesso!");
            } catch (SQLException e) {
                request.setAttribute("error", e.getMessage());
            }
            response.sendRedirect("/panel");
        }
    }
}
