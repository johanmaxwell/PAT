package org.example.login;

import com.google.gson.Gson;
import org.example.HttpUtil;
import org.example.SignupPage;
import org.example.absensi.AbsensiListPage;
import org.example.absensi.LogAbsensi;
import org.example.login.LoginRequest;
import org.example.login.LoginResponse;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;


public class MainLogin extends JFrame {

    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton loginButton;
    private final JButton signUpButton;

    private SignupPage signupPage; // Reference to the sign-up page panel

    public MainLogin() {
        setTitle("Login");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with light gray background
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(220, 220, 220)); // Light gray background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Login panel with blue background
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(new Color(60, 90, 170)); // Blue background
        loginPanel.setBorder(new EmptyBorder(50, 50, 50, 50)); // Padding

        // Username label and field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);
        usernameField = new JTextField(20);
        loginPanel.add(usernameLabel, gbc);
        loginPanel.add(usernameField, gbc);

        // Password label and field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        passwordField = new JPasswordField(20);
        loginPanel.add(passwordLabel, gbc);
        loginPanel.add(passwordField, gbc);

        // Login button
        loginButton = new JButton("Login");
        loginButton.addActionListener(e -> loginUser());
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(loginButton, gbc);

        // Add loginPanel to mainPanel
        mainPanel.add(loginPanel, gbc);

        // Signup label
        JLabel signupLabel = new JLabel("Don't have an account?");
        signupLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center align the label
        mainPanel.add(signupLabel, gbc);

        // Sign up button
        signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(e -> openSignUpPage());
        mainPanel.add(signUpButton, gbc);

        // Initialize SignupPage with reference to this frame
        signupPage = new SignupPage(this);

        // Add mainPanel to JFrame
        setContentPane(mainPanel);
    }

    private void loginUser() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username and password are required", "Login Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Create login request object
            LoginRequest loginRequest = new LoginRequest(username, password);

            // Perform POST request using HttpUtil
            String responseJson = HttpUtil.login(loginRequest);

            // Parse JSON response
            Gson gson = new Gson();
            LoginResponse loginResponse = gson.fromJson(responseJson, LoginResponse.class);

            // Redirect based on response
            String redirectUrl = loginResponse.getRedirectUrl();
            System.out.println(redirectUrl);
            if (redirectUrl != null && !redirectUrl.isEmpty()) {
                if (redirectUrl.equals("petugas-dashboard")) {
                    JOptionPane.showMessageDialog(this, loginResponse.getMessage(), "Login Successful", JOptionPane.INFORMATION_MESSAGE);

                    // Open AbsensiListPage after successful login
                    openAbsensiListPage();

                    // Close the current login window
                    dispose();
                }else {
                    JOptionPane.showMessageDialog(this, "You have no access!", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Unknown response from server", "Login Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            // Display concise error message based on the type of exception
            String errorMessage = ex.getMessage();
            System.out.println(errorMessage);
            if (errorMessage != null && !errorMessage.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Error: " + errorMessage, "Login Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "An unknown error occurred", "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void openAbsensiListPage() {
        try {
            // Fetch list absensi from server
            List<LogAbsensi> absensiList = HttpUtil.getListAbsensi();

            // Check if absensiList is not null or empty before proceeding
            if (absensiList != null && !((java.util.List<?>) absensiList).isEmpty()) {
                // Create and display AbsensiListPage with the fetched data
                new AbsensiListPage(absensiList, this).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "No data found.", "Information", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to fetch absensi list: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public void openSignUpPage() {
        getContentPane().removeAll();
        setContentPane(signupPage);
        revalidate();
        repaint();
    }

    public void openLoginPage() {
        clearField();
        getContentPane().removeAll();
        setContentPane(new MainLogin().getContentPane());
        revalidate();
        repaint();
    }

    private void clearField(){
        usernameField.setText("");
        passwordField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainLogin mainLogin = new MainLogin();
            mainLogin.setVisible(true);
        });
    }
}
