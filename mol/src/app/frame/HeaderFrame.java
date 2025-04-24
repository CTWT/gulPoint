package app.frame;

import java.awt.*;

import javax.swing.*;

public class HeaderFrame extends JPanel {

    public HeaderFrame() {
        setPreferredSize(new Dimension(800, 50)); // 상단 영역 크기 지정
        setBackground(Color.LIGHT_GRAY); // 배경색 지정

        // 버튼 이름 배열
        String[] teamNames = { "1팀", "2팀", "3팀", "4팀", "5팀" };

        // 각 팀 버튼 생성
        for (String teamName : teamNames) {
            JButton teamButton = new JButton(teamName);
            add(teamButton);

            // 버튼 클릭 시 동작 정의
            teamButton.addActionListener(e -> {
                System.out.println("요청된 팀 페이지: " + teamName);
                // 추후 MainFrame 인스턴스에서 showTeamPage(teamName) 호출 예정
                MainFrame.getInstance().showPage(teamName);
            });
        }
    }
}
