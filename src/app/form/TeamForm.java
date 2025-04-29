package app.form;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import app.dbConnect.TeamDB;
import app.frame.MainFrame;

/*
 * 생성자 : 신인철
 * 생성일 : 25.04.24
 * 파일명 : TeamForm.java
 * 설명 : JSplitPane 적용 및 화면 분할 구현 (팀원 목록과 버튼 패널)
 */

public class TeamForm extends JPanel {

    private JPanel memberListPanel;
    // 멤버 버튼 리스트
    private List<JButton> memberButtons = new ArrayList<>();
    // 점수 라벨 리스트
    private List<JLabel> memberScores = new ArrayList<>();
    private int teamNum;
    private JLabel teamMoneyLabel;

    /**
     * @param teamName 표시 및 관리할 팀의 이름
     */
    public TeamForm(String teamName) {

        this.teamNum = Integer.parseInt(teamName.replaceAll("\\D", ""));
        setLayout(new BorderLayout());

        // 상단 영역
        JLabel label = new JLabel("현재 팀: " + teamName);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setOpaque(true);
        label.setBackground(new Color(220, 220, 220));
        label.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        add(label, BorderLayout.NORTH);

        // 왼쪽 영역: 팀원 목록 패널 생성
        memberListPanel = new JPanel();
        memberListPanel.setLayout(new BoxLayout(memberListPanel, BoxLayout.Y_AXIS));
        TeamDB.loadTeamMembersFromDB(teamNum, memberListPanel, memberButtons);
        MainFrame.getInstance().updateTeamScore(teamNum);

        // 오른쪽 영역: 팀원 추가 및 삭제 버튼 패널 생성
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        JButton addButton = new JButton("팀원 추가");
        JButton removeButton = new JButton("팀원 삭제");

        addButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "추가할 팀원 이름을 입력하세요:");
            if (input != null && !input.trim().isEmpty()) {
                TeamDB.saveToDB(teamNum, input.trim(), memberListPanel, memberButtons);
                MainFrame.getInstance().updateTeamScore(teamNum);
                memberListPanel.revalidate();
                memberListPanel.repaint();
            }
        });

        removeButton.addActionListener(e -> {
            removeLastMemberField();
            MainFrame.getInstance().updateTeamScore(teamNum);
        });

        rightPanel.add(addButton);
        rightPanel.add(removeButton);

        // 총점, 귤포인트 금액환산
        // 총점
        teamMoneyLabel = new JLabel();
        teamMoneyLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        teamMoneyLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        rightPanel.add(teamMoneyLabel);

        // 왼쪽 팀원 목록을 스크롤 가능한 패널로 감싸기
        JScrollPane scrollPane = new JScrollPane(memberListPanel);
        // JSplitPane을 사용하여 왼쪽(팀원 목록)과 오른쪽(버튼 패널)을 50:50으로 분할
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, rightPanel);
        splitPane.setResizeWeight(0.5); // 50:50 비율
        splitPane.setEnabled(false); // 드래그 금지
        splitPane.setDividerSize(0); // 분할선 자체를 안 보이게
        // 완성된 분할 패널을 화면 중앙에 추가
        add(splitPane, BorderLayout.CENTER);

    }

    /**
     * 
     * @param teamNum  팀 번호
     * @param newScore 갱신한 총점수
     */
    public void updateScoreLabel(int teamNum, int newScore) {
        teamMoneyLabel
                .setText("<html>" + teamNum + "팀의 총점 : " + newScore + "<br>귤포인트는 : " + (newScore * 300) + "</html>");
        teamMoneyLabel.revalidate();
        teamMoneyLabel.repaint();
    }

    private void removeLastMemberField() {

        if (!memberButtons.isEmpty()) {
            JButton lastButton = memberButtons.remove(memberButtons.size() - 1);
            memberListPanel.remove(lastButton);
            TeamDB.deleteFromDB(teamNum, lastButton.getText(), memberListPanel, memberButtons);
            memberListPanel.revalidate();
            memberListPanel.repaint();

        }
    }

}
