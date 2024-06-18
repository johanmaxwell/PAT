package org.example.dashboard;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.example.HttpUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TaskAssignmentDashboard extends JPanel {
    private JComboBox<String> nipPetugasComboBox;
    private JComboBox<String> nipPegawaiComboBox;
    private DefaultListModel<String> nipPegawaiListModel;
    private JList<String> nipPegawaiList;
    private JButton addButton;
    private JButton assignButton;

    public TaskAssignmentDashboard() {
        setLayout(new GridBagLayout());
        setBackground(new Color(236, 240, 241));

        // Create and configure combo boxes and buttons with preferred sizes
        nipPetugasComboBox = new JComboBox<>();
        nipPegawaiComboBox = new JComboBox<>();
        nipPegawaiListModel = new DefaultListModel<>();
        nipPegawaiList = new JList<>(nipPegawaiListModel);
        addButton = new JButton("Add");
        assignButton = new JButton("Assign Tasks");

        styleButton(addButton);
        styleButton(assignButton);

        // Set preferred sizes to maintain consistent size
        Dimension comboBoxSize = new Dimension(200, 25);
        Dimension buttonSize = new Dimension(150, 30);

        nipPetugasComboBox.setPreferredSize(comboBoxSize);
        nipPegawaiComboBox.setPreferredSize(comboBoxSize);
        addButton.setPreferredSize(buttonSize);
        assignButton.setPreferredSize(buttonSize);

        // Populate combo boxes
        fetchNipPetugasList();
        fetchNipPegawaiList();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nipPegawai = (String) nipPegawaiComboBox.getSelectedItem();
                if (nipPegawai != null && !nipPegawaiListModel.contains(nipPegawai)) {
                    nipPegawaiListModel.addElement(nipPegawai);
                }
            }
        });

        assignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nipPetugas = (String) nipPetugasComboBox.getSelectedItem();
                if (nipPetugas == null) {
                    JOptionPane.showMessageDialog(TaskAssignmentDashboard.this,
                            "Please select NIP Petugas.",
                            "Input Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                List<String> nipPegawaiList = new ArrayList<>();
                for (int i = 0; i < nipPegawaiListModel.size(); i++) {
                    nipPegawaiList.add(nipPegawaiListModel.get(i));
                }

                if (nipPegawaiList.isEmpty()) {
                    JOptionPane.showMessageDialog(TaskAssignmentDashboard.this,
                            "Please add at least one NIP Pegawai.",
                            "Input Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    Gson gson = new Gson();
                    String payload = gson.toJson(Map.of(
                            "nip_petugas", nipPetugas,
                            "nip_pegawai", nipPegawaiList
                    ));

                    System.out.println(payload);
                    String responseJson = HttpUtil.assignTasks(payload);

                    JsonReader reader = new JsonReader(new StringReader(responseJson));
                    reader.setLenient(true); // Enable lenient mode
                    Map<String, Object> response = gson.fromJson(reader, Map.class);

                    if (response.get("status").equals(200.0)) {
                        JOptionPane.showMessageDialog(TaskAssignmentDashboard.this,
                                response.get("message").toString(),
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                        nipPegawaiListModel.clear();
                    } else {
                        JOptionPane.showMessageDialog(TaskAssignmentDashboard.this,
                                "Error: " + response.get("error").toString(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(TaskAssignmentDashboard.this,
                            "An error occurred: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("NIP Petugas:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(nipPetugasComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("NIP Pegawai:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(nipPegawaiComboBox, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        add(addButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        add(new JScrollPane(nipPegawaiList), gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(assignButton, gbc);
    }

    private void fetchNipPetugasList() {
        try {
            String responseJson = HttpUtil.getNipPetugas();
            System.out.println("NIP Petugas Response: " + responseJson); // Debugging statement
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new StringReader(responseJson));
            reader.setLenient(true); // Enable lenient mode
            Map<String, Object> response = gson.fromJson(reader, Map.class);
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

    private void fetchNipPegawaiList() {
        try {
            String responseJson = HttpUtil.getNipPegawai();
            System.out.println("NIP Pegawai Response: " + responseJson); // Debugging statement
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new StringReader(responseJson));
            reader.setLenient(true); // Enable lenient mode
            Map<String, Object> response = gson.fromJson(reader, Map.class);
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

    private void styleButton(JButton button) {
        button.setBackground(new Color(52, 152, 219));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setFocusPainted(false);
    }
}
