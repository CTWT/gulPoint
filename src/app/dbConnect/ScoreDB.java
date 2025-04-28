package app.dbConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * 생성자 : 신인철
 * 생성일 : 25.04.25
 * 파일명 : ScoreDB.java
 * 수정자 : 
 * 수정일 :
 * 설명 : 점수 조회를 위한 SQL
 */

public class ScoreDB {

    // public static void loadTeamScoreDB(int teamNum, JPanel memberScorePanel,
    // List<JLabel> memberScore) {
    // try (Connection conn = DBManager.getConnection();
    // PreparedStatement pstmt = conn.prepareStatement("SELECT sum(score) FROM score
    // WHERE teamNum = ?")) {
    // pstmt.setInt(1, teamNum);
    // pstmt.executeQuery();
    // } catch (Exception e) {
    // e.printStackTrace();
    // }

    // }

    /**
     * 
     * @param teamNum      조회할 팀번호
     * @param teamUserName 조회할 팀원 명
     * @return
     */
    public static int loadTeamUserNameScoreDB(int teamNum, String teamUserName) {
        int score = 0;
        try (Connection conn = DBManager.getConnection();
                PreparedStatement pstmt = conn
                        .prepareStatement("SELECT sum(score) FROM score WHERE teamNum = ? AND teamUserName = ?")) {
            pstmt.setInt(1, teamNum);
            pstmt.setString(2, teamUserName);
            var rs = pstmt.executeQuery();
            if (rs.next()) {
                // 컬럼을 한개만 가져오기때문에 그 컬럼의 값을 가져옴
                score = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return score;
    }

    /**
     * 
     * @param teamNum      팀번호
     * @param teamUserName 추가될 팀원이름
     * @param score        점수(정수형)
     */
    public static void saveScoreDB(int teamNum, String teamUserName, int score) {
        try (Connection conn = DBManager.getConnection();
                PreparedStatement pstmt = conn
                        .prepareStatement("INSERT INTO score(teamNum, teamUserName, score) VALUES (?, ?, ?)")) {
            pstmt.setInt(1, teamNum);
            pstmt.setString(2, teamUserName);
            pstmt.setInt(3, score);
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param teamNum      팀 번호
     * @param teamUserName 삭제할 팀원 이름
     */
    public static void deleteScoreDB(int teamNum, String teamUserName) {

        try (Connection conn = DBManager.getConnection();
                PreparedStatement pstmt = conn
                        .prepareStatement("DELETE FROM SCORE WHERE teamNum = ? AND teamUserName = ?")) {
            pstmt.setInt(1, teamNum);
            pstmt.setString(2, teamUserName);
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
