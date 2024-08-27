package com;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UpdateRecord")
public class UpdateRecord extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve parameters from the request
        String username = request.getParameter("uname");
        String newPassword = request.getParameter("newPwd");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
           
            boolean isUpdated = updateRecordInDatabase(username, newPassword);

            // Output response to the user
            out.println("<html><body>");
            if (isUpdated) {
                out.println("<h3>Record for user '" + username + "' has been successfully updated.</h3>");
                
                
                
            } else {
                out.println("<h3>Failed to update record for user '" + username + "'. Please try again.</h3>");
                
            }
            out.println("<a href='user.html'>Go Back to user</a>");
            out.println("</body></html>");
        } finally {
            out.close();
        }
    }

   
    private boolean updateRecordInDatabase(String username, String newPassword) {
       
        return true;
    }
}
