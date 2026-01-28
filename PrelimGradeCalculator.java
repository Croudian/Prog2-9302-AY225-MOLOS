import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrelimGradeCalculator {

    public static void main(String[] args) {
        // Set system look and feel for better native appearance
        try {
            UIManager.setLookAndFeel(UIManager.getLookAndFeel());
        } catch (Exception e) {
            // Fallback to default
        }
        SwingUtilities.invokeLater(() -> new PrelimGradeCalculator().createGUI());
    }

    private void createGUI() {
        JFrame frame = new JFrame("Prelim Grade Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false); // Keep it fixed-size for consistency

        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255)); // Light blue background
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Input panel (top section)
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBackground(new Color(240, 248, 255));
        inputPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 2), 
            "Enter Your Grades", 
            0, 0, 
            new Font("Arial", Font.BOLD, 14), 
            new Color(70, 130, 180)
        ));

        // Input fields with styling
        JTextField attendanceField = createStyledTextField("e.g., 90");
        JTextField lab1Field = createStyledTextField("e.g., 85");
        JTextField lab2Field = createStyledTextField("e.g., 88");
        JTextField lab3Field = createStyledTextField("e.g., 92");

        inputPanel.add(createLabeledField("Attendance Score (0-100):", attendanceField));
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(createLabeledField("Lab Work 1 Grade (0-100):", lab1Field));
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(createLabeledField("Lab Work 2 Grade (0-100):", lab2Field));
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(createLabeledField("Lab Work 3 Grade (0-100):", lab3Field));

        // Button panel (center section)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 248, 255));
        JButton calculateBtn = new JButton("Calculate Required Scores");
        calculateBtn.setFont(new Font("Arial", Font.BOLD, 14));
        calculateBtn.setBackground(new Color(70, 130, 180));
        calculateBtn.setForeground(Color.WHITE);
        calculateBtn.setFocusPainted(false);
        calculateBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        calculateBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        calculateBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                calculateBtn.setBackground(new Color(100, 149, 237));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                calculateBtn.setBackground(new Color(70, 130, 180));
            }
        });
        buttonPanel.add(calculateBtn);

        // Output panel (bottom section)
        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.setBackground(new Color(240, 248, 255));
        outputPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 2), 
            "Results & Remarks", 
            0, 0, 
            new Font("Arial", Font.BOLD, 14), 
            new Color(70, 130, 180)
        ));
        JTextArea outputArea = new JTextArea(10, 40);
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        outputArea.setBackground(Color.WHITE);
        outputArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        outputPanel.add(scrollPane, BorderLayout.CENTER);

        // Add panels to main panel
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(outputPanel, BorderLayout.SOUTH);

        // Button action listener (logic remains the same)
        calculateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double attendance = getValidInput(attendanceField.getText());
                    double lab1 = getValidInput(lab1Field.getText());
                    double lab2 = getValidInput(lab2Field.getText());
                    double lab3 = getValidInput(lab3Field.getText());

                    double labAvg = (lab1 + lab2 + lab3) / 3.0;
                    double classStanding = (attendance * 0) + (labAvg * 0.6);
                    double requiredForPass = (75 - (classStanding * 0.7)) / 0.3;
                    double requiredForExcellent = (100 - (classStanding * 0.7)) / 0.3;

                    StringBuilder output = new StringBuilder();
                    output.append("Attendance Score: ").append(attendance).append("\n");
                    output.append("Lab Work Grades: ").append(lab1)
                          .append(", ").append(lab2)
                          .append(", ").append(lab3).append("\n");
                    output.append("Lab Work Average: ").append(String.format("%.2f", labAvg)).append("\n");
                    output.append("Class Standing: ").append(String.format("%.2f", classStanding)).append("\n\n");

                    output.append("Required Prelim Exam for Passing (75): ")
                          .append(String.format("%.2f", requiredForPass)).append("\n");
                    
                    // Updated: Show "Not achievable" if requiredForExcellent > 100
                    output.append("Required Prelim Exam for Excellent (100): ");
                    if (requiredForExcellent > 100) {
                        output.append("Not achievable\n");
                    } else {
                        output.append(String.format("%.2f", requiredForExcellent)).append("\n");
                    }

                    output.append("\nRemarks:\n");

                    if (requiredForPass > 100) {
                        output.append("• It is impossible to pass (75) with the current Class Standing.\n");
                    } else if (requiredForPass < 0) {
                        output.append("• You already pass (75) without needing a Prelim Exam score.\n");
                    } else {
                        output.append("• To pass (75), you need at least ")
                              .append(String.format("%.2f", requiredForPass)).append(" in the Prelim Exam.\n");
                    }

                    if (requiredForExcellent > 100) {
                        output.append("• It is impossible to achieve excellent (100) with the current Class Standing.\n");
                    } else if (requiredForExcellent < 0) {
                        output.append("• You already have excellent (100) without needing a Prelim Exam score.\n");
                    } else {
                        output.append("• To achieve excellent (100), you need at least ")
                              .append(String.format("%.2f", requiredForExcellent)).append(" in the Prelim Exam.\n");
                    }

                    outputArea.setText(output.toString());

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame,
                            "Please enter valid numbers between 0 and 100 for all fields.",
                            "Input Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    // Helper to create styled text fields
    private JTextField createStyledTextField(String tooltip) {
        JTextField field = new JTextField(10);
        field.setFont(new Font("Arial", Font.PLAIN, 12));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        field.setToolTipText(tooltip);
        return field;
    }

    // Helper to create labeled fields
    private JPanel createLabeledField(String labelText, JTextField field) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 248, 255));
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(label, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }

    private double getValidInput(String text) {
        double value = Double.parseDouble(text);
        if (value < 0 || value > 100) {
            throw new NumberFormatException();
        }
        return value;
    }
}