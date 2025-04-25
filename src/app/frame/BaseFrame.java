package app.frame;

import javax.swing.JFrame;

/*
 * 생성자 : 신인철
 * 생성일 : 25.04.24
 * 파일명 : BaseFrame.java
 * 수정자 : 
 * 수정일 :
 * 설명 : swing 기본 출력화면 사이즈 지정
 */

public class BaseFrame extends JFrame {

    public BaseFrame() {
        setTitle("포인트게임");

        // 고정 크기 설정하기
        setSize(800, 600);

        // 창 크기 고정하기
        setResizable(false);

        // 닫기 시 종료
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 중앙으로 정렬하기
        setLocationRelativeTo(null);
    }

}
