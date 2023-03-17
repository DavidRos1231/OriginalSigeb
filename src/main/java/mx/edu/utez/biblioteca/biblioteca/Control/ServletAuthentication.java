package mx.edu.utez.biblioteca.biblioteca.Control;

import mx.edu.utez.biblioteca.biblioteca.model.BeanAuthentication ;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "ServletAuthentication", urlPatterns = {
        "/login",
        "/logout"
}
)
public class ServletAuthentication extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String option = request.getServletPath();

        if (option.equals("/logout")) {
            HttpSession session = request.getSession(false);

            if (session != null) {
                session.invalidate();
                response.sendRedirect("index.jsp");
            } else {
                response.sendRedirect("index.jsp");
            }

        } else {
            response.sendRedirect("index.jsp");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String option = request.getServletPath();

        if (option.equals("/login"))
        {/*
            HttpSession session =request.getSession(false);
            if (session != null) {
            response.sendRedirect("menu");
        } else {*/


            String id = request.getParameter("id") != null ? request.getParameter("id") : "";
            String password = request.getParameter("password") != null ? request.getParameter("password") : "";

            ServiceAuthentication serviceAuthenticacion = new ServiceAuthentication();
            BeanAuthentication userAuthentication =  serviceAuthenticacion.login(id,password);

            if (userAuthentication.getNickname() != null){
                 HttpSession session = request.getSession(true);
                session.setAttribute("name", userAuthentication.getNickname());
                session.setAttribute("role", userAuthentication.getRol());
                session.setAttribute("noprestamos",userAuthentication.getNoprestamos());
                session.setAttribute("salaprestada",userAuthentication.isSalaprestada());
                response.sendRedirect("menu");
            } else {
                response.sendRedirect("index.jsp?message=error");
            }


        } else {
            //request.getRequestDispatcher("index.jsp").forward(request,response);
            response.sendRedirect("index.jsp");
        }
    }
}
