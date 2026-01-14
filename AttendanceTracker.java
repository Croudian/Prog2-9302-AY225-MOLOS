import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class AttendanceTracker {

    static DefaultTableModel tableModel;
    static JFrame listFrame;

    public static void main(String[] args) {

        // ===== DATE / MONTH / TIME FORMAT =====
        DateTimeFormatter dateTimeFormat =
                DateTimeFormatter.ofPattern("MM/dd/yyyy - hh:mm:ss a");

        // ===== COLORS (LoL THEME) =====
        Color bgDark = new Color(10, 20, 35);
        Color gold = new Color(200, 170, 110);
        Color textLight = new Color(220, 220, 220);

        // ===== MAIN FRAME =====
        JFrame frame = new JFrame("Attendance Tracker");
        frame.setSize(460, 330);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(bgDark);

        // ===== LABELS =====
        JLabel nameLabel = createLabel("Attendance Name:", textLight);
        JLabel courseLabel = createLabel("Course:", textLight);
        JLabel yearLabel = createLabel("Year:", textLight);
        JLabel timeLabel = createLabel("Time In:", textLight);
        JLabel signatureLabel = createLabel("E-Signature:", textLight);

        // ===== INPUT FIELDS =====
        JTextField nameField = createTextField(bgDark, gold, textLight);

        // Changed from JComboBox to JTextField for manual course entry
        JTextField courseField = createTextField(bgDark, gold, textLight);

        String[] years = {"-- Select Year --", "1st Year", "2nd Year", "3rd Year", "4th Year"};
        JComboBox<String> yearBox = createComboBox(years, bgDark, gold, textLight);

        JTextField timeField = createTextField(bgDark, gold, textLight);
        JTextField signatureField = createTextField(bgDark, gold, textLight);

        // ===== AUTO DATE / TIME =====
        timeField.setText(LocalDateTime.now().format(dateTimeFormat));
        timeField.setEditable(false);

        // ===== AUTO E-SIGNATURE =====
        signatureField.setText(UUID.randomUUID().toString());
        signatureField.setEditable(false);

        JButton enterButton = new JButton("ENTER");
        enterButton.setBackground(bgDark);
        enterButton.setForeground(gold);
        enterButton.setFocusPainted(false);
        enterButton.setBorder(BorderFactory.createLineBorder(gold, 2));

        // ===== ADD COMPONENTS =====
        panel.add(nameLabel);
        panel.add(nameField);

        panel.add(courseLabel);
        panel.add(courseField);  // Now a text field instead of combo box

        panel.add(yearLabel);
        panel.add(yearBox);

        panel.add(timeLabel);
        panel.add(timeField);

        panel.add(signatureLabel);
        panel.add(signatureField);

        panel.add(new JLabel());
        panel.add(enterButton);

        frame.add(panel);
        frame.setVisible(true);

        // ===== ATTENDANCE LIST FRAME =====
        listFrame = new JFrame("Attendance List");
        listFrame.setSize(750, 300);
        listFrame.setLocationRelativeTo(null);

        String[] columns = {"Name", "Course", "Year", "Date / Time", "E-Signature"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);

        table.setBackground(bgDark);
        table.setForeground(textLight);
        table.setGridColor(gold);
        table.setSelectionBackground(gold);
        table.setSelectionForeground(Color.BLACK);

        JTableHeader header = table.getTableHeader();
        header.setBackground(bgDark);
        header.setForeground(gold);
        header.setFont(new Font("Serif", Font.BOLD, 14));

        listFrame.add(new JScrollPane(table));
        listFrame.getContentPane().setBackground(bgDark);

        // ===== ENTER BUTTON ACTION =====
        enterButton.addActionListener(e -> {

            if (nameField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter your name.");
                return;
            }

            // Updated validation: Check if course field is empty instead of combo box selection
            if (courseField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter your course.");
                return;
            }

            if (yearBox.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(frame, "Please select year.");
                return;
            }

            // Add attendance record
            tableModel.addRow(new Object[]{
                    nameField.getText(),
                    courseField.getText(),  // Use text from field instead of selected item
                    yearBox.getSelectedItem(),
                    timeField.getText(),
                    signatureField.getText()
            });

            listFrame.setVisible(true);

            // Reset fields
            nameField.setText("");
            courseField.setText("");  // Clear the course field
            yearBox.setSelectedIndex(0);
            timeField.setText(LocalDateTime.now().format(dateTimeFormat));
            signatureField.setText(UUID.randomUUID().toString());
        });
    }

    // ===== UI HELPER METHODS =====
    static JLabel createLabel(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setForeground(color);
        return label;
    }

    static JTextField createTextField(Color bg, Color border, Color fg) {
        JTextField field = new JTextField();
        field.setBackground(bg);
        field.setForeground(fg);
        field.setCaretColor(fg);
        field.setBorder(BorderFactory.createLineBorder(border));
        return field;
    }

    static JComboBox<String> createComboBox(String[] items, Color bg, Color border, Color fg) {
        JComboBox<String> box = new JComboBox<>(items);
        box.setBackground(bg);
        box.setForeground(fg);
        box.setBorder(BorderFactory.createLineBorder(border));
        return box;
    }
}