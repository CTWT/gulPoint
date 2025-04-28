package app.frame;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JPanel;

import app.config.ScreenType;

/*
 * 생성자 : 신인철
 * 생성일 : 25.04.24
 * 파일명 : HeaderFrame.java
 * 수정자 : 
 * 수정일 :
 * 설명 : swing 상단 고정 메뉴바
 */

public class HeaderFrame extends JPanel {

    public HeaderFrame() {
        setPreferredSize(new Dimension(800, 50)); // 상단 영역 크기 지정
        setBackground(Color.LIGHT_GRAY); // 배경색 지정

        // 홈 화면 버튼만 만들어서 이용
        String homePage = "홈화면";
        JButton homeButton = new JButton(homePage);
        add(homeButton);
        homeButton.addActionListener(e -> {
            System.out.println("홈화면 입니다.");
            MainFrame.getInstance().showPage(homePage);
        });

        String[] teamNames = new String[ScreenType.values().length];

        for (int i = 0; i < ScreenType.values().length; i++) {
            ScreenType screenType = ScreenType.values()[i];
            teamNames[i] = screenType.getName();
        }

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
