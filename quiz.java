import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet(urlPatterns = {"/action"})
public class action extends HttpServlet {
String message,Seat_no,Name,ans1,ans2,ans3,ans4,ans5;
int Total=0;
java.sql.Connection connect;
java.sql.Statement stmt=null;
java.sql.ResultSet rs=null;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
           try (PrintWriter out = response.getWriter()) {                   
               String url="jdbc:derby://localhost:1527/student;create=true;user=student;password=student";
               try {       
                       Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
                       connect=DriverManager.getConnection(url,"student","student");    
                message="Thank you for participating in online Exam";
                Seat_no=request.getParameter("Seat_no");
                Name=request.getParameter("Name");
                ans1=request.getParameter("group1");
                ans2=request.getParameter("group2");
                ans3=request.getParameter("group3");
                ans4=request.getParameter("group4");
                ans5=request.getParameter("group5");
                if(ans1.equals("True"))
                Total+=1;
                if(ans2.equals("True"))
                Total+=1;
                if(ans3.equals("False"))
                Total+=1;
                if(ans4.equals("True"))
                Total+=1;
                if(ans5.equals("True"))
                Total+=1;
                stmt=connect.createStatement();
   String query="INSERT INTO                      STUDENT.student(seatno,name,total)VALUES('"+Seat_no+"','"+Name+"',"+Total+")";
                stmt.executeUpdate(query);
                stmt.executeUpdate(query);
                stmt.close();
                response.setContentType("text/html");
                out.println("<html>");
                out.println("<head>");
                out.println("</head>");
                out.println("<body bgcolor=gray>");
                out.println("<center>");
                out.println("<h1>"+message+"</h1>\n");
                out.println("<h3>Yours results stored in our database</h3>");
                out.print("<br><br>");
                out.println("<b>"+"Participants and their Marks"+"</b>");
                out.println("<table border=5>");
                java.sql.Statement stmt2=connect.createStatement();
                String query1="SELECT * FROM student";
                rs=stmt2.executeQuery(query1);
                out.println("<th>"+"Seat_no"+"</th>");
                out.println("<th>"+"Name"+"</th>");
                out.println("<th>"+"Marks"+"</th>");
                while(rs.next())
                {
                out.println("<tr>");
                out.print("<td>"+rs.getString(1)+"</td>");
                out.print("<td>"+rs.getString(2)+"</td>");
                out.print("<td>"+rs.getString(3)+"</td>");
                out.println("</tr>");
                }
                out.println("</table>");
                if(rs!=null)
                rs.close();
                if(stmt!=null)
                stmt.close();
                if(connect!=null)
                connect.close();
                out.println("</center>");
                out.println("</body></html>");
                Total=0;
               } catch (InstantiationException ex) {
                       Logger.getLogger(action.class.getName()).log(Level.SEVERE, null, ex);
                   } catch (IllegalAccessException ex) {
                       Logger.getLogger(action.class.getName()).log(Level.SEVERE, null, ex);
                   }
               catch (ClassNotFoundException ex) {
                   Logger.getLogger(action.class.getName()).log(Level.SEVERE, null, ex);
               }
               catch (SQLException ex) {
                       Logger.getLogger(action.class.getName()).log(Level.SEVERE, null, ex);
                   }
        }
    }
}




