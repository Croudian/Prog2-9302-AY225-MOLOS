import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class PrelimGradeCalculator extends JFrame {

    private JTextField attendanceField;
    private JTextField lab1Field;
    private JTextField lab2Field;
    private JTextField lab3Field;
    private JButton calculateBtn;
    private JButton clearBtn;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            PrelimGradeCalculator calculator = new PrelimGradeCalculator();
            calculator.setVisible(true);
        });
    }

    public PrelimGradeCalculator() {
        setTitle("Prelim Grade Calculator");
        setSize(550, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main panel with gradient background
        GradientPanel mainPanel = new GradientPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Container for the white card
        JPanel cardContainer = new JPanel();
        cardContainer.setOpaque(false);
        cardContainer.setLayout(new BorderLayout());

        // White card panel
        RoundedPanel cardPanel = new RoundedPanel(15);
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = createHeaderPanel();
        cardPanel.add(headerPanel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = createFormPanel();
        cardPanel.add(formPanel, BorderLayout.CENTER);

        cardContainer.add(cardPanel);
        mainPanel.add(cardContainer, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 15, 20));

        // Icon
        JLabel iconLabel = new JLabel("📊");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Title
        JLabel titleLabel = new JLabel("Prelim Grade Calculator");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(45, 55, 72));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Subtitle
        JLabel subtitleLabel = new JLabel("Calculate your required exam score");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitleLabel.setForeground(new Color(113, 128, 150));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(iconLabel);
        headerPanel.add(Box.createVerticalStrut(10));
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(subtitleLabel);

        return headerPanel;
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setBackground(Color.WHITE);
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 30, 30));

        // Attendance field
        attendanceField = createStyledTextField(5);
        JPanel attendancePanel = createInputGroup("✓", "Attendance Count", attendanceField, "out of 5");
        formPanel.add(attendancePanel);
        formPanel.add(Box.createVerticalStrut(15));

        // Lab section title
        JLabel labSectionLabel = new JLabel("Lab Work Scores");
        labSectionLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        labSectionLabel.setForeground(new Color(74, 85, 104));
        labSectionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        labSectionLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        formPanel.add(labSectionLabel);
        formPanel.add(new JSeparator());
        formPanel.add(Box.createVerticalStrut(15));

        // Lab fields
        lab1Field = createStyledTextField(100);
        JPanel lab1Panel = createInputGroup("📝", "Lab Work 1", lab1Field, "%");
        formPanel.add(lab1Panel);
        formPanel.add(Box.createVerticalStrut(15));

        lab2Field = createStyledTextField(100);
        JPanel lab2Panel = createInputGroup("📝", "Lab Work 2", lab2Field, "%");
        formPanel.add(lab2Panel);
        formPanel.add(Box.createVerticalStrut(15));

        lab3Field = createStyledTextField(100);
        JPanel lab3Panel = createInputGroup("📝", "Lab Work 3", lab3Field, "%");
        formPanel.add(lab3Panel);
        formPanel.add(Box.createVerticalStrut(25));

        // Buttons
        JPanel buttonPanel = createButtonPanel();
        formPanel.add(buttonPanel);

        return formPanel;
    }

    private JPanel createInputGroup(String icon, String labelText, JTextField field, String hint) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        // Label with icon
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        labelPanel.setBackground(Color.WHITE);
        
        JLabel iconLabel = new JLabel(icon + " ");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        
        JLabel textLabel = new JLabel(labelText);
        textLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        textLabel.setForeground(new Color(45, 55, 72));
        
        labelPanel.add(iconLabel);
        labelPanel.add(textLabel);

        // Input wrapper with hint
        JPanel inputWrapper = new JPanel(new BorderLayout());
        inputWrapper.setBackground(Color.WHITE);
        inputWrapper.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        JLabel hintLabel = new JLabel(hint);
        hintLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        hintLabel.setForeground(new Color(160, 174, 192));
        hintLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));

        inputWrapper.add(field, BorderLayout.CENTER);
        inputWrapper.add(hintLabel, BorderLayout.EAST);

        panel.add(labelPanel, BorderLayout.NORTH);
        panel.add(inputWrapper, BorderLayout.CENTER);

        return panel;
    }

    private JTextField createStyledTextField(int maxValue) {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(300, 40));
        field.setBackground(new Color(247, 250, 252));
        field.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(new Color(226, 232, 240), 8),
            BorderFactory.createEmptyBorder(8, 15, 8, 60)
        ));
        
        // Only allow integers - block letters, decimals, and special characters
        field.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                // Only allow digits (0-9), backspace, and delete
                if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
                    e.consume(); // Block this character
                }
            }
            
            @Override
            public void keyReleased(KeyEvent e) {
                // Check if value exceeds max and cap at max
                String text = field.getText();
                if (!text.isEmpty()) {
                    try {
                        int value = Integer.parseInt(text);
                        if (value > maxValue) {
                            field.setText(String.valueOf(maxValue));
                        }
                    } catch (NumberFormatException ex) {
                        // Invalid number, clear it
                        field.setText("");
                    }
                }
            }
        });
        
        // Focus effects
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                field.setBackground(Color.WHITE);
                field.setBorder(BorderFactory.createCompoundBorder(
                    new RoundedBorder(new Color(102, 126, 234), 8),
                    BorderFactory.createEmptyBorder(8, 15, 8, 60)
                ));
            }

            @Override
            public void focusLost(FocusEvent e) {
                field.setBackground(new Color(247, 250, 252));
                field.setBorder(BorderFactory.createCompoundBorder(
                    new RoundedBorder(new Color(226, 232, 240), 8),
                    BorderFactory.createEmptyBorder(8, 15, 8, 60)
                ));
            }
        });

        return field;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 12, 0));
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        // Calculate button
        calculateBtn = new GradientButton("Calculate Grade");
        calculateBtn.addActionListener(e -> calculateGrade());

        // Clear button
        clearBtn = new JButton("Clear All");
        clearBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        clearBtn.setBackground(new Color(237, 242, 247));
        clearBtn.setForeground(new Color(74, 85, 104));
        clearBtn.setFocusPainted(false);
        clearBtn.setBorderPainted(false);
        clearBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        clearBtn.addActionListener(e -> clearFields());
        clearBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                clearBtn.setBackground(new Color(226, 232, 240));
            }
            public void mouseExited(MouseEvent e) {
                clearBtn.setBackground(new Color(237, 242, 247));
            }
        });

        panel.add(calculateBtn);
        panel.add(clearBtn);

        return panel;
    }

    private void calculateGrade() {
        try {
            String attendanceText = attendanceField.getText().trim();
            String lab1Text = lab1Field.getText().trim();
            String lab2Text = lab2Field.getText().trim();
            String lab3Text = lab3Field.getText().trim();
            
            if (attendanceText.isEmpty() || lab1Text.isEmpty() || 
                lab2Text.isEmpty() || lab3Text.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "⚠️ Please fill in all fields",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int attendanceCount = Integer.parseInt(attendanceText);
            int lab1 = Integer.parseInt(lab1Text);
            int lab2 = Integer.parseInt(lab2Text);
            int lab3 = Integer.parseInt(lab3Text);

            // Validate ranges
            if (attendanceCount < 0 || attendanceCount > 5 ||
                lab1 < 0 || lab1 > 100 ||
                lab2 < 0 || lab2 > 100 ||
                lab3 < 0 || lab3 > 100) {
                JOptionPane.showMessageDialog(this,
                    "⚠️ Please enter valid whole numbers only\n\n• Attendance: 0-5\n• Lab Work: 0-100",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check for automatic fail (4+ absences)
            if (attendanceCount <= 1) {
                showFailDialog(attendanceCount);
                return;
            }

            // Calculate grades
            int attendance = attendanceCount * 20;
            double labAvg = (lab1 + lab2 + lab3) / 3.0;
            double classStanding = (attendance * 0.4) + (labAvg * 0.6);
            double requiredForPass = (75 - (classStanding * 0.7)) / 0.3;
            double requiredForExcellent = (100 - (classStanding * 0.7)) / 0.3;

            showResultDialog(attendanceCount, attendance, lab1, lab2, lab3, labAvg, 
                           classStanding, requiredForPass, requiredForExcellent);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "⚠️ Please enter valid whole numbers only\n\n• Attendance: 0-5\n• Lab Work: 0-100",
                "Input Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showFailDialog(int attendanceCount) {
        JDialog dialog = new JDialog(this, "Grade Results", true);
        dialog.setSize(500, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JLabel iconLabel = new JLabel("❌");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 64));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel failLabel = new JLabel("FAILED");
        failLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        failLabel.setForeground(new Color(231, 76, 60));
        failLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel absenceLabel = new JLabel("You have " + (5 - attendanceCount) + " absences (Attendance: " + attendanceCount + "/5)");
        absenceLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        absenceLabel.setForeground(new Color(45, 55, 72));
        absenceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel messageLabel = new JLabel("<html><center>Students with 4 or more absences automatically fail the course,<br>regardless of other grades.</center></html>");
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        messageLabel.setForeground(new Color(113, 128, 150));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanel.add(iconLabel);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(failLabel);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(absenceLabel);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(messageLabel);

        dialog.add(contentPanel, BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    private void showResultDialog(int attendanceCount, int attendance, int lab1, int lab2, int lab3,
                                  double labAvg, double classStanding, double requiredForPass, double requiredForExcellent) {
        JDialog dialog = new JDialog(this, "Grade Results", true);
        dialog.setSize(650, 600);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        // Header with gradient
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(102, 126, 234));
        headerPanel.setPreferredSize(new Dimension(650, 70));
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));

        JLabel headerLabel = new JLabel("📈 Grade Results");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);

        // Content panel with scroll
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        // Input Summary Table
        contentPanel.add(createResultTable("📊 Input Summary", new String[][]{
            {"Attendance Count", attendanceCount + " / 5"},
            {"Absences", String.valueOf(5 - attendanceCount)},
            {"Attendance Score", attendance + "%"},
            {"Lab Work 1", String.valueOf(lab1)},
            {"Lab Work 2", String.valueOf(lab2)},
            {"Lab Work 3", String.valueOf(lab3)},
            {"Lab Work Average", String.format("%.2f", labAvg)},
            {"Class Standing (70%)", String.format("%.2f", classStanding)}
        }));

        contentPanel.add(Box.createVerticalStrut(20));

        // Required Scores Table
        String passScore = requiredForPass > 100 ? "❌ Not achievable" : 
                          requiredForPass < 0 ? "✅ Already passed!" : 
                          "✅ " + String.format("%.2f", requiredForPass);
        
        String excellentScore = requiredForExcellent > 100 ? "❌ Not achievable" : 
                               requiredForExcellent < 0 ? "✅ Already achieved!" : 
                               "✅ " + String.format("%.2f", requiredForExcellent);

        contentPanel.add(createResultTable("🎯 Required Prelim Exam Score (30%)", new String[][]{
            {"To Pass (75%)", passScore},
            {"For Excellent (100%)", excellentScore}
        }));

        contentPanel.add(Box.createVerticalStrut(20));

        // Remarks
        JLabel remarksLabel = new JLabel("💡 Remarks");
        remarksLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        remarksLabel.setForeground(new Color(45, 55, 72));
        remarksLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(remarksLabel);
        contentPanel.add(Box.createVerticalStrut(10));

        JPanel remarksPanel = new JPanel();
        remarksPanel.setLayout(new BoxLayout(remarksPanel, BoxLayout.Y_AXIS));
        remarksPanel.setBackground(Color.WHITE);
        remarksPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        if (requiredForPass > 100) {
            remarksPanel.add(createRemarkLabel("Unfortunately, it is impossible to pass (75%) with your current Class Standing. Consider improving attendance and lab work."));
        }
        if (requiredForExcellent > 100 && requiredForPass <= 100) {
            remarksPanel.add(createRemarkLabel("While passing is achievable, reaching excellent (100%) is not possible with your current Class Standing."));
        }
        if (requiredForPass >= 0 && requiredForPass <= 100) {
            remarksPanel.add(createRemarkLabel("To pass with 75%, you need at least " + String.format("%.2f", requiredForPass) + " points in the Prelim Exam."));
        }
        if (requiredForExcellent >= 0 && requiredForExcellent <= 100) {
            remarksPanel.add(createRemarkLabel("To achieve excellent (100%), you need at least " + String.format("%.2f", requiredForExcellent) + " points in the Prelim Exam."));
        }
        if (requiredForPass < 0) {
            remarksPanel.add(createRemarkLabel("Great job! You've already secured a passing grade. Keep up the excellent work!"));
        }

        contentPanel.add(remarksPanel);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        dialog.add(headerPanel, BorderLayout.NORTH);
        dialog.add(scrollPane, BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    private JPanel createResultTable(String title, String[][] data) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBackground(new Color(102, 126, 234));
        titleLabel.setOpaque(true);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 1));

        for (int i = 0; i < data.length; i++) {
            JPanel rowPanel = new JPanel(new BorderLayout());
            rowPanel.setBackground(i % 2 == 0 ? Color.WHITE : new Color(247, 250, 252));
            rowPanel.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));

            JLabel keyLabel = new JLabel(data[i][0]);
            keyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            keyLabel.setForeground(new Color(74, 85, 104));

            JLabel valueLabel = new JLabel(data[i][1]);
            valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
            valueLabel.setForeground(new Color(45, 55, 72));

            rowPanel.add(keyLabel, BorderLayout.WEST);
            rowPanel.add(valueLabel, BorderLayout.EAST);

            tablePanel.add(rowPanel);
            
            if (i < data.length - 1) {
                JSeparator sep = new JSeparator();
                sep.setForeground(new Color(226, 232, 240));
                tablePanel.add(sep);
            }
        }

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(tablePanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createRemarkLabel(String text) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(247, 250, 252));
        panel.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(new Color(102, 126, 234), 8),
            BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel label = new JLabel("<html>→ " + text + "</html>");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setForeground(new Color(45, 55, 72));
        panel.add(label, BorderLayout.CENTER);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(Color.WHITE);
        wrapper.add(panel, BorderLayout.CENTER);
        wrapper.add(Box.createVerticalStrut(8), BorderLayout.SOUTH);
        wrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));

        return wrapper;
    }

    private void clearFields() {
        attendanceField.setText("");
        lab1Field.setText("");
        lab2Field.setText("");
        lab3Field.setText("");
    }

    // Custom gradient panel
    class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            int w = getWidth();
            int h = getHeight();
            GradientPaint gp = new GradientPaint(0, 0, new Color(102, 126, 234), w, h, new Color(118, 75, 162));
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, w, h);
        }
    }

    // Custom gradient button
    class GradientButton extends JButton {
        public GradientButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setForeground(Color.WHITE);
            setFont(new Font("Segoe UI", Font.BOLD, 14));
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            GradientPaint gp = new GradientPaint(0, 0, new Color(102, 126, 234), getWidth(), getHeight(), new Color(118, 75, 162));
            g2.setPaint(gp);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // Rounded panel
    class RoundedPanel extends JPanel {
        private int radius;

        public RoundedPanel(int radius) {
            this.radius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radius, radius));
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // Rounded border
    class RoundedBorder extends AbstractBorder {
        private Color color;
        private int radius;

        public RoundedBorder(Color color, int radius) {
            this.color = color;
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.draw(new RoundRectangle2D.Float(x, y, width - 1, height - 1, radius, radius));
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(2, 2, 2, 2);
        }
    }
}