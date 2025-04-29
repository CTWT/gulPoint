package app.dbConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import app.frame.MainFrame;

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
                        .prepareStatement("SELECT teamUserName FROM team WHERE teamNum = ? ORDER BY teamSeq")) {
            pstmt.setInt(1, teamNum);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("teamUserName");
                JButton btn = new JButton(name);

                int scoreVal = 0;
                try {
                    scoreVal = ScoreDB.loadTeamUserNameScoreDB(teamNum, name);
                } catch (Exception e) {

                }
                JLabel scoreLabel = null;

                for (var comp : memberListPanel.getComponents()) {
                    if (comp instanceof JLabel lbl && name.equals(lbl.getName())) {
                        scoreLabel = lbl;
                        break;
                    }
                }
                if (scoreLabel == null) {
                    scoreLabel = new JLabel("점수 : " + scoreVal);
                    scoreLabel.setName(name);
                } else {
                    scoreLabel.setText("점수 : " + scoreVal);
                }
                btn.addActionListener(e -> {
                    System.out.println("점수입력이벤트 시작");
                    String input = JOptionPane.showInputDialog("점수를 입력해주세요(정수만!!!)");
                    if (input != null && !input.trim().isEmpty()) {
                        try {
                            int score = Integer.parseInt(input.trim());
                            System.out.println("입력된 점수: " + score);
                            ScoreDB.saveScoreDB(teamNum, name, score);
                            int updatedScoreVal = ScoreDB.loadTeamUserNameScoreDB(teamNum, name);
                            JLabel foundLabel = null;
                            for (var comp : memberListPanel.getComponents()) {
                                if (comp instanceof JLabel lbl && name.equals(lbl.getName())) {
                                    foundLabel = lbl;
                                    break;
                                }
                            }
                            if (foundLabel != null) {
                                foundLabel.setText("점수 : " + updatedScoreVal);
                            } else {

                                JLabel newLabel = new JLabel("점수 : " + updatedScoreVal);
                                newLabel.setName(name);
                                memberListPanel.add(newLabel);
                                memberListPanel.add(Box.createVerticalStrut(10));
                            }
                            MainFrame.getInstance().updateTeamScore(teamNum);
                            memberListPanel.revalidate();
                            memberListPanel.repaint();
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "정수만 입력 가능합니다!", "입력 오류", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
                memberButtons.add(btn);
                memberListPanel.add(btn);
                memberListPanel.add(scoreLabel);
                memberListPanel.add(Box.createVerticalStrut(10));
            }
            memberListPanel.revalidate();
            memberListPanel.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param teamNum         조회할 팀 번호
     * @param memberListPanel 팀원 JButton들을 담는 JPanel
     * @param memberButtons   팀원 JButton 리스트
     */
    // public static void viewAllTeamMembersFromDB(int teamNum, JPanel
    // memberListPanel, List<JButton> memberButtons) {
    // try (Connection conn = DBManager.getConnection();
    // PreparedStatement pstmt = conn
    // .prepareStatement("SELECT teamUserName FROM team WHERE teamNum = ?")) {
    // pstmt.setInt(1, teamNum);
    // ResultSet rs = pstmt.executeQuery();
    // while (rs.next()) {
    // String name = rs.getString("teamUserName");
    // JButton btn = new JButton(name);
    // btn.addActionListener(e -> {
    // String input = JOptionPane.showInputDialog("점수를 입력해주세요(정수만!!!)");
    // if (input != null && !input.trim().isEmpty()) {
    // try {
    // int score = Integer.parseInt(input.trim());
    // System.out.println("입력된 점수: " + score);
    // ScoreDB.saveScoreDB(teamNum, name, score);
    // } catch (NumberFormatException ex) {
    // JOptionPane.showMessageDialog(null, "정수만 입력 가능합니다!", "입력 오류",
    // JOptionPane.ERROR_MESSAGE);
    // }
    // }
    // });

    // memberButtons.add(btn);
    // memberListPanel.add(btn);
    // memberListPanel.add(Box.createVerticalStrut(10));
    // }
    // memberListPanel.revalidate();
    // memberListPanel.repaint();
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }

    /**
     * @param teamNum         팀 번호
     * @param name            추가할 팀원 이름
     * @param memberListPanel 팀원 JButton들을 담는 JPanel
     * @param memberButtons   팀원 JButton 리스트
     */
    public static void saveToDB(int teamNum, String name, JPanel memberListPanel, List<JButton> memberButtons) {
        try (Connection conn = DBManager.getConnection()) {
            int nextSeq = 1;
            try (PreparedStatement seqStmt = conn
                    .prepareStatement("SELECT IFNULL(MAX(teamSeq), 0) + 1 FROM team WHERE teamNum = ?")) {
                seqStmt.setInt(1, teamNum);
                ResultSet rsSeq = seqStmt.executeQuery();
                if (rsSeq.next()) {
                    nextSeq = rsSeq.getInt(1);
                }
            }
            try (PreparedStatement pstmt = conn
                    .prepareStatement("INSERT INTO team (teamNum, teamUserName, teamSeq) VALUES (?, ?, ?)")) {
                pstmt.setInt(1, teamNum);
                pstmt.setString(2, name);
                pstmt.setInt(3, nextSeq);
                pstmt.executeUpdate();
            }

            JButton btn = new JButton(name);
            JLabel scoreLabel = new JLabel("점수 : 0");
            scoreLabel.setName(name);

            btn.addActionListener(e -> {
                String input = JOptionPane.showInputDialog("점수를 입력해주세요(정수만!!!)");
                if (input != null && !input.trim().isEmpty()) {
                    try {
                        int score = Integer.parseInt(input.trim());
                        System.out.println("입력된 점수: " + score);
                        ScoreDB.saveScoreDB(teamNum, name, score);
                        int scoreVal = ScoreDB.loadTeamUserNameScoreDB(teamNum, name);
                        scoreLabel.setText("점수 : " + scoreVal);
                        MainFrame.getInstance().updateTeamScore(teamNum);
                        memberListPanel.revalidate();
                        memberListPanel.repaint();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "정수만 입력 가능합니다!", "입력 오류", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            memberButtons.add(btn);
            memberListPanel.add(btn);
            memberListPanel.add(scoreLabel);

            memberListPanel.revalidate();
            memberListPanel.repaint();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "중복된 팀원 명 입니다.", "팀원 명 중복 에러", JOptionPane.ERROR_MESSAGE);
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

            // 팀원 삭제시 팀원이 보유한 점수도 전체 삭제 처리
            ScoreDB.deleteScoreDB(teamNum, name);

            JButton toRemoveButton = null;
            JLabel toRemoveLabel = null;

            for (var comp : memberListPanel.getComponents()) {
                if (comp instanceof JButton btn && btn.getText().equals(name)) {
                    toRemoveButton = btn;
                } else if (comp instanceof JLabel lbl && name.equals(lbl.getName())) {
                    toRemoveLabel = lbl;
                }
            }

            if (toRemoveButton != null) {
                memberListPanel.remove(toRemoveButton);
                memberButtons.remove(toRemoveButton);
            }
            if (toRemoveLabel != null) {
                memberListPanel.remove(toRemoveLabel);
            }
            MainFrame.getInstance().updateTeamScore(teamNum);
            memberListPanel.revalidate();
            memberListPanel.repaint();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
