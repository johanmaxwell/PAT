package org.example;

import org.example.login.MainLogin;
import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainLogin mainLogin = new MainLogin();
            mainLogin.setVisible(true);
        });
    }
}
