package com.fred.servlet;

import com.fred.domain.PropertyDomain;
import com.fred.repository.PropertyRepository;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

@WebServlet("/panel/property/form")
@MultipartConfig
public class PanelPropertyFormServlet extends PanelAbstractServlet {

    protected void doGet(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws ServletException, IOException {
        if (this.checkUserLogged(request, response)) {
            if (request.getParameter("id") != null) {
                try {
                    PropertyRepository propertyRepository = new PropertyRepository();
                    PropertyDomain property = propertyRepository.get(Integer.parseInt(request.getParameter("id")));
                    request.setAttribute("property", property);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            request.getRequestDispatcher("/WEB-INF/panel/property/form.jsp").forward(request, response);
        }
    }

    protected void doPost(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws ServletException, IOException {
        if (this.checkUserLogged(request, response)) {
            try {
                PropertyRepository propertyRepository = new PropertyRepository();
                PropertyDomain property = new PropertyDomain();
                if (!request.getParameter("id").equals("")) {
                    property = propertyRepository.get(Integer.parseInt(request.getParameter("id")));
                }

                property.setCode(request.getParameter("code"));
                property.setTitle(request.getParameter("title"));
                property.setDescription(request.getParameter("description"));
                property.setValue(Float.parseFloat(request.getParameter("value")));
                if (!request.getPart("image").getSubmittedFileName().equals("")) {
                    // Convert InputStream into Blob
                    InputStream input = request.getPart("image").getInputStream();
                    byte[] image = IOUtils.toByteArray(input);
                    property.setImage(image);
                }

                propertyRepository.save(property);

                request.setAttribute("property", property);
                request.setAttribute("success", "Registro salvo com sucesso!");
            } catch (SQLException e) {
                request.setAttribute("error", e.getMessage());
            }
            request.getRequestDispatcher("/WEB-INF/panel/property/form.jsp").forward(request, response);
        }
    }
}
