package org.example.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginForm() {
        // Ustawienia okna
        setTitle("Ekran logowania");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Tytuł
        JLabel titleLabel = new JLabel("Rejestr osób");
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 24));
        titleLabel.setBorder(new EmptyBorder(0, 30, 0, 0));

        // Element graficzny
        ImageIcon imageIcon = new ImageIcon("src/main/resources/image/no_photo.jpg");
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        // Tworzenie nowego ImageIcon z przeskalowanym obrazem
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
        JLabel graphicLabel = new JLabel(scaledImageIcon); // Przykładowy element graficzny (plik graficzny)
        graphicLabel.setBorder(new EmptyBorder(20, 10, 20, 30));

        // Tytuł
        JLabel loginLabel = new JLabel("Logowanie");
        loginLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 12));
        loginLabel.setBorder(new EmptyBorder(0, 50, 30, 0));

        // Przycisk Logowanie
        JButton loginButton = new JButton("Zaloguj");
        loginButton.addActionListener(e -> {
            // Wykonaj akcję logowania
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if(isValidLogin(username, password)) {
                openNewForm();
            } else {
                JOptionPane.showMessageDialog(null, "Błędny login lub hasło", "Błąd logowania", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Etykieta Nazwa użytkownika
        JLabel usernameLabel = new JLabel("Nazwa użytkownika:");

        // Pole tekstowe na login
        usernameField = new JTextField(20);
        // Ustawienie preferowanego rozmiaru
        usernameField.setMaximumSize(new Dimension(400, 20));

        // Etykieta Hasło
        JLabel passwordLabel = new JLabel("Hasło:");

        // Pole tekstowe na hasło
        passwordField = new JPasswordField(20);
        passwordField.setMaximumSize(new Dimension(400, 20));

        // Checkbox "Zapamiętaj"
        JCheckBox rememberCheckbox = new JCheckBox("Zapamiętaj");

        JTextArea description = new JTextArea();
        description.setEditable(false);
        description.setFont(new Font("Arial", Font.PLAIN, 12));
        description.setForeground(Color.GRAY);
        description.setLineWrap(true);
        description.setText("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
        description.setPreferredSize(new Dimension(200, 100));
        description.setBackground(getContentPane().getBackground());

        // Komponenty logowania
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));

        loginPanel.add(loginLabel, BorderLayout.CENTER);
        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(rememberCheckbox);
        loginPanel.add(loginButton, BorderLayout.EAST);

        loginPanel.setBorder(new EmptyBorder(0, 100, 0, 0));

        JPanel areaPanel = new JPanel((new BorderLayout()));
        areaPanel.add(description, BorderLayout.WEST);
        areaPanel.setBorder(new EmptyBorder(0,30,40,0));


        // Panel tytułu
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(titleLabel, BorderLayout.WEST);
        titlePanel.add(graphicLabel, BorderLayout.EAST);

        // Dodawanie elementów do kontenera
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(titlePanel, BorderLayout.NORTH);
        contentPanel.add(loginPanel, BorderLayout.CENTER);
        contentPanel.add(areaPanel, BorderLayout.SOUTH);
        add(contentPanel);

        // Ustawienia wyglądu
        setSize(600, 550);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private boolean isValidLogin(String username, String password) {
        return username.equals("admin") && password.equals("admin123");
    }

    private void openNewForm() {
        // Create and display a new instance of the FormWithTabs class
        SwingUtilities.invokeLater(() -> new FormWithTabs().setVisible(true));

        // Zamknięcie okienka
        dispose();
    }
}