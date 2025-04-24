package app.dbConnect;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DBManager {

    private static String URL;
    private static String USER;
    private static String PASS;

    static {
        try {
            Properties props = new Properties();
            InputStream input = DBManager.class.getClassLoader().getResourceAsStream("db.properties");
            if (input == null) {
                throw new RuntimeException("db.properties 파일을 찾을 수 없습니다.");
            }
            props.load(input);

            URL = props.getProperty("db.url");
            USER = props.getProperty("db.user");
            PASS = props.getProperty("db.password");

            Class.forName("org.mariadb.jdbc.Driver");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static boolean login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println("DB 연결 성공");
            }
        } catch (SQLException e) {
            System.out.println("DB 연결 실패: " + e.getMessage());
        }
    }
}