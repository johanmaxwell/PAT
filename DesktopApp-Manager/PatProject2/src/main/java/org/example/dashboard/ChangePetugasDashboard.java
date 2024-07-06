package org.example.dashboard;

import com.google.gson.Gson;
import org.example.HttpUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class ChangePetugasDashboard extends JPanel {
    private JComboBox<String> nipPegawaiComboBox;
    private JComboBox<String> nipPetugasComboBox;
    private JButton changeButton;

    public ChangePetugasDashboard() {
        setLayout(new GridBagLayout());
        setBackground(new Color(236, 240, 241));

        nipPegawaiComboBox = new JComboBox<>();
        nipPetugasComboBox = new JComboBox<>();
        changeButton = new JButton("Change Petugas");
        styleButton(changeButton);

        // Populate combo boxes
        fetchNipPegawaiList();
        fetchNipPetugasList();

        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nipPegawai = (String) nipPegawaiComboBox.getSelectedItem();
                String nipPetugas = (String) nipPetugasComboBox.getSelectedItem();

                if (nipPegawai == null || nipPetugas == null) {
                    JOptionPane.showMessageDialog(ChangePetugasDashboard.this,
                            "Please select NIP Pegawai and NIP Petugas.",
                            "Input Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    // Create JSON payload
                    Gson gson = new Gson();
                    String payload = gson.toJson(Map.of(
                            "nip_pegawai", nipPegawai,
                            "nip_petugas", nipPetugas
                    ));

                    // Send PUT request
                    String responseJson = HttpUtil.changePetugas(payload);

                    // Parse and handle response
                    Map<String, Object> response = gson.fromJson(responseJson, Map.class);
                    if (response.get("status").equals(200.0)) {
                        JOptionPane.showMessageDialog(ChangePetugasDashboard.this,
                                response.get("message").toString(),
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(ChangePetugasDashboard.this,
                                "Error: " + response.get("error").toString(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(ChangePetugasDashboard.this,
                            "An error occurred: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        add(new JLabel("NIP Pegawai:"), gbc);
        add(nipPegawaiComboBox, gbc);
        add(new JLabel("NIP Petugas:"), gbc);
        add(nipPetugasComboBox, gbc);
        add(changeButton, gbc);
    }

    private void fetchNipPegawaiList() {
        try {
            String responseJson = HttpUtil.getNipPegawai();
            Gson gson = new Gson();
            Map<String, Object> response = gson.fromJson(responseJson, Map.class);
            List<String> nipPegawaiList = (List<String>) response.get("response");

            if (nipPegawaiList != null) {
                for (String nipPegawai : nipPegawaiList) {
                    nipPegawaiComboBox.addItem(nipPegawai);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Failed to fetch NIP Pegawai list: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
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
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Failed to fetch NIP Petugas list: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(52, 152, 219));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setFocusPainted(false);
    }
}
