import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class BmiTrackerServlet extends HttpServlet {
    
    // Aapke MySQL Database ki details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/bmi_db";
    private static final String DB_USER = "root"; 
    private static final String DB_PASS = "root1234"; // Aapka naya password

    // HTML form se data lene ke liye (POST request)
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        double weight = Double.parseDouble(request.getParameter("weight"));
        double height = Double.parseDouble(request.getParameter("height"));
        double bmi = Double.parseDouble(request.getParameter("bmi"));
        String category = request.getParameter("category");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            
            String sql = "INSERT INTO bmi_records (weight, height, bmi_value, category) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, weight);
            pstmt.setDouble(2, height);
            pstmt.setDouble(3, bmi);
            pstmt.setString(4, category);
            
            pstmt.executeUpdate();
            
            pstmt.close();
            conn.close();
            
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Success");
            
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    // Database se data wapas dikhane ke liye (GET request)
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<BmiRecord> recordList = new ArrayList<>();
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM bmi_records ORDER BY record_date DESC");
            
            while(rs.next()) {
                recordList.add(new BmiRecord(
                    rs.getDouble("weight"),
                    rs.getDouble("height"),
                    rs.getDouble("bmi_value"),
                    rs.getString("category"),
                    rs.getString("record_date")
                ));
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        request.setAttribute("historyData", recordList);
        request.getRequestDispatcher("history.jsp").forward(request, response);
    }
}