package app.frame;

import javax.swing.JFrame;

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
