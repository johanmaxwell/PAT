package org.example;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SignupPage extends JPanel {

    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JTextField nipField;
    private final JTextField emailField;
    private final JButton signUpButton;
    private final JButton backButton;
    private org.example.login.MainLogin mainLoginFrame; // Fully qualified reference to MainLogin

    public SignupPage(org.example.login.MainLogin mainLoginFrame) {
        this.mainLoginFrame = mainLoginFrame;

        // Main panel with light gray background
        setLayout(new GridBagLayout());
        setBackground(new Color(220, 220, 220)); // Light gray background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Signup panel with blue background
        JPanel signupPanel = new JPanel(new GridBagLayout());
        signupPanel.setBackground(new Color(60, 90, 170)); // Blue background
        signupPanel.setBorder(new EmptyBorder(50, 50, 50, 50)); // Padding

        // Username label and field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);
        usernameField = new JTextField(20);
        signupPanel.add(usernameLabel, gbc);
        signupPanel.add(usernameField, gbc);

        // Password label and field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        passwordField = new JPasswordField(20);
        signupPanel.add(passwordLabel, gbc);
        signupPanel.add(passwordField, gbc);

        // NIP label and field
        JLabel nipLabel = new JLabel("NIP:");
        nipLabel.setForeground(Color.WHITE);
        nipField = new JTextField(20);
        signupPanel.add(nipLabel, gbc);
        signupPanel.add(nipField, gbc);

        // Email label and field
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(Color.WHITE);
        emailField = new JTextField(20);
        signupPanel.add(emailLabel, gbc);
        signupPanel.add(emailField, gbc);

        // Sign up button
        signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(e -> signUp());
        gbc.anchor = GridBagConstraints.CENTER;
        signupPanel.add(signUpButton, gbc);

        // Add signupPanel to this panel
        add(signupPanel, gbc);

        // Back to login button
        backButton = new JButton("Back to Login");
        backButton.addActionListener(e -> openLoginPage());
        add(backButton, gbc);
    }

    private void signUp() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String nip = nipField.getText().trim();
        String email = emailField.getText().trim();

        // Validate input fields
        if (username.isEmpty() || password.isEmpty() || nip.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Manager manager = new Manager(username, password, nip, email);

            HttpUtil.signupManager(manager);

            JOptionPane.showMessageDialog(this, "User registered successfully");
            clearFields();

            openLoginPage();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
        nipField.setText("");
        emailField.setText("");
    }

    private void openLoginPage() {
        mainLoginFrame.openLoginPage();
    }
}
