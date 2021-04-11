// To save as "ebookshop\WEB-INF\classes\QueryServlet.java".
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/pricefilter2")   // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class pricefilter2Servlet extends HttpServlet {

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
      out.println("<head><title>Query Response</title><link rel=\"stylesheet\" href=\"response.css?=v1.1\" </head>"
      +"<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\">");
      out.println("<body>");
      

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
            // Returns an array of Strings
         String sqlStr = "SELECT items.id, items.name, items.stock, items.price, items.type, items.imgurl, rating.rating, rating.voted_user FROM items, rating where stock > 0 and items.id = rating.id ORDER BY price DESC";
 // Echo for debugging
         ResultSet rset = stmt.executeQuery(sqlStr);  // Send the query to the server

         // Step 4: Process the query result
         // Print the <form> start tag
         
         
         out.println("<div id=\"sidebar\" class=\"sideBar-main\"> <form method=\"get\" action=\"action\"> <div class=\"filterDropdown-topPart\"> <input class=\"filterDropdown-prodName\" type=\"text\" name=\"varName\" placeholder=\"Harry Potter and The Half Blood Prince Novel\"/> <button type=\"submit\" class=\"filterDropdown-submitButton\" value=\"Search\"><i class=\"fa fa-search\"></i></button> <!--<input class=\"filterDropdown-submitButton\" type=\"submit\" value=\"Search\"/>--> </div> </form> </div> <!--Main--> <div id=\"main\" class=\"main\"> <center><a href=\"main.html\"><img src=\"myshop.jpeg\" style=\"width:10%\"></a>   </center> <div id=\"sortBar\" class=\"sortBar-main\"style=\"border-radius:10px; padding-left:auto; padding-right:auto\"> <center><span style=\"font-size:25\">Sort by : </span> <div class=\"sortBar-dropDown\"> <p class=\"sortBar-dropDown-title\">Name</p> <div class=\"sortBar-dropDown-list\"> <li><form method=\"get\" action=\"atoz\"><input class=\"sortBar-dropDown-content\" type=\"submit\" value=\"A to Z\"/></form></li> <li><form method=\"get\" action=\"ztoa\"><input class=\"sortBar-dropDown-content\" type=\"submit\" value=\"Z to A\"/></form></li> </div> </div> <div class=\"sortBar-dropDown\"> <p class=\"sortBar-dropDown-title\">Category</p> <div class=\"sortBar-dropDown-list\"> <li><form method=\"get\" action=\"stationeries\"><input class=\"sortBar-dropDown-content\" type=\"submit\" value=\"Stationeries\"/></form></li> <li><form method=\"get\" action=\"clothes\"><input class=\"sortBar-dropDown-content\" type=\"submit\" value=\"Clothes\"/></form></li> <li><form method=\"get\" action=\"music\"><input class=\"sortBar-dropDown-content\" type=\"submit\" value=\"Musical Instruments\"/></form></li> <li><form method=\"get\" action=\"electronics\"><input class=\"sortBar-dropDown-content\" type=\"submit\" value=\"Electronics\"/></form></li> <li><form method=\"get\" action=\"furniture\"><input class=\"sortBar-dropDown-content\" type=\"submit\" value=\"Furniture\"/></form></li> <li><form method=\"get\" action=\"books\"><input class=\"sortBar-dropDown-content\" type=\"submit\" value=\"Books\"/></form></li> <li><form method=\"get\" action=\"kitchen\"><input class=\"sortBar-dropDown-content\" type=\"submit\" value=\"Kitchen\"/></form></li> </div> </div> <div class=\"sortBar-dropDown\"> <p class=\"sortBar-dropDown-title\">Rating</p> <div class=\"sortBar-dropDown-list\"> <li><form method=\"get\" action=\"ratingasc\"> <input class=\"sortBar-dropDown-content\" type=\"submit\" value=\"Lowest to Highest\"/></form></li> <li><form method=\"get\" action=\"ratingdes\"><input class=\"sortBar-dropDown-content\" type=\"submit\" value=\"Highest to Lowest\"/></form></li> </div> </div> <div class=\"sortBar-dropDown\" > <p class=\"sortBar-dropDown-title\">Price</p> <div class=\"sortBar-dropDown-list\"> <li><form method=\"get\" action=\"pricefilter\"> <input class=\"sortBar-dropDown-content\" type=\"submit\" value=\"Lowest to Highest\"/></form></li> <li><form method=\"get\" action=\"pricefilter2\"><input class=\"sortBar-dropDown-content\" type=\"submit\" value=\"Highest to Lowest\"/></form></li> </div></center> </div> </div> <script type=\"text/javascript\" src=\"main.js?v=1.1\"></script>");
         out.println("<div class=\"row\">");
         // For each row in ResultSet, print one checkbox inside the <form>
         while(rset.next()) {
            
            out.println("<div class=\"column\">");
            out.println("<div class=\"product-card\">");
            out.println("<div style=\"height:430\"><img src="+rset.getString("items.imgurl")+ "></div>");
            out.println("<h3>" +rset.getString("items.name")+"</h3>");
            out.println("<p class=\"product-price\">$" +rset.getString("items.price")+"    <br>   Rating:"+rset.getString("rating.rating")+"/5.0("+rset.getString("rating.voted_user")+" Reviews)</p>");
            out.println("<form method=\"get\" action=\"addcart\">");
            out.println("<button type=\"submit\" name=\"id\" value="+rset.getString("items.id")+">Buy Now</button>");
            out.println("</form></div></div>");

         }
         sqlStr = "SELECT items.id, items.name, items.stock, items.price, items.type, items.imgurl, rating.rating, rating.voted_user FROM items, rating where stock <= 0 and items.id = rating.id ORDER BY price DESC";
 // Echo for debugging
         rset = stmt.executeQuery(sqlStr); 
         while(rset.next()) {
            
            out.println("<div class=\"column\">");
            out.println("<div class=\"product-card\">");
            out.println("<div style=\"height:430\"><img src="+rset.getString("items.imgurl")+ "></div>");
            out.println("<h3>" +rset.getString("items.name")+"</h3>");
            out.println("<p class=\"product-price\">$" +rset.getString("items.price")+"    <br>   Rating:"+rset.getString("rating.rating")+"/5.0("+rset.getString("rating.voted_user")+" Reviews)</p>");
            out.println("<form method=\"get\" action=\"\">");
            out.println("<button type=\"submit\" name=\"id\" style=\"background-color:grey\" value="+rset.getString("items.id")+">OUT OF STOCK</button>");
            out.println("</form></div></div>");

         }
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
      out.println("<script type=\"text/javascript\" src=\"main.js?=v1.1\"></script>");
      out.println("</body></html>");
      out.close();
   }
}