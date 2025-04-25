package app.form;

import java.awt.BorderLayout;

import app.frame.BaseFrame;
import app.frame.FooterFrame;
import app.frame.HeaderFrame;
import app.frame.MainFrame;

/*
 * 생성자 : 신인철
 * 생성일 : 25.04.24
 * 파일명 : MaintForm.java
 * 수정자 : 
 * 수정일 :
 * 설명 : swing 메인화면 표시
 */

public class MainForm extends BaseFrame {

    private MainFrame mainFrame;

    public MainForm() {
        setLayout(new BorderLayout());

        // 상단 영역
        add(new HeaderFrame(), BorderLayout.NORTH);

        // 하단 영역
        add(new FooterFrame(), BorderLayout.SOUTH);

        mainFrame = MainFrame.getInstance();
        add(mainFrame, BorderLayout.CENTER);
    }

    public void navigateTo(String pageName) {
        mainFrame.showPage(pageName);
    }
}
