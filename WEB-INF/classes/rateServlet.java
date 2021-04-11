// To save as "ebookshop\WEB-INF\classes\QueryServlet.java".
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/rateservlet")   // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class rateServlet extends HttpServlet {

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
      out.println("<head> <meta charset=\"utf-8\" /> <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\" /> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"> <title></title> <link href='https://fonts.googleapis.com/css?family=Lato:300,400|Montserrat:700' rel='stylesheet' type='text/css'> <style> @import url(//cdnjs.cloudflare.com/ajax/libs/normalize/3.0.1/normalize.min.css); @import url(//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css); </style> <link rel=\"stylesheet\" href=\"https://2-22-4-dot-lead-pages.appspot.com/static/lp918/min/default_thank_you.css\"> <script src=\"https://2-22-4-dot-lead-pages.appspot.com/static/lp918/min/jquery-1.9.1.min.js\"></script> <script src=\"https://2-22-4-dot-lead-pages.appspot.com/static/lp918/min/html5shiv.js\"></script> </head>");
      out.println("<body><center><a href=\"main.html\"><img src=\"myshop.jpeg\" style=\"width:7%\"></a></center><header class=\"site-header\" id=\"header\"> <h2 class=\"site-header__title\" data-lead-id=\"site-header-title\">THANK YOU!</h2> </header> <div class=\"main-content\"> <i class=\"fa fa-check main-content__checkmark\" id=\"checkmark\"></i> <p class=\"main-content__body\" data-lead-id=\"main-content-body\">Thank you for rating the item.</p>");

      try (
         // Step 1: Allocate a database 'Connection' object
         Connection conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/myshop?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
               "myuser", "xxxx");   // For MySQL
               // The format is: "jdbc:mysql://hostname:port/databaseName", "username", "password"

         // Step 2: Allocate a 'Statement' object in the Connection
         Statement stmt = conn.createStatement();
      ) {// Step 3 & 4: Execute a SQL SELECT query and Process the query result
         // Retrieve the books' id. Can order more than one books.
         String rate = request.getParameter("rating");
         String itemid = request.getParameter("itemid");
         if (rate != null) {
         // Returns an array of Strings
         
         String sqlStr2 = "SELECT * from rating where id="+ itemid;
         ResultSet rset2 = stmt.executeQuery(sqlStr2);
            while(rset2.next()){
            out.println("<p class=\"main-content__body\" data-lead-id=\"main-content-body\"> Previous Rating: " + rset2.getString("rating") +"<p>");
            out.println("<p class=\"main-content__body\" data-lead-id=\"main-content-body\"> Previous Review Count: " + rset2.getString("voted_user") +"<p>");
            }
            String sqlStr = "update rating set rating =( rating*voted_user +"+ rate+")/(voted_user+1), voted_user = voted_user+1 where id =" +itemid;
         int rset = stmt.executeUpdate(sqlStr);
         sqlStr2 = "SELECT * from rating where id="+ itemid;
         rset2 = stmt.executeQuery(sqlStr2);
         while(rset2.next()){
               
            out.println("<p class=\"main-content__body\" data-lead-id=\"main-content-body\"> Updated Rating: " + rset2.getString("rating") +"<p>");
            out.println("<p class=\"main-content__body\" data-lead-id=\"main-content-body\">Updated Review Count: " + rset2.getString("voted_user") +"<p>");
            }
            
         } else { // No book selected
            out.println("<h3>Please go back and Rate...</h3>");
         }
      }catch(Exception ex) {
         out.println("<p>Error: Please type in a number between 0 and 5</p>");
         ex.printStackTrace();
      }   // Step 5: Close conn and stmt - Done automatically by try-with-resources (JDK 7)
 
      out.println("</body></html>");
      out.close();
   }
}