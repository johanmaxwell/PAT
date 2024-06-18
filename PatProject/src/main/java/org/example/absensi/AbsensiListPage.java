package org.example.absensi;

import org.example.HttpUtil;
import org.example.login.MainLogin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.net.URL;
import java.util.List;

public class AbsensiListPage extends JFrame {

    private JTable absensiTable;
    private JButton approveButton;
    private JButton rejectButton;
    private JButton telatButton;
    private JButton pulangAwalButton;
    private JButton logoutButton; // Added logout button
    private MainLogin mainLoginFrame; // Reference to MainLogin frame

    public AbsensiListPage(List<LogAbsensi> absensiList, MainLogin mainLoginFrame) {
        this.mainLoginFrame = mainLoginFrame;

        setTitle("Absensi List");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Set Nimbus look and feel for modern appearance
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Column names including Foto Bukti Masuk and Foto Bukti Pulang
        String[] columnNames = {"NIP Pegawai", "Waktu Masuk", "Waktu Pulang", "Status", "Foto Bukti Masuk", "Foto Bukti Pulang"};
        Object[][] data = new Object[absensiList.size()][6]; // Adjust to match number of columns

        /// Adjusting the loop where data is populated
        for (int i = 0; i < absensiList.size(); i++) {
            LogAbsensi absensi = absensiList.get(i);
            data[i][0] = absensi.getNip_pegawai();
            data[i][1] = absensi.getWaktu_masuk();
            data[i][2] = absensi.getWaktu_pulang() != null ? absensi.getWaktu_pulang() : ""; // Handle null value for waktu_pulang
            data[i][3] = absensi.getStatus();

            try {
                if (absensi.getFoto_bukti_masuk() != null) {
                    data[i][4] = new ImageIcon(new URL("http://localhost:8000/upload/" + absensi.getFoto_bukti_masuk()));
                } else {
                    data[i][4] = new ImageIcon(); // Empty icon if foto_bukti_masuk is null
                }
            } catch (Exception e) {
                data[i][4] = new ImageIcon(); // Empty icon if there's an error
            }

            if (absensi.getFoto_bukti_pulang() != null) {
                try {
                    data[i][5] = new ImageIcon(new URL("http://localhost:8000/upload/" + absensi.getFoto_bukti_pulang()));
                } catch (Exception e) {
                    data[i][5] = new ImageIcon(); // Empty icon if there's an error
                }
            } else {
                data[i][5] = ""; // Set empty string or null depending on your requirements
            }
        }


        // Use DefaultTableModel to allow dynamic addition of rows and columns
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        absensiTable = new JTable(model) {
            @Override
            public Class<?> getColumnClass(int column) {
                return getValueAt(0, column).getClass();
            }
        };

        // Set renderers
        absensiTable.getColumnModel().getColumn(0).setCellRenderer(new CenteredTableCellRenderer());
        absensiTable.getColumnModel().getColumn(1).setCellRenderer(new CenteredTableCellRenderer());
        absensiTable.getColumnModel().getColumn(2).setCellRenderer(new CenteredTableCellRenderer());
        absensiTable.getColumnModel().getColumn(3).setCellRenderer(new CenteredTableCellRenderer());
        absensiTable.getColumnModel().getColumn(4).setCellRenderer(new ImageRenderer());
        absensiTable.getColumnModel().getColumn(5).setCellRenderer(new ImageRenderer());

        // Set preferred size and properties
        absensiTable.setPreferredScrollableViewportSize(new Dimension(1600, 800)); // Adjust size as needed
        absensiTable.setFillsViewportHeight(true);
        absensiTable.setRowHeight(200); // Adjust row height to fit images

        // Customize table header
        JTableHeader header = absensiTable.getTableHeader();
        header.setBackground(Color.WHITE);
        header.setForeground(Color.BLACK);
        header.setFont(new Font("Arial", Font.BOLD, 14));

        // Add title above the table
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel("List Absensi Pegawai");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titlePanel.add(titleLabel);

        getContentPane().add(titlePanel, BorderLayout.NORTH);

        // Add padding and border around the table within JScrollPane
        JScrollPane scrollPane = new JScrollPane(absensiTable);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(20, 10, 0, 20), // Outer empty border for padding
                new LineBorder(Color.BLACK)   // Inner line border for table
        ));
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Initialize buttons with custom styles
        approveButton = new JButton("Approve");
        rejectButton = new JButton("Reject");
        telatButton = new JButton("Telat");
        pulangAwalButton = new JButton("Pulang Awal");
        logoutButton = new JButton("Logout"); // Initialize logout button

        // Apply custom styles to buttons
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        Color buttonBgColor = new Color(0, 128, 0); // Green
        Color buttonFgColor = Color.WHITE;

        approveButton.setFont(buttonFont);
        approveButton.setBackground(buttonBgColor);
        approveButton.setForeground(buttonFgColor);

        rejectButton.setFont(buttonFont);
        rejectButton.setBackground(Color.RED);
        rejectButton.setForeground(buttonFgColor);

        telatButton.setFont(buttonFont);
        telatButton.setBackground(Color.ORANGE);
        telatButton.setForeground(buttonFgColor);

        pulangAwalButton.setFont(buttonFont);
        pulangAwalButton.setBackground(Color.BLUE);
        pulangAwalButton.setForeground(buttonFgColor);

        logoutButton.setFont(buttonFont);
        logoutButton.setBackground(Color.GRAY); // Example color
        logoutButton.setForeground(buttonFgColor);

        // Add action listeners to buttons
        approveButton.addActionListener(e -> performAction("approved"));
        rejectButton.addActionListener(e -> performAction("rejected"));
        telatButton.addActionListener(e -> performAction("telat"));
        pulangAwalButton.addActionListener(e -> performAction("pulang_awal"));
        logoutButton.addActionListener(e -> logoutUser()); // Add listener for logout button

        // Create panel for buttons and add to JFrame
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(approveButton);
        buttonPanel.add(rejectButton);
        buttonPanel.add(telatButton);
        buttonPanel.add(pulangAwalButton);
        buttonPanel.add(logoutButton); // Add logout button to panel

        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Set JFrame size to 1920x1080
        setSize(1920, 840);
        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }

    private void performAction(String action) {
        int selectedRow = absensiTable.getSelectedRow();
        if (selectedRow != -1) {
            String nipPegawai = (String) absensiTable.getValueAt(selectedRow, 0); // Assuming NIP Pegawai is in the first column
            try {
                String response = HttpUtil.updateAbsensiStatus(action, nipPegawai);
                JOptionPane.showMessageDialog(null, "Status updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

                // Update status in JTable
                DefaultTableModel model = (DefaultTableModel) absensiTable.getModel();
                model.setValueAt(action, selectedRow, 3); // Assuming Status column index is 3

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Failed to update status: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a row to perform the action.", "No Row Selected", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void logoutUser() {
        try {
            String response = HttpUtil.logout(); // Call logout method from HttpUtil class
            JOptionPane.showMessageDialog(null, "Logged out successfully.", "Logout", JOptionPane.INFORMATION_MESSAGE);
            dispose(); // Close the current JFrame

            // Redirect to login page using mainLoginFrame instance
            mainLoginFrame.setVisible(true); // Assuming mainLoginFrame has setVisible method to show the frame
            mainLoginFrame.setLocationRelativeTo(null); // Center the login frame on screen

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Failed to logout: " + ex.getMessage(), "Logout Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Custom cell renderer to display images in JTable
    private static class ImageRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof ImageIcon) {
                JLabel label = new JLabel((ImageIcon) value);
                label.setHorizontalAlignment(JLabel.CENTER);
                return label;
            }
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

    // Custom cell renderer to center text in specific columns
    private static class CenteredTableCellRenderer extends DefaultTableCellRenderer {
        public CenteredTableCellRenderer() {
            setHorizontalAlignment(SwingConstants.CENTER);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Example usage: Fetch list absensi and display in AbsensiListPage
                List<LogAbsensi> absensiList = HttpUtil.getListAbsensi();
                MainLogin mainLogin = new MainLogin(); // Initialize MainLogin instance
                new AbsensiListPage(absensiList, mainLogin);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to fetch absensi list: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
