package com;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Logout")
public class Logout extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }

           
            out.println("<html><body>");
            out.println("<h3>You have successfully logged out.</h3>");
            out.println("<a href='login.html'>Go to Login Page</a>");
            out.println("</body></html>");
        } finally {
            out.close();
        }
    }
}
