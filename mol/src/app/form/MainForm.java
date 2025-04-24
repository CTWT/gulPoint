package app.form;

import java.awt.BorderLayout;

import app.frame.BaseFrame;
import app.frame.FooterFrame;
import app.frame.HeaderFrame;
import app.frame.MainFrame;

public class MainForm extends BaseFrame {

    private MainFrame mainFrame;

    public MainForm() {
        setLayout(new BorderLayout());

        // 상단 영역
        add(new HeaderFrame(), BorderLayout.NORTH);

        // 하단 영역
        add(new FooterFrame(), BorderLayout.SOUTH);

        mainFrame = new MainFrame();
        add(mainFrame, BorderLayout.CENTER);
    }

    public void navigateTo(String pageName) {
        mainFrame.showPage(pageName);
    }
}
