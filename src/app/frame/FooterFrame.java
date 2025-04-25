package app.frame;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JPanel;

/*
 * 생성자 : 신인철
 * 생성일 : 25.04.24
 * 파일명 : Footer.java
 * 수정자 : 
 * 수정일 :
 * 설명 : swing 하단 출력 고정 메뉴 바 
 */

public class FooterFrame extends JPanel {

    public FooterFrame() {
        setPreferredSize(new Dimension(800, 40));
        setBackground(Color.DARK_GRAY);

        add(new JButton("주사위게임"));
        add(new JButton("룰렛"));
        add(new JButton("랜덤팀 고르기"));
        add(new JButton("점수입력"));
        add(new JButton("팀별 점수출력"));
    }
}
