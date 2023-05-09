import java.sql.*;

public class Database {
    private static Connection con;

    //This method executes a query and returns the number of albums for the artist with name artistName
    public int getTitles(String artistName) {
        int titleNum = 0;
        //Implement this method
        try {
            String sql = "SELECT COUNT(*) title FROM album INNER JOIN artist ON artist.artistid = album.artistid WHERE artist.name = ? ";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.clearParameters();
            pstmt.setString(1, artistName);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            titleNum = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Prevent SQL injections!
        return titleNum;
    }

    public boolean establishDBConnection() {
        //Implement this method
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(Credentials.URL, Credentials.USERNAME, Credentials.PASSWORD);
            return con.isValid(5);
        } catch (SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //5 sec timeout
        return false;
    }
}