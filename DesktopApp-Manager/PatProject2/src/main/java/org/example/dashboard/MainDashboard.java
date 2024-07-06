package org.example.dashboard;

import com.google.gson.Gson;
import org.example.HttpUtil;
import org.example.login.MainLogin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class MainDashboard extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private ChangePetugasDashboard changePetugasDashboard;
    private DeleteTaskDashboard deleteTaskDashboard;
    private TaskAssignmentDashboard taskAssignmentDashboard;
    private JPanel navPanel;

    public MainDashboard() {
        setTitle("Main Dashboard");
        setSize(1920, 840);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create navigation panel with buttons and logout button
        createNavigationPanel();

        // Create main panel with CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(new Color(236, 240, 241));

        // Initialize dashboard panels
        changePetugasDashboard = new ChangePetugasDashboard();
        deleteTaskDashboard = new DeleteTaskDashboard();
        taskAssignmentDashboard = new TaskAssignmentDashboard();

        // Add dashboard panels to main panel
        mainPanel.add(changePetugasDashboard, "Change Petugas");
        mainPanel.add(deleteTaskDashboard, "Delete Task");
        mainPanel.add(taskAssignmentDashboard, "Assign Task");

        // Add navigation panel and main panel to frame
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(navPanel, BorderLayout.WEST);
        getContentPane().add(mainPanel, BorderLayout.CENTER);
    }

    private void createNavigationPanel() {
        navPanel = new JPanel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        navPanel.setBackground(new Color(52, 152, 219));
        navPanel.setPreferredSize(new Dimension(200, getHeight()));

        JButton changePetugasButton = createNavButton("Change Petugas");
        JButton deleteTaskButton = createNavButton("Delete Task");
        JButton assignTaskButton = createNavButton("Assign Task");
        JButton logoutButton = createLogoutButton(); // New logout button

        navPanel.add(Box.createVerticalStrut(40)); // Add some spacing
        navPanel.add(changePetugasButton);
        navPanel.add(Box.createVerticalStrut(30));
        navPanel.add(deleteTaskButton);
        navPanel.add(Box.createVerticalStrut(30));
        navPanel.add(assignTaskButton);
        navPanel.add(Box.createVerticalGlue()); // Push buttons to the top
        navPanel.add(logoutButton); // Add logout button at the end
        navPanel.add(Box.createVerticalStrut(20));
    }

    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBackground(new Color(46, 204, 113));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(180, 40));

        // Add action listeners to navigation buttons
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (text) {
                    case "Change Petugas":
                        cardLayout.show(mainPanel, "Change Petugas");
                        break;
                    case "Delete Task":
                        cardLayout.show(mainPanel, "Delete Task");
                        break;
                    case "Assign Task":
                        cardLayout.show(mainPanel, "Assign Task");
                        break;
                }
            }
        });

        return button;
    }

    private JButton createLogoutButton() {
        JButton button = new JButton("Logout");
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBackground(Color.GRAY);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(180, 40));

        // Add action listener for logout button
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });

        return button;
    }

    private void logout() {
        try {
            String responseJson = HttpUtil.logout(); // Call your logout method in HttpUtil
            Gson gson = new Gson();
            Map<String, Object> response = gson.fromJson(responseJson, Map.class);
            if (response.get("status").equals(200.0)) {
                JOptionPane.showMessageDialog(this,
                        "Logged out successfully.",
                        "Logout",
                        JOptionPane.INFORMATION_MESSAGE);
                showLoginPage();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error: " + response.get("error").toString(),
                        "Logout Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "An error occurred: " + ex.getMessage(),
                    "Logout Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showLoginPage() {
        dispose(); // Dispose current frame
        MainLogin mainLogin = new MainLogin();
        mainLogin.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainDashboard dashboard = new MainDashboard();
            dashboard.setVisible(true);
        });
    }
}
