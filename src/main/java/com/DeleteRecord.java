package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/DeleteRecord")
public class DeleteRecord extends HttpServlet {
private static final long serialVersionUID = 1L;
protected void doPost(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException {
PrintWriter pw = response.getWriter();
response.setContentType("text/html");
HttpSession session = request.getSession(false);
if (session != null) {
String user = (String) session.getAttribute("name");
pw.print("<h1 align='center'>Welcome, " + user + " Continue with your transactions</h1>");
Connection con = null;
try {
con = DBConnection.get();
int num = Integer.parseInt(request.getParameter("anum"));
String query = "delete from account where anum=?";
PreparedStatement ps = con.prepareStatement(query);
ps.setInt(1, num);
int count = ps.executeUpdate();
if (count > 0) {
pw.print("<h3 align='center'>Account Deleted Successfully</h3>");
RequestDispatcher rd =request.getRequestDispatcher("/user.html");
rd.include(request, response);
} else {
pw.print("<h3 align='center'>No such Account to Delete - Try Again</h3>");
request.getRequestDispatcher("/user.html").include(request,
response);
}
} catch (Exception e) {
pw.print("<h3 align='center'>No such Account to Delete - Try Again</h3>");
request.getRequestDispatcher("/user.html").include(request,
response);
} finally {
if (con != null) {
try {
con.close();
} catch (SQLException e) {
}
}
}
} else {
pw.print("<h3>You logged out from previous Session - Please Login</h3>");
request.getRequestDispatcher("login.html").include(request, response);
}
}
}