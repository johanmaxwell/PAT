package org.example.dashboard;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.example.HttpUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class DeleteTaskDashboard extends JPanel {
    private JComboBox<String> nipPetugasComboBox;
    private JButton deleteButton;

    public DeleteTaskDashboard() {
        setLayout(new GridBagLayout());
        setBackground(new Color(236, 240, 241)); // Light grey background

        nipPetugasComboBox = new JComboBox<>();
        deleteButton = new JButton("Delete Tasks");
        styleButton(deleteButton); // Apply button styling

        // Populate NIP Petugas combo box
        fetchNipPetugasList();

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteTasks();
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel nipPetugasLabel = new JLabel("NIP Petugas:");
        nipPetugasLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        nipPetugasLabel.setForeground(new Color(52, 73, 94)); // Dark grey text
        add(nipPetugasLabel, gbc);

        add(nipPetugasComboBox, gbc);
        add(deleteButton, gbc);
    }

    private void fetchNipPetugasList() {
        try {
            String responseJson = HttpUtil.getNipPetugas();
            Gson gson = new Gson();
            Map<String, Object> response = gson.fromJson(responseJson, Map.class);
            List<String> nipPetugasList = (List<String>) response.get("response");

            if (nipPetugasList != null) {
                for (String nipPetugas : nipPetugasList) {
                    nipPetugasComboBox.addItem(nipPetugas);
                }
            }
        } catch (JsonSyntaxException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Malformed JSON received: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error fetching NIP Petugas: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteTasks() {
        String nipPetugas = (String) nipPetugasComboBox.getSelectedItem();

        if (nipPetugas == null || nipPetugas.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please select NIP Petugas.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Gson gson = new Gson();
            String payload = gson.toJson(Map.of("nip_petugas", nipPetugas));

            String responseJson = HttpUtil.deleteTask(payload);

            Map<String, Object> response = gson.fromJson(responseJson, Map.class);
            if (response.get("status").equals(200.0)) {
                JOptionPane.showMessageDialog(this,
                        response.get("message").toString(),
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                // Optionally refresh the combo box after deletion
                nipPetugasComboBox.removeItem(nipPetugas);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error: " + response.get("error").toString(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (JsonSyntaxException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Malformed JSON received: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "An error occurred: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(231, 76, 60)); // Red color
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setPreferredSize(new Dimension(150, 40));
    }
}
