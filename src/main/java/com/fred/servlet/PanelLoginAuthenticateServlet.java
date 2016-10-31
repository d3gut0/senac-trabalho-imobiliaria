package com.fred.servlet;

import com.fred.domain.UserDomain;
import com.fred.repository.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/panel/authenticate")
public class PanelLoginAuthenticateServlet extends HttpServlet {

    protected void doPost(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserRepository userRepository = new UserRepository();
        try {
            UserDomain user = userRepository.findByEmailAndPassword(
                request.getParameter("email"),
                request.getParameter("password")
            );
            if (user != null) {
                session.setAttribute("user", user);
                response.sendRedirect("/panel");
                return;
            }
            request.setAttribute("error", "Usuário e ou senha inválidos");
        } catch (SQLException e) {
            request.setAttribute("error", e.getMessage());
        }
        request.getRequestDispatcher("/WEB-INF/panel/login.jsp").forward(request, response);
    }
}
