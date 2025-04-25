package app.form;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import app.dbConnect.TeamDB;

/*
 * 생성자 : 신인철
 * 생성일 : 25.04.24
 * 파일명 : TeamForm.java
 * 수정자 : 
 * 수정일 :
 * 설명 : swing 팀원 정보 화면
 */

public class TeamForm extends JPanel {

    private JPanel memberListPanel;
    private List<JButton> memberButtons = new ArrayList<>();
    private int teamNum;

    /**
     * @param teamName 표시 및 관리할 팀의 이름
     */
    public TeamForm(String teamName) {
        this.teamNum = Integer.parseInt(teamName.replaceAll("\\D", ""));
        setLayout(new BorderLayout());

        memberListPanel = new JPanel();
        memberListPanel.setLayout(new BoxLayout(memberListPanel, BoxLayout.Y_AXIS));
        add(new JScrollPane(memberListPanel), BorderLayout.CENTER);

        TeamDB.loadTeamMembersFromDB(teamNum, memberListPanel, memberButtons);

        JButton addButton = new JButton("팀원 추가");
        JButton removeButton = new JButton("팀원 삭제");

        addButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "추가할 팀원 이름을 입력하세요:");
            if (input != null && !input.trim().isEmpty()) {
                TeamDB.saveToDB(teamNum, input.trim(), memberListPanel, memberButtons);
                revalidate();
                repaint();
            }
        });

        removeButton.addActionListener(e -> removeLastMemberField());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void removeLastMemberField() {
        if (!memberButtons.isEmpty()) {
            JButton lastButton = memberButtons.remove(memberButtons.size() - 1);
            memberListPanel.remove(lastButton);
            TeamDB.deleteFromDB(teamNum, lastButton.getText(), memberListPanel, memberButtons);
            revalidate();
            repaint();
        }
    }
}
