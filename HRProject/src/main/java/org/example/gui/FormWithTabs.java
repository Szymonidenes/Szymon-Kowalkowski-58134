package org.example.gui;

import org.example.model.Employee;
import org.example.service.EmployeeManager;
import org.example.service.EmployeeManagerImpl;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.List;

public class FormWithTabs extends JFrame {

    private JTextField IdField;
    private JTextField firstnameField;
    private JTextField lastnameField;
    private JTextField streetField;
    private JTextField zipcodeField;
    private JTextField cityField;
    private JLabel photoImageLabel;
    private JLabel cardPhotoLabel;
    private JTable employeesTable;
    private DefaultTableModel tableModel;
    private byte[] imageBytes;
    private JTextField cardFirstNameField;
    private JTextField cardLastNameField;
    private JTextField cardStreetField;
    private JTextField cardZipCodeField;
    private JTextField cardCityField;

    private final EmployeeManager employeeManager;
    private List<Employee> employees;
    private int currentIndex;

    public FormWithTabs() {
        employeeManager = new EmployeeManagerImpl();

        employees = employeeManager.getAllEmployees();
        currentIndex = 0;

        setTitle("Formularz z zakładkami");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Tworzenie zakładek
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Formularz", createFormPanel());
        tabbedPane.addTab("Kartoteki", createRecordsPanel());
        // Ustawienie aktywnej zakładki na "Formularz"
        tabbedPane.setSelectedIndex(0);

        tabbedPane.addChangeListener(e ->
                {
                    employees = employeeManager.getAllEmployees();
                    clearCardFields();
                    if (employees.size() != 0) {
                        displayEmployee(employees.get(currentIndex));
                    }


                }
        );

        // Dodawanie zakładek do ramki
        getContentPane().add(tabbedPane);

        // Wyświetlenie danych w tabeli
        showAllData();
        cardFieldsRO();

        if (employees.size() != 0) {
            displayEmployee(employees.get(currentIndex));
        }

        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private JPanel createFormPanel() {
        // Tworzenie panelu dla zakładki "Formularz"
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BorderLayout());

        // Tworzenie pierwszej kolumny
        JPanel firstColumnPanel = new JPanel(new BorderLayout());
        firstColumnPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Tworzenie etykiety tytułu formularza
        JLabel titleLabel = new JLabel("Formularz rejestracji pracowników");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        firstColumnPanel.add(titleLabel, BorderLayout.NORTH);

        // Tworzenie formularza z polami
        JPanel formFieldsPanel = new JPanel(new GridLayout(7, 2));
        formFieldsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        IdField = new JTextField();
        IdField.setVisible(false);
        JLabel firstnameLabel = new JLabel("Imię:");
        formFieldsPanel.add(firstnameLabel);
        firstnameField = new JTextField();
        formFieldsPanel.add(firstnameField);
        JLabel lastnameLabel = new JLabel("Nazwisko:");
        formFieldsPanel.add(lastnameLabel);
        lastnameField = new JTextField();
        formFieldsPanel.add(lastnameField);
        JLabel streetLabel = new JLabel("Ulica:");
        formFieldsPanel.add(streetLabel);
        streetField = new JTextField();
        formFieldsPanel.add(streetField);
        JLabel zipcodeLabel = new JLabel("Kod pocztowy:");
        formFieldsPanel.add(zipcodeLabel);
        zipcodeField = new JTextField();
        formFieldsPanel.add(zipcodeField);
        JLabel cityLabel = new JLabel("Miasto:");
        formFieldsPanel.add(cityLabel);
        cityField = new JTextField();
        formFieldsPanel.add(cityField);
        JLabel photoLabel = new JLabel("Dodaj zdjęcie:");
        formFieldsPanel.add(photoLabel);
        JButton browseButton = new JButton("Przeglądaj...");
        formFieldsPanel.add(browseButton);
        firstColumnPanel.add(formFieldsPanel, BorderLayout.CENTER);

        browseButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "jpeg", "png", "gif");
            fileChooser.setFileFilter(filter);
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    FileInputStream fis = new FileInputStream(selectedFile);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = fis.read(buffer)) != -1) {
                        bos.write(buffer, 0, bytesRead);
                    }
                    imageBytes = bos.toByteArray();

                    // Element graficzny
                    ImageIcon imageIcon = new ImageIcon(imageBytes);
                    Image image = imageIcon.getImage();
                    Image scaledImage = image.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
                    // Tworzenie nowego ImageIcon z przeskalowanym obrazem
                    ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
                    photoImageLabel.setIcon(scaledImageIcon);

                    fis.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Tworzenie przycisków "Anuluj" i "Zapisz"
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton cancelButton = new JButton("Anuluj");
        buttonsPanel.add(cancelButton);

        cancelButton.addActionListener(e -> clearFields());

        JButton saveButton = new JButton("Zapisz");
        buttonsPanel.add(saveButton);

        saveButton.addActionListener(e -> {

            if (firstnameField.getText().isEmpty() || lastnameField.getText().isEmpty() || streetField.getText().isEmpty() || zipcodeField.getText().isEmpty() || cityField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Wszystkie pola muszą być wypełnione", "Błąd", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!IdField.getText().isEmpty() && !IdField.getText().isBlank()) {

                int employeeId = Integer.parseInt(IdField.getText());

                Employee emp = new Employee(employeeId, firstnameField.getText(), lastnameField.getText(), streetField.getText(), zipcodeField.getText(), cityField.getText());
                employeeManager.updateEmployee(emp);
                refreshTable();
                clearFields();

            } else {

                Employee employee = new Employee(firstnameField.getText(), lastnameField.getText(), streetField.getText(), zipcodeField.getText(), cityField.getText(), imageBytes);

                int result = employeeManager.createEmployee(employee);

                if (result != 0) {
                    refreshTable();
                    clearFields();
                    JOptionPane.showMessageDialog(null, "Dodano nowego pracownika");
                } else {
                    JOptionPane.showMessageDialog(null, "Nie dodano nowego pracownika");
                }
            }
        });

        JButton editButton = new JButton("Edytuj");
        buttonsPanel.add(editButton);

        editButton.addActionListener(e -> {
            int selectedRow = employeesTable.getSelectedRow();

            if (selectedRow != -1) {
                // Pobieranie danych z modelu tabeli
                int id = (int) tableModel.getValueAt(selectedRow, 0);
                String firstname = (String) tableModel.getValueAt(selectedRow, 1);
                String lastname = (String) tableModel.getValueAt(selectedRow, 2);
                String street = (String) tableModel.getValueAt(selectedRow, 3);
                String zipcode = (String) tableModel.getValueAt(selectedRow, 4);
                String city = (String) tableModel.getValueAt(selectedRow, 5);

                // Wyświetlanie formularza edycji
                IdField.setText(String.valueOf(id));
                firstnameField.setText(firstname);
                lastnameField.setText(lastname);
                streetField.setText(street);
                zipcodeField.setText(zipcode);
                cityField.setText(city);

            } else {
                JOptionPane.showMessageDialog(null, "Nie wybrałeś pracownika do usunięcia");
            }
        });

        JButton deleteButton = new JButton("Usuń");
        buttonsPanel.add(deleteButton);

        deleteButton.addActionListener(e -> {
            int rowIndex = employeesTable.getSelectedRow();

            if (rowIndex != -1) {
                int employeeId = (int) employeesTable.getValueAt(rowIndex, 0);

                employeeManager.deleteEmployee(employeeId);
                refreshTable();

                if (employees.isEmpty()) {
                    clearCardFields();
                    currentIndex = 0;
                } else {
                    if (currentIndex >= employees.size()) {
                        currentIndex = employees.size() - 1;
                    }
                    displayEmployee(employees.get(currentIndex));
                    if (employeesTable.getSelectedRow() != -1) {
                        employeesTable.setRowSelectionInterval(currentIndex, currentIndex);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Nie zaznaczono osoby do usunięcia");
            }
        });

        firstColumnPanel.add(buttonsPanel, BorderLayout.SOUTH);

        // Tworzenie drugiej kolumny (zdjęcie)
        JPanel secondColumnPanel = new JPanel();
        secondColumnPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        photoImageLabel = new JLabel();
        secondColumnPanel.add(photoImageLabel);

        // Tworzenie panelu dla całej zakładki "Formularz"
        JPanel formTabPanel = new JPanel(new BorderLayout());
        formTabPanel.add(firstColumnPanel, BorderLayout.WEST);
        formTabPanel.add(secondColumnPanel, BorderLayout.EAST);
//        formTabPanel.add(tableScrollPane, BorderLayout.SOUTH);

        return formTabPanel;
    }


    private JPanel createRecordsPanel() {
        // Tworzenie panelu dla zakładki "Kartoteki"
        JPanel recordsPanel = new JPanel();
        recordsPanel.setLayout(new BorderLayout());

        // Tworzenie pierwszej kolumny
        JPanel firstColumnPanel = new JPanel(new BorderLayout());
        firstColumnPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Tworzenie etykiety tytułu kartotek pracowników
        JLabel titleLabel = new JLabel("Kartoteki pracowników");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        firstColumnPanel.add(titleLabel, BorderLayout.NORTH);

        // Tworzenie formularza z polami
        JPanel formFieldsPanel = new JPanel(new GridLayout(7, 2));
        formFieldsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        formFieldsPanel.add(new JLabel("Imię:"));
        cardFirstNameField = new JTextField();
        formFieldsPanel.add(cardFirstNameField);
        formFieldsPanel.add(new JLabel("Nazwisko:"));
        cardLastNameField = new JTextField();
        formFieldsPanel.add(cardLastNameField);
        formFieldsPanel.add(new JLabel("Ulica:"));
        cardStreetField = new JTextField();
        formFieldsPanel.add(cardStreetField);
        formFieldsPanel.add(new JLabel("Kod pocztowy:"));
        cardZipCodeField = new JTextField();
        formFieldsPanel.add(cardZipCodeField);
        formFieldsPanel.add(new JLabel("Miasto:"));
        cardCityField = new JTextField();
        formFieldsPanel.add(cardCityField);
        firstColumnPanel.add(formFieldsPanel, BorderLayout.CENTER);

        // Tworzenie przycisków "Poprzedni" i "Następny"
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton previousButton = new JButton("Poprzedni");
        buttonsPanel.add(previousButton);
        previousButton.addActionListener(e -> {
            employees = employeeManager.getAllEmployees();

            if (currentIndex > 0) {
                currentIndex--;
                displayEmployee(employees.get(currentIndex));
                employeesTable.setRowSelectionInterval(currentIndex, currentIndex);
            }
        });


        JButton nextButton = new JButton("Następny");
        buttonsPanel.add(nextButton);
        nextButton.addActionListener(e -> {
            employees = employeeManager.getAllEmployees();
            if (currentIndex < employees.size() - 1) {
                currentIndex++;
                displayEmployee(employees.get(currentIndex));
                employeesTable.setRowSelectionInterval(currentIndex, currentIndex);
            }
        });

        firstColumnPanel.add(buttonsPanel, BorderLayout.SOUTH);

        // Tworzenie drugiej kolumny (zdjęcie)
        JPanel secondColumnPanel = new JPanel();
        secondColumnPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // zdjęcie w kartotece po prawej stronie
        cardPhotoLabel = new JLabel();
        secondColumnPanel.add(cardPhotoLabel);

        // Tworzenie panelu dla całej zakładki "Kartoteki"
        JPanel recordsTabPanel = new JPanel(new BorderLayout());
        recordsTabPanel.add(firstColumnPanel, BorderLayout.WEST);
        recordsTabPanel.add(secondColumnPanel, BorderLayout.EAST);
//        recordsTabPanel.add(tableScrollPane, BorderLayout.SOUTH);

        return recordsTabPanel;
    }

    private void clearFields() {
        firstnameField.setText("");
        lastnameField.setText("");
        streetField.setText("");
        zipcodeField.setText("");
        cityField.setText("");
        photoImageLabel.setIcon(null);
        IdField.setText("");
        employeesTable.clearSelection();
    }

    private void clearCardFields() {
        cardFirstNameField.setText("");
        cardLastNameField.setText("");
        cardStreetField.setText("");
        cardZipCodeField.setText("");
        cardCityField.setText("");
        cardPhotoLabel.setIcon(null);
    }

    private void cardFieldsRO() {
        cardFirstNameField.setEnabled(false);
        cardLastNameField.setEnabled(false);
        cardStreetField.setEnabled(false);
        cardZipCodeField.setEnabled(false);
        cardCityField.setEnabled(false);
    }

    private void showAllData() {
        // Tworzenie modelu tabeli
        tableModel = new DefaultTableModel();
        employeesTable = new JTable(tableModel);
        new JScrollPane(employeesTable);

        // Dodawanie kolumn do modelu tabeli
        tableModel.addColumn("Id");
        tableModel.addColumn("Imię");
        tableModel.addColumn("Nazwisko");
        tableModel.addColumn("Ulica");
        tableModel.addColumn("Kod pocztowy");
        tableModel.addColumn("Miasto");

        // Pobieranie danych z funkcji w innej klasie
        List<Employee> employees = employeeManager.getAllEmployees();

        // Dodawanie wierszy z danymi do modelu tabeli
        for (Employee employee : employees) {
            Object[] rowData = {employee.getId(), employee.getFirstname(), employee.getLastname(), employee.getStreet(), employee.getZipcode(), employee.getCity()};
            tableModel.addRow(rowData);
        }

        // Dodawanie tabeli do JScrollPane i ustawienie jako zawartość ramki
        JScrollPane scrollPane = new JScrollPane(employeesTable);
        add(scrollPane, BorderLayout.SOUTH);
    }

    private void refreshTable() {
        // Clear the existing table data
        tableModel.setRowCount(0);

        // Retrieve updated employee data from the database
        List<Employee> employees = employeeManager.getAllEmployees();

        // Add the updated data to the table model
        for (Employee employee : employees) {
            Object[] rowData = {employee.getId(), employee.getFirstname(), employee.getLastname(), employee.getStreet(), employee.getZipcode(), employee.getCity()};
            tableModel.addRow(rowData);
        }

        // Notify the table of the changes
        tableModel.fireTableDataChanged();
    }

    private void displayEmployee(Employee employee) {
        cardFirstNameField.setText(employee.getFirstname());
        cardLastNameField.setText(employee.getLastname());
        cardStreetField.setText(employee.getStreet());
        cardZipCodeField.setText(employee.getZipcode());
        cardCityField.setText(employee.getCity());
        byte[] photo = employee.getPhoto();

        // Kod do wyświetlania zdjęcia w pictureLabel
        Image image = null;

        if (photo != null) {
            InputStream inputStream = new ByteArrayInputStream(photo);
            try {
                image = ImageIO.read(inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (image != null) {
            Image scaledImage = image.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(scaledImage);

            cardPhotoLabel.setIcon(imageIcon);
        } else {
            // Przykładowy element graficzny (plik graficzny)
            image = new ImageIcon("src/main/resources/image/no_photo.jpg").getImage();

            Image scaledImage = image.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            // Tworzenie nowego ImageIcon z przeskalowanym obrazem
            ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
            cardPhotoLabel.setIcon(scaledImageIcon);
        }
    }
}