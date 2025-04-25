package app.login;

/*
 * 생성자 : 신인철
 * 생성일 : 25.04.24
 * 파일명 : LoginForm.java
 * 수정자 : 
 * 수정일 :
 * 설명 : 로그인 화면 form
 */

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import app.dbConnect.LoginDB;
import app.form.MainForm;
import app.frame.BaseFrame;

public class LoginForm extends BaseFrame {

    private JTextField userNameField;
    private JPasswordField userPasswordField;

    public LoginForm() {
        setLayout(new GridBagLayout()); // 자동 정렬 레이아웃

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // 여백

        // ID 라벨
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST; // 오른쪽 정렬
        add(new JLabel("ID"), gbc);

        // ID 입력창
        userNameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST; // 왼쪽 정렬
        add(userNameField, gbc);

        // PW 라벨
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("PW"), gbc);

        // PW 입력창
        userPasswordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(userPasswordField, gbc);

        // 로그인 버튼
        JButton loginBtn = new JButton("로그인");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // 두 칸 합치기
        gbc.anchor = GridBagConstraints.CENTER;
        add(loginBtn, gbc);

        loginBtn.addActionListener(e -> login());
    }

    private void login() {
        String username = userNameField.getText();
        String password = new String(userPasswordField.getPassword());
        boolean result = LoginDB.login(username, password);

        if (result) {
            JOptionPane.showMessageDialog(this, "로그인 성공");

            MainForm mainForm = new MainForm();
            mainForm.setVisible(true);
            mainForm.navigateTo("nones");
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "로그인 실패\nID 또는 PW를 확인하세요");
        }
    }
}