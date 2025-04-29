package app.frame;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import app.config.ScreenType;
import app.dbConnect.ScoreDB;
import app.form.TeamForm;

/*
 * 생성자 : 신인철
 * 생성일 : 25.04.24
 * 파일명 : MainFrame.java
 * 수정자 : 
 * 수정일 :
 * 설명 : swing 중앙 화면 전환 제어
 */

public class MainFrame extends JPanel {

    private static MainFrame instance;
    private CardLayout cardLayout;

    private Map<String, JPanel> screenMap = new HashMap<>();

    // 팀 총점 저장용맵
    private Map<Integer, Integer> teamScoreMap = new HashMap<>();

    public MainFrame() {
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        setBackground(Color.WHITE);

        JPanel emptyPanel = new JPanel();
        add(emptyPanel, "empty");
        cardLayout.show(this, "empty");
    }

    /**
     * 
     * @param name 화면 명
     */
    public void showPage(String name) {
        if (name == null || name.trim().isEmpty() || !name.matches(".*\\d+.*")) {
            System.out.println("[오류] 잘못된 화면 이름 요청: '" + name + "'");
            return;
        }

        int teamNum;
        try {
            teamNum = Integer.parseInt(name.replaceAll("\\D", ""));
        } catch (NumberFormatException e) {
            System.out.println("[예외] 팀 번호 파싱 실패: '" + name + "'");
            e.printStackTrace();
            return;
        }

        if (screenMap.containsKey(name)) {
            cardLayout.show(this, name);
            updateTeamScore(teamNum);
        } else {
            JPanel screen = createScreen(name);
            screen.setPreferredSize(new Dimension(800, 500)); // 크기 명시
            add(screen, name);
            screenMap.put(name, screen);
            updateTeamScore(teamNum);
            revalidate(); // 레이아웃 갱신
            repaint(); // 화면 다시 그리기
            cardLayout.show(this, name);
        }
    }

    /**
     * 
     * @param name 화면 명
     * @return
     */
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
                    System.out.println("Error creating screen for: " + name + ", class: " + screenType.getClassName());
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

    /**
     * 
     * @param teamNum 점수갱신을 위한 팀 번호
     */
    public void updateTeamScore(int teamNum) {
        String teamName = teamNum + "팀";

        JPanel panel = screenMap.get(teamName);
        if (panel instanceof TeamForm) {
            int newScore = ScoreDB.teamScoreMoneyDB(teamNum);
            teamScoreMap.put(teamNum, newScore);
            ((TeamForm) panel).updateScoreLabel(teamNum, newScore);
            System.out.println("updateTeamScore 호출됨 - teamNum: " + teamNum);
            System.out.println("screenMap.get(\"" + teamName + "\") = " + panel);
            System.out.println("panel instanceof TeamForm = " + (panel instanceof TeamForm));
        } else {
            System.out.println("[경고] '" + teamName + "' 화면이 아직 로딩되지 않았습니다. 점수 업데이트 생략.");
        }
    }
}
