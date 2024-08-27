package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/getrecord")
public class GetRecord extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        response.setContentType("text/html");
        HttpSession session = request.getSession(false);

        if (session != null) {
            String user = (String) session.getAttribute("name");
            pw.print("<h1 align='center'>Welcome, " + user + " - Here are your account details</h1>");

            Connection con = null;
            try {
                con = DBConnection.get();
                int num = Integer.parseInt(request.getParameter("anum"));

                String query = "SELECT account_name, balance FROM account WHERE account_number = ?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setInt(1, num);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    String accountName = rs.getString("account_name");
                    int balance = rs.getInt("balance");

                    pw.print("<h3 align='center'>Account Details</h3>");
                    pw.print("<table align='center' border='1'>");
                    pw.print("<tr><th>Account Name</th><th>Balance</th></tr>");
                    pw.print("<tr><td>" + accountName + "</td><td>" + balance + "</td></tr>");
                    pw.print("</table>");
                } else {
                    pw.print("<h3 align='center'>No account found with Account Number: " + num + "</h3>");
                }

                RequestDispatcher rd = request.getRequestDispatcher("/user.html");
                rd.include(request, response);

            } catch (Exception e) {
                pw.print("<h3 align='center'>Error retrieving account details. Please try again.</h3>");
                request.getRequestDispatcher("/user.html").include(request, response);
            } finally {
                if (con != null) {
                    try {
                        con.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            pw.print("<h3>You logged out from the previous session - Please log in again</h3>");
            request.getRequestDispatcher("login.html").include(request, response);
        }
    }
}
