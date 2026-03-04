// Programmer: KAVIN KARL C. MOLOS - 22-0702-626

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.io.*;

public class StudentRecordSystem extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField idField, firstNameField, lastNameField;
    private JTextField lab1Field, lab2Field, lab3Field, prelimField, attendanceField;
    private JButton addButton, deleteButton, clearButton;
    
    public StudentRecordSystem() {
        setTitle("Student Records - KAVIN KARL C. MOLOS - 22-0702-626");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initializeComponents();
        loadCSVData();
        setVisible(true);
    }
    
    private void initializeComponents() {

        
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Table with better column names
        String[] columns = {
            "Student ID", 
            "First Name", 
            "Last Name", 
            "Lab Work 1", 
            "Lab Work 2", 
            "Lab Work 3", 
            "Prelim Exam", 
            "Attendance Grade"
        };
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        
        // Center align table content
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        // Set column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(100);  // Student ID
        table.getColumnModel().getColumn(1).setPreferredWidth(120);  // First Name
        table.getColumnModel().getColumn(2).setPreferredWidth(120);  // Last Name
        table.getColumnModel().getColumn(3).setPreferredWidth(80);   // Lab 1
        table.getColumnModel().getColumn(4).setPreferredWidth(80);   // Lab 2
        table.getColumnModel().getColumn(5).setPreferredWidth(80);   // Lab 3
        table.getColumnModel().getColumn(6).setPreferredWidth(90);   // Prelim
        table.getColumnModel().getColumn(7).setPreferredWidth(110);  // Attendance
        
        // Table appearance
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(102, 126, 234));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(200, 220, 255));
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(102, 126, 234), 2),
            "Student Records",
            0,
            0,
            new Font("Arial", Font.BOLD, 14),
            new Color(102, 126, 234)
        ));
        
        // Input Panel with organized sections
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(102, 126, 234), 2),
            "Add New Student",
            0,
            0,
            new Font("Arial", Font.BOLD, 14),
            new Color(102, 126, 234)
        ));
        
        // Personal Information Section
        JPanel personalPanel = createSectionPanel("Personal Information");
        
        JPanel idPanel = createFieldPanel("Student ID (Numbers only):");
        idField = new JTextField(15);
        idPanel.add(idField);
        
        JPanel firstNamePanel = createFieldPanel("First Name (Letters only):");
        firstNameField = new JTextField(15);
        firstNamePanel.add(firstNameField);
        
        JPanel lastNamePanel = createFieldPanel("Last Name (Letters only):");
        lastNameField = new JTextField(15);
        lastNamePanel.add(lastNameField);
        
        personalPanel.add(idPanel);
        personalPanel.add(firstNamePanel);
        personalPanel.add(lastNamePanel);
        
        // Academic Scores Section
        JPanel scoresPanel = createSectionPanel("Academic Scores (0-100)");
        
        JPanel lab1Panel = createFieldPanel("Lab Work 1:");
        lab1Field = new JTextField(10);
        lab1Field.setText("0");
        lab1Panel.add(lab1Field);
        
        JPanel lab2Panel = createFieldPanel("Lab Work 2:");
        lab2Field = new JTextField(10);
        lab2Field.setText("0");
        lab2Panel.add(lab2Field);
        
        JPanel lab3Panel = createFieldPanel("Lab Work 3:");
        lab3Field = new JTextField(10);
        lab3Field.setText("0");
        lab3Panel.add(lab3Field);
        
        JPanel prelimPanel = createFieldPanel("Prelim Exam:");
        prelimField = new JTextField(10);
        prelimField.setText("0");
        prelimPanel.add(prelimField);
        
        JPanel attendancePanel = createFieldPanel("Attendance Grade:");
        attendanceField = new JTextField(10);
        attendanceField.setText("0");
        attendancePanel.add(attendanceField);
        
        scoresPanel.add(lab1Panel);
        scoresPanel.add(lab2Panel);
        scoresPanel.add(lab3Panel);
        scoresPanel.add(prelimPanel);
        scoresPanel.add(attendancePanel);
        
        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        
        addButton = new JButton("Add Student");
        addButton.setBackground(new Color(102, 126, 234));
        addButton.setForeground(Color.BLUE);
        addButton.setFont(new Font("Arial", Font.BOLD, 13));
        addButton.setFocusPainted(false);
        
        deleteButton = new JButton("Delete Selected");
        deleteButton.setBackground(new Color(248, 80, 50));
        deleteButton.setForeground(Color.RED);
        deleteButton.setFont(new Font("Arial", Font.BOLD, 13));
        deleteButton.setFocusPainted(false);
        
        clearButton = new JButton("Clear Form");
        clearButton.setBackground(new Color(150, 150, 150));
        clearButton.setForeground(Color.RED);
        clearButton.setFont(new Font("Arial", Font.BOLD, 13));
        clearButton.setFocusPainted(false);
        
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        
        // Add sections to input panel
        inputPanel.add(personalPanel);
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(scoresPanel);
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(buttonPanel);
        
        // Event listeners
        final StudentRecordSystem self = this;
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                self.addStudent();
            }
        });
        
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                self.deleteStudent();
            }
        });
        
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                self.clearForm();
            }
        });
        
        // Layout
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(inputPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private JPanel createSectionPanel(String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
            title,
            0,
            0,
            new Font("Arial", Font.BOLD, 12),
            new Color(102, 126, 234)
        ));
        return panel;
    }
    
    private JPanel createFieldPanel(String labelText) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        label.setPreferredSize(new Dimension(200, 25));
        panel.add(label);
        return panel;
    }
    
    private void loadCSVData() {
        try {
            File csvFile = new File("../MOCK_DATA.csv"); // if running from bin folder

            
            if (!csvFile.exists()) {
                JOptionPane.showMessageDialog(this, 
                    "MOCK_DATA.csv not found in current directory.\nThe table will be empty until you add students.",
                    "File Not Found", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            BufferedReader reader = new BufferedReader(new FileReader(csvFile));
            String line;
            
            reader.readLine();
            
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                
                if (data.length >= 8) {
                    tableModel.addRow(data);
                }
            }
            
            reader.close();
            
            JOptionPane.showMessageDialog(this, 
                "Successfully loaded " + tableModel.getRowCount() + " student records.",
                "Data Loaded", 
                JOptionPane.INFORMATION_MESSAGE);
            
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, 
                "Error: CSV file not found.",
                "File Error", 
                JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, 
                "Error reading CSV file.",
                "I/O Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean isValidStudentId(String id) {
        // Check if ID contains only numbers
        return id.matches("[0-9]+");
    }
    
    private boolean isValidName(String name) {
        // Check if name contains only letters and spaces
        return name.matches("[A-Za-z ]+");
    }
    
    private boolean isValidScore(String score) {
        try {
            int num = Integer.parseInt(score);
            return num >= 0 && num <= 100;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    private void addStudent() {
        String id = idField.getText().trim();
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String lab1 = lab1Field.getText().trim();
        String lab2 = lab2Field.getText().trim();
        String lab3 = lab3Field.getText().trim();
        String prelim = prelimField.getText().trim();
        String attendance = attendanceField.getText().trim();
        
        // Validation: Check required fields
        if (id.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please fill in all required fields:\n- Student ID\n- First Name\n- Last Name",
                "Input Error", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Validation: Student ID must be numbers only
        if (!isValidStudentId(id)) {
            JOptionPane.showMessageDialog(this, 
                "Student ID must contain only numbers (integers)!\n\nExample: 22070262",
                "Invalid Student ID", 
                JOptionPane.ERROR_MESSAGE);
            idField.requestFocus();
            return;
        }
        
        // Validation: First name must be letters only
        if (!isValidName(firstName)) {
            JOptionPane.showMessageDialog(this, 
                "First Name must contain only letters!\n\nNo numbers or special characters allowed.",
                "Invalid First Name", 
                JOptionPane.ERROR_MESSAGE);
            firstNameField.requestFocus();
            return;
        }
        
        // Validation: Last name must be letters only
        if (!isValidName(lastName)) {
            JOptionPane.showMessageDialog(this, 
                "Last Name must contain only letters!\n\nNo numbers or special characters allowed.",
                "Invalid Last Name", 
                JOptionPane.ERROR_MESSAGE);
            lastNameField.requestFocus();
            return;
        }
        
        // Validation: All scores must be 0-100
        if (!isValidScore(lab1) || !isValidScore(lab2) || !isValidScore(lab3) || 
            !isValidScore(prelim) || !isValidScore(attendance)) {
            JOptionPane.showMessageDialog(this, 
                "All scores must be numbers between 0 and 100!",
                "Invalid Score", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Check for duplicate Student ID
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 0).toString().equals(id)) {
                JOptionPane.showMessageDialog(this, 
                    "A student with this ID already exists!\n\nStudent ID: " + id,
                    "Duplicate ID", 
                    JOptionPane.WARNING_MESSAGE);
                idField.requestFocus();
                return;
            }
        }
        
        // Add new row
        Object[] newRow = {id, firstName, lastName, lab1, lab2, lab3, prelim, attendance};
        tableModel.addRow(newRow);
        
        // Clear form
        clearForm();
        
        // Success message
        JOptionPane.showMessageDialog(this, 
            "Student added successfully!\n\nName: " + firstName + " " + lastName + "\nStudent ID: " + id,
            "Success", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void deleteStudent() {
        int selectedRow = table.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Please select a row to delete.",
                "No Selection", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String studentName = tableModel.getValueAt(selectedRow, 1) + " " + 
                           tableModel.getValueAt(selectedRow, 2);
        String studentId = tableModel.getValueAt(selectedRow, 0).toString();
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this student?\n\n" +
            "Name: " + studentName + "\n" +
            "Student ID: " + studentId + "\n\n" +
            "This action cannot be undone!",
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            tableModel.removeRow(selectedRow);
            JOptionPane.showMessageDialog(this, 
                "Student record deleted successfully!",
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void clearForm() {
        idField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        lab1Field.setText("0");
        lab2Field.setText("0");
        lab3Field.setText("0");
        prelimField.setText("0");
        attendanceField.setText("0");
        idField.requestFocus();
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new StudentRecordSystem();
            }
        });
    }
}