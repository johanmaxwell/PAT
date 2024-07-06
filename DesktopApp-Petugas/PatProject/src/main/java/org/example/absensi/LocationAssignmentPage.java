package org.example.absensi;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.example.HttpUtil;

import javax.swing.*;
import java.awt.*;
import java.io.StringReader;
import java.util.List;
import java.util.Map;

public class LocationAssignmentPage extends JFrame {
    private JComboBox<String> nipComboBox;
    private JTextField latitudeField;
    private JTextField longitudeField;
    private JTextField assignmentDateField;
    private JButton assignButton;
    private JButton backButton;
    private AbsensiListPage absensiListPage;

    public LocationAssignmentPage(List<String> nipList, AbsensiListPage absensiListPage) {
        this.absensiListPage = absensiListPage;

        setTitle("Assign Location");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Set Nimbus look and feel for modern appearance
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create form components
        JLabel nipLabel = new JLabel("NIP Pegawai:");
        nipComboBox = new JComboBox<>(nipList.toArray(new String[0]));
        JLabel latitudeLabel = new JLabel("Latitude:");
        latitudeField = new JTextField(15);
        JLabel longitudeLabel = new JLabel("Longitude:");
        longitudeField = new JTextField(15);
        JLabel assignmentDateLabel = new JLabel("Assignment Date:");
        assignmentDateField = new JTextField(15);

        assignButton = new JButton("Assign");
        backButton = new JButton("Back");

        assignButton.addActionListener(e -> assignLocation());
        backButton.addActionListener(e -> goBack());

        // Layout setup
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nipLabel, gbc);

        gbc.gridx = 1;
        panel.add(nipComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(latitudeLabel, gbc);

        gbc.gridx = 1;
        panel.add(latitudeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(longitudeLabel, gbc);

        gbc.gridx = 1;
        panel.add(longitudeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(assignmentDateLabel, gbc);

        gbc.gridx = 1;
        panel.add(assignmentDateField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(assignButton, gbc);

        gbc.gridy = 5;
        panel.add(backButton, gbc);

        getContentPane().add(panel, BorderLayout.CENTER);

        setSize(400, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void assignLocation() {
        String nip = (String) nipComboBox.getSelectedItem();
        String latitude = latitudeField.getText();
        String longitude = longitudeField.getText();
        String assignmentDate = assignmentDateField.getText();

        try {
            String response = HttpUtil.assignLocation(nip, latitude, longitude, assignmentDate);
            JOptionPane.showMessageDialog(this, "Location assigned successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to assign location: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void goBack() {
        absensiListPage.setVisible(true);
        dispose();
    }
}
