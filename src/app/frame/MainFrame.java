package app.frame;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;
import java.lang.reflect.Constructor;

import app.config.ScreenType;

/*
 * 생성자 : 신인철
 * 생성일 : 25.04.24
 * 파일명 : MainFrame.java
 * 수정자 : 
 * 수정일 :
 * 설명 : swing 중앙 화면 이벤트 제어
 */

public class MainFrame extends JPanel {

    private static MainFrame instance;
    private CardLayout cardLayout;

    private Map<String, JPanel> screenMap = new HashMap<>();

    public MainFrame() {
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        setBackground(Color.WHITE);

        JPanel emptyPanel = new JPanel();
        add(emptyPanel, "empty");
        cardLayout.show(this, "empty");
    }

    public void showPage(String name) {
        // 화면이 이미 추가되어 있는 경우 화면만 전환
        if (screenMap.containsKey(name)) {
            cardLayout.show(this, name);
        } else {
            // 화면이 없다면 동적으로 추가 후 전환
            JPanel screen = createScreen(name);
            screen.setPreferredSize(new Dimension(800, 500)); // 크기 명시
            add(screen, name);
            screenMap.put(name, screen);
            revalidate(); // 레이아웃 갱신
            repaint(); // 화면 다시 그리기
            cardLayout.show(this, name);
        }
    }

    // 화면을 동적으로 생성
    private JPanel createScreen(String name) {
        for (ScreenType screenType : ScreenType.values()) {
            if (screenType.getName().equals(name)) {
                try {
                    // 리플렉션을 사용하여 화면 클래스 로드
                    Class<?> clazz = Class.forName(screenType.getClassName());
                    Constructor<?> constructor = clazz.getConstructor(String.class);
                    return (JPanel) constructor.newInstance(name);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error creating screen for: " + name);
                }
            }
        }
        return new JPanel(); // 화면이 등록되지 않으면 빈 화면 반환
    }

    public static MainFrame getInstance() {
        if (instance == null) {
            instance = new MainFrame();
        }
        return instance;
    }
}
