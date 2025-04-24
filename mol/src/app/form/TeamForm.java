package app.form;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import app.dbConnect.DBManager;

public class TeamForm extends JPanel {

    private String teamName;
    private JPanel memberListPanel;
    private JLabel emptyMessageLabel = new JLabel("저장된 팀원이 없습니다.", SwingConstants.CENTER);
    private List<JTextField> memberFields = new ArrayList<>();

    public TeamForm(String teamName) {
        this.teamName = teamName;
        setLayout(new BorderLayout());

        // 상단 라벨
        JLabel titleLabel = new JLabel(teamName + " 팀원 목록", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        add(titleLabel, BorderLayout.NORTH);

        // 중앙 패널 (팀원 목록)
        memberListPanel = new JPanel();
        memberListPanel.setLayout(new BoxLayout(memberListPanel, BoxLayout.Y_AXIS));
        memberListPanel.setPreferredSize(new Dimension(800, 400));
        JScrollPane scrollPane = new JScrollPane(memberListPanel);
        add(scrollPane, BorderLayout.CENTER);

        // // DB에서 팀원 로드
        // loadTeamMembersFromDB();

        // // 하단 버튼
        // JButton addButton = new JButton("팀원 추가");
        // JButton removeButton = new JButton("팀원 삭제");

        // addButton.addActionListener(e -> {
        // String input = JOptionPane.showInputDialog(this, "추가할 팀원 이름을 입력하세요:");
        // if (input != null && !input.trim().isEmpty()) {
        // saveToDB(input.trim()); // 먼저 DB에 저장
        // addMemberField(input.trim()); // 저장 후 화면에 추가
        // memberListPanel.remove(emptyMessageLabel); // 메시지 제거
        // memberListPanel.revalidate();
        // memberListPanel.repaint();
        // }
        // });
        // removeButton.addActionListener(e -> removeLastMemberField());

        // JPanel buttonPanel = new JPanel();
        // buttonPanel.add(addButton);
        // buttonPanel.add(removeButton);

        // add(buttonPanel, BorderLayout.SOUTH);
        revalidate();
        repaint();
    }

    // private void loadTeamMembersFromDB() {
    // // 실제 DB에서 팀원 데이터를 읽어옴
    // List<String> teamMembers = getTeamMembersFromDB();
    // if (teamMembers.isEmpty()) {
    // // DB에 데이터가 없으면 메시지 표시
    // emptyMessageLabel.setFont(new Font("SansSerif", Font.ITALIC, 14));
    // emptyMessageLabel.setForeground(Color.GRAY);
    // memberListPanel.add(emptyMessageLabel); // 중앙에 표시
    // } else {
    // // 팀원이 있으면 화면에 추가
    // for (String member : teamMembers) {
    // addMemberField(member);
    // }
    // }
    // // UI 갱신
    // memberListPanel.revalidate();
    // memberListPanel.repaint();
    // }

    // private List<String> getTeamMembersFromDB() {
    // List<String> teamMembers = new ArrayList<>();
    // String sql = "SELECT teamUserName FROM team WHERE teamNum = ? ORDER BY
    // teamSeq";
    // try (Connection conn = DBManager.getConnection();
    // PreparedStatement pstmt = conn.prepareStatement(sql)) {
    // pstmt.setInt(1, Integer.parseInt(teamName.replaceAll("[^0-9]", ""))); //
    // teamName에서 숫자만 추출
    // ResultSet rs = pstmt.executeQuery();
    // while (rs.next()) {
    // teamMembers.add(rs.getString("teamUserName"));
    // }
    // } catch (SQLException e) {
    // e.printStackTrace();
    // }
    // memberListPanel.revalidate();
    // memberListPanel.repaint();
    // return teamMembers;
    // }

    // private void addMemberField(String name) {
    // JTextField field = new JTextField(20);
    // if (name != null) {
    // field.setText(name);
    // }
    // memberFields.add(field);
    // memberListPanel.add(field);
    // memberListPanel.revalidate();
    // memberListPanel.repaint();
    // }

    // private void removeLastMemberField() {
    // if (!memberFields.isEmpty()) {
    // JTextField last = memberFields.remove(memberFields.size() - 1);
    // memberListPanel.remove(last);
    // memberListPanel.revalidate();
    // memberListPanel.repaint();

    // // 팀원이 모두 삭제되면 메시지 표시
    // if (memberFields.isEmpty()) {
    // JOptionPane.showMessageDialog(this, "더 이상 삭제할 팀원이 없습니다.");
    // }

    // deleteLastMemberFromDB();
    // }
    // }

    // private void saveToDB(String teamMember) {
    // if (teamMember != null) {
    // String sql = "INSERT INTO team (teamNum, teamUserName, teamSeq) VALUES (?, ?,
    // ?)";
    // try (Connection conn = DBManager.getConnection();
    // PreparedStatement pstmt = conn.prepareStatement(sql)) {
    // pstmt.setInt(1, Integer.parseInt(teamName.replaceAll("[^0-9]", "")));
    // pstmt.setString(2, teamMember);
    // pstmt.setInt(3, memberFields.size() + 1);
    // pstmt.executeUpdate();
    // } catch (SQLException e) {
    // e.printStackTrace();
    // }
    // }
    // memberListPanel.revalidate();
    // memberListPanel.repaint();
    // }

    // private void deleteLastMemberFromDB() {
    // String sql = "DELETE FROM team WHERE teamNum = ? AND teamSeq = ?";
    // try (Connection conn = DBManager.getConnection();
    // PreparedStatement pstmt = conn.prepareStatement(sql)) {
    // pstmt.setInt(1, Integer.parseInt(teamName.replaceAll("[^0-9]", "")));
    // pstmt.setInt(2, memberFields.size() + 1); // 팀원 순서
    // pstmt.executeUpdate();
    // } catch (SQLException e) {
    // e.printStackTrace();
    // }
    // memberListPanel.revalidate();
    // memberListPanel.repaint();
    // }
}
