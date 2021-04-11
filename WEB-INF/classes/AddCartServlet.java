// To save as "ebookshop\WEB-INF\classes\QueryServlet.java".
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/addcart")   // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class AddCartServlet extends HttpServlet {

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
      out.println("<head><title>Query Response</title><link rel=\"stylesheet\" href=\"response.css?=v1.1\" </head>");
      
      out.println("<body><center>");

      try (
         // Step 1: Allocate a database 'Connection' object
         Connection conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/myshop?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
               "myuser", "xxxx");   // For MySQL
               // The format is: "jdbc:mysql://hostname:port/databaseName", "username", "password"

         // Step 2: Allocate a 'Statement' object in the Connection
         Statement stmt = conn.createStatement();
      ) {
         // Step 3: Execute a SQL SELECT query
                  // Step 3: Execute a SQL SELECT query
         String[] id = request.getParameterValues("id");
         // Returns an array of Strings
         String sqlStr = "SELECT * FROM items WHERE id =" + id[0] +";";
         out.println("<h3></h3>");
         ResultSet rset = stmt.executeQuery(sqlStr);  // Send the query to the server

         // Step 4: Process the query result
         // Print the <form> start tag
         out.println("<form method='get' action='orderservlet'>");
         out.println("<a href=\"main.html\"><img src=\"myshop.jpeg\" style=\"width:7%\"></a>" );

         // For each row in ResultSet, print one checkbox inside the <form>
         String itemid;
         while(rset.next()) {

            out.println("<h2>"+ rset.getString("name") +"</h2><br>" );
            out.println("<img src=" + rset.getString("imgurl")+" alt=\"yo\" class=\"images\"><br>" + "<h3>$" +rset.getString("price")+"</h3>");
            itemid = rset.getString("id");

         }
         out.println("<h3>Please pay using PAYLATER/PAYSOMEONE/PAYPLEASE to 91341234</h3>");
         out.println("Item ID (DO NOT CHANGE): <input type=\"text\" name=\"itemid\" value=\""+id[0]+"\" >") ;
         out.println("<p>Enter your unique ID: <input type='text' name='id' /></p>");

 
         // Print the submit button and </form> end-tag
         out.println("<input type='submit' value='ORDER' />");
         out.println("</form>");
         int count = 0;
         while(rset.next()) {
            // Print a paragraph <p>...</p> for each record
            out.println("<p>" + rset.getString("author")
                  + ", " + rset.getString("title")
                  + ", $" + rset.getDouble("price") + "</p>");
            count++;
         }
      } catch(Exception ex) {
         out.println("<p>Error: " + ex.getMessage() + "</p>");
         out.println("<p>Check Tomcat console for details.</p>");
         ex.printStackTrace();
      }  // Step 5: Close conn and stmt - Done automatically by try-with-resources (JDK 7)
 
      out.println("</center></body></html>");
      out.close();
   }
}