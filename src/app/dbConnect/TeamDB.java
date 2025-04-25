package app.dbConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

/*
 * 생성자 : 신인철
 * 생성일 : 25.04.25
 * 파일명 : TeamDB.java
 * 수정자 : 
 * 수정일 :
 * 설명 : 팀정보 조회 SQL
 */

public class TeamDB {

    /**
     * @param teamNum         조회할 팀 번호
     * @param memberListPanel 팀원 JButton들을 담는 JPanel
     * @param memberButtons   팀원 JButton 리스트
     */
    public static void loadTeamMembersFromDB(int teamNum, JPanel memberListPanel, List<JButton> memberButtons) {
        memberListPanel.removeAll();
        memberButtons.clear();
        try (Connection conn = DBManager.getConnection();
                PreparedStatement pstmt = conn
                        .prepareStatement("SELECT teamUserName FROM team WHERE teamNum = ?")) {
            pstmt.setInt(1, teamNum);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String name = rs.getString("teamUserName");
                JButton btn = new JButton(name);
                btn.addActionListener(e -> {
                    System.out.println("RIdkdkdkdk");
                });
                memberButtons.add(btn);
                memberListPanel.add(btn);
                memberListPanel.add(Box.createVerticalStrut(10));
            }
            memberListPanel.revalidate();
            memberListPanel.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param teamNum         팀 번호
     * @param name            추가할 팀원 이름
     * @param memberListPanel 팀원 JButton들을 담는 JPanel
     * @param memberButtons   팀원 JButton 리스트
     */
    public static void saveToDB(int teamNum, String name, JPanel memberListPanel, List<JButton> memberButtons) {
        try (Connection conn = DBManager.getConnection();
                PreparedStatement pstmt = conn
                        .prepareStatement("INSERT INTO team (teamNum, teamUserName) VALUES (?, ?)")) {
            pstmt.setInt(1, teamNum);
            pstmt.setString(2, name);
            pstmt.executeUpdate();

            JButton btn = new JButton(name);
            memberButtons.add(btn);
            memberListPanel.add(btn);

            memberListPanel.revalidate();
            memberListPanel.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param teamNum         팀 번호
     * @param name            삭제할 팀원 이름
     * @param memberListPanel 팀원 JButton들을 담는 JPanel
     * @param memberButtons   팀원 JButton 리스트
     */
    public static void deleteFromDB(int teamNum, String name, JPanel memberListPanel, List<JButton> memberButtons) {
        try (Connection conn = DBManager.getConnection();
                PreparedStatement pstmt = conn
                        .prepareStatement("DELETE FROM team WHERE teamNum = ? AND teamUserName = ? LIMIT 1")) {
            pstmt.setInt(1, teamNum);
            pstmt.setString(2, name);
            pstmt.executeUpdate();

            JButton toRemove = null;
            for (JButton btn : memberButtons) {
                if (btn.getText().equals(name)) {
                    toRemove = btn;
                    break;
                }
            }
            if (toRemove != null) {
                memberButtons.remove(toRemove);
                memberListPanel.remove(toRemove);
                memberListPanel.revalidate();
                memberListPanel.repaint();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
