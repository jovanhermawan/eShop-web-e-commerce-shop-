// To save as "ebookshop\WEB-INF\classes\QueryServlet.java".
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/question")   // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class questionServlet extends HttpServlet {

   // The doGet() runs once per HTTP GET request to this servlet.
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
      // Set the MIME type for the response message
      response.setContentType("text/html");
      // Get a output writer to write the response message into the network socket
      PrintWriter out = response.getWriter();
      // Print an HTML page as the output of the query

      out.println("<html>");
       
      
      
      try (
         // Step 1: Allocate a database 'Connection' object
         Connection conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/clicker?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
               "myuser", "xxxx");   // For MySQL
               // The format is: "jdbc:mysql://hostname:port/databaseName", "username", "password"

         // Step 2: Allocate a 'Statement' object in the Connection
         Statement stmt = conn.createStatement();
      ) {// Step 3 & 4: Execute a SQL SELECT query and Process the query result
         // Retrieve the books' id. Can order more than one books.
         String id = request.getParameter("id");
         String q = request.getParameter("q");
         String ca = request.getParameter("ca");
         String cb = request.getParameter("cb");
         String cc = request.getParameter("cc");
         String cd = request.getParameter("cd");
         
         if (id != null && q != null && ca != null && cb != null) {
            String sqlStr;
            int count;
            out.println("<head> <meta charset=\"utf-8\" /> <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\" /> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"> <title></title> <link href='https://fonts.googleapis.com/css?family=Lato:300,400|Montserrat:700' rel='stylesheet' type='text/css'> <style> @import url(//cdnjs.cloudflare.com/ajax/libs/normalize/3.0.1/normalize.min.css); @import url(//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css); </style> <link rel=\"stylesheet\" href=\"https://2-22-4-dot-lead-pages.appspot.com/static/lp918/min/default_thank_you.css\"> <script src=\"https://2-22-4-dot-lead-pages.appspot.com/static/lp918/min/jquery-1.9.1.min.js\"></script> <script src=\"https://2-22-4-dot-lead-pages.appspot.com/static/lp918/min/html5shiv.js\"></script> </head>");
            out.println("<body><center><a href=\"main.html\"><img src=\"myshop.jpeg\" style=\"width:7%\"></a></center><header class=\"site-header\" id=\"header\"> <h2 class=\"site-header__title\" data-lead-id=\"site-header-title\">THANK YOU!</h2> </header> <div class=\"main-content\"> <i class=\"fa fa-check main-content__checkmark\" id=\"checkmark\"></i> <p class=\"main-content__body\" data-lead-id=\"main-content-body\">Successfully Registered</p>");
            sqlStr = "INSERT INTO questions VALUES ("+id+","+q+","
                     + ca + ",'" + cb + "','" + cc + "'," + cd + ")";
            count = stmt.executeUpdate(sqlStr);
            // Process each of the books

            out.println("<a class=\"main-content__body\" data-lead-id=\"main-content-body\" href=\"main.html\"><input type='submit' value='Continue Shopping' /></a>");
         } else { // No book selected
            out.println("<h3 class=\"main-content__body\" data-lead-id=\"main-content-body\">.Please go back and fill in the form...</h3>");
         }
      }catch(Exception ex) {
         out.println("<p>Error: " + ex.getMessage() + "</p>");
         out.println("<p>Please go back and fill in the form completely...<p><p>Please Type in Numbers only in your ID and Phone Number</p>");
         ex.printStackTrace();
      }   // Step 5: Close conn and stmt - Done automatically by try-with-resources (JDK 7)
 
      out.println("</body></html>");
      out.close();
   }
}