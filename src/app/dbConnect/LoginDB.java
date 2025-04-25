package app.dbConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * 생성자 : 신인철
 * 생성일 : 25.04.25
 * 파일명 : LoginDB.java
 * 수정자 : 
 * 수정일 :
 * 설명 : 로그인 SQL
 */

public class LoginDB {

    /**
     * @param username 계정정보
     * @param password 계정비밀번호
     * @return
     */
    public static boolean login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DBManager.getConnection();
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
}
