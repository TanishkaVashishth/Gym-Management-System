import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

class GymMember implements Serializable {
    private String name;
    private String email;
    private String phone;
    private String packageType;

    public GymMember(String name, String email, String phone, String packageType) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.packageType = packageType;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPackageType() {
        return packageType;
    }
}

class Trainer implements Serializable {
    private String name;
    private String specialization;
    private List<String> availability;

    public Trainer(String name, String specialization, List<String> availability) {
        this.name = name;
        this.specialization = specialization;
        this.availability = availability;
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public List<String> getAvailability() {
        return availability;
    }
}

class WorkoutSession implements Serializable {
    private Trainer trainer;
    private GymMember member;
    private String date;
    private String time;

    public WorkoutSession(Trainer trainer, GymMember member, String date, String time) {
        this.trainer = trainer;
        this.member = member;
        this.date = date;
        this.time = time;
    }

    public GymMember getMember() {
        return member;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}

class Billing implements Serializable {
    private GymMember member;
    private double amount;
    private String date;

    public Billing(GymMember member, double amount, String date) {
        this.member = member;
        this.amount = amount;
        this.date = date;
    }

    public GymMember getMember() {
        return member;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }
}

class InventoryItem implements Serializable {
    private String itemName;
    private int quantity;

    public InventoryItem(String itemName, int quantity) {
        this.itemName = itemName;
        this.quantity = quantity;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

class LoginFrame extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginFrame() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(300, 150));
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(15);
        panel.add(usernameLabel, gbc);
        gbc.gridx++;
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(15);
        panel.add(passwordLabel, gbc);
        gbc.gridx++;
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        panel.add(loginButton, gbc);

        add(panel);
        pack();
        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.equals("admin") && password.equals("tanishka")) {
                dispose();
                SwingUtilities.invokeLater(() -> {
                    GymManagementSystem gymManagementSystem = new GymManagementSystem();
                    gymManagementSystem.setVisible(true);
                });
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

class GradientPanel extends JPanel {
    private Color color1;
    private Color color2;

    public GradientPanel(Color color1, Color color2) {
        this.color1 = color1;
        this.color2 = color2;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        GradientPaint gradientPaint = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
        g2d.setPaint(gradientPaint);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}

public class GymManagementSystem extends JFrame implements ActionListener {
    private JPanel enrolledMembersPanel;
    private JPanel scheduledWorkoutsPanel;
    private JButton addMemberButton;
    private JButton bmiCalculatorButton;
    private JButton deleteButton;
    private JButton scheduleWorkoutButton;
    private JButton viewBillingButton;
    private JButton manageInventoryButton;
    private List<GymMember> members;
    private List<Trainer> trainers;
    private List<WorkoutSession> workoutSessions;
    private List<Billing> billings;
    private List<InventoryItem> inventory;

    public GymManagementSystem() {
        setTitle("Gym Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));

        members = new ArrayList<>();
        trainers = new ArrayList<>();
        workoutSessions = new ArrayList<>();
        billings = new ArrayList<>();
        inventory = new ArrayList<>();

        // Sample trainers
        trainers.add(new Trainer("John Doe", "Strength Training", new ArrayList<>(List.of("09:00", "10:00", "11:00"))));
        trainers.add(new Trainer("Jane Smith", "Yoga", new ArrayList<>(List.of("09:00", "10:00", "11:00"))));

        // Sample inventory
        inventory.add(new InventoryItem("Treadmill", 5));
        inventory.add(new InventoryItem("Dumbbells", 20));

        // New Color Scheme
        Color primaryColor = new Color(173, 216, 230); // Light Blue
        Color secondaryColor = new Color(255, 255, 224); // Light Yellow
        Color textColor = new Color(0, 0, 139); // Dark Blue

        Font titleFont = new Font("Arial", Font.BOLD, 24);
        Font labelFont = new Font("Arial", Font.PLAIN, 16);

        GradientPanel headerPanel = new GradientPanel(primaryColor, secondaryColor);
        JLabel titleLabel = new JLabel("Gym Management System");
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(textColor);
        headerPanel.add(titleLabel);

        enrolledMembersPanel = new JPanel();
        enrolledMembersPanel.setLayout(new BoxLayout(enrolledMembersPanel, BoxLayout.Y_AXIS));
        enrolledMembersPanel.setBackground(secondaryColor);

        JScrollPane scrollPane = new JScrollPane(enrolledMembersPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        addMemberButton = new JButton("Add New Member");
        addMemberButton.addActionListener(this);
        addMemberButton.setBackground(primaryColor);
        addMemberButton.setForeground(textColor);
        addMemberButton.setFont(labelFont);

        bmiCalculatorButton = new JButton("BMI Calculator");
        bmiCalculatorButton.addActionListener(this);
        bmiCalculatorButton.setBackground(primaryColor);
        bmiCalculatorButton.setForeground(textColor);
        bmiCalculatorButton.setFont(labelFont);

        deleteButton = new JButton("Delete Member");
        deleteButton.addActionListener(this);
        deleteButton.setBackground(primaryColor);
        deleteButton.setForeground(textColor);
        deleteButton.setFont(labelFont);

        scheduleWorkoutButton = new JButton("Schedule Workout");
        scheduleWorkoutButton.addActionListener(this);
        scheduleWorkoutButton.setBackground(primaryColor);
        scheduleWorkoutButton.setForeground(textColor);
        scheduleWorkoutButton.setFont(labelFont);

        viewBillingButton = new JButton("View Billing");
        viewBillingButton.addActionListener(this);
        viewBillingButton.setBackground(primaryColor);
        viewBillingButton.setForeground(textColor);
        viewBillingButton.setFont(labelFont);

        manageInventoryButton = new JButton("Manage Inventory");
        manageInventoryButton.addActionListener(this);
        manageInventoryButton.setBackground(primaryColor);
        manageInventoryButton.setForeground(textColor);
        manageInventoryButton.setFont(labelFont);

        // Panel for scheduled workouts
        scheduledWorkoutsPanel = new JPanel();
        scheduledWorkoutsPanel.setLayout(new BoxLayout(scheduledWorkoutsPanel, BoxLayout.Y_AXIS));
        scheduledWorkoutsPanel.setBackground(secondaryColor);
        JScrollPane workoutScrollPane = new JScrollPane(scheduledWorkoutsPanel);
        workoutScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        workoutScrollPane.setBorder(BorderFactory.createTitledBorder("Scheduled Workouts"));

        // Layout setup
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(workoutScrollPane, BorderLayout.EAST);

        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(primaryColor);
        footerPanel.add(addMemberButton);
        footerPanel.add(bmiCalculatorButton);
        footerPanel.add(deleteButton);
        footerPanel.add(scheduleWorkoutButton);
        footerPanel.add(viewBillingButton);
        footerPanel.add(manageInventoryButton);

        add(headerPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);

        loadData();
        refreshEnrolledMembersList();
        refreshScheduledWorkoutsList();
    }

    private void showEnrollmentForm() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(20);
        formPanel.add(nameLabel, gbc);
        gbc.gridx++;
        formPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(20);
        formPanel.add(emailLabel, gbc);
        gbc.gridx++;
        formPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel phoneLabel = new JLabel("Phone:");
        JTextField phoneField = new JTextField(20);
        formPanel.add(phoneLabel, gbc);
        gbc.gridx++;
        formPanel.add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel packageLabel = new JLabel("Package:");
        String[] packageOptions = {"Basic", "Mid", "Premium"};
        JComboBox<String> packageComboBox = new JComboBox<>(packageOptions);
        formPanel.add(packageLabel, gbc);
        gbc.gridx++;
        formPanel.add(packageComboBox, gbc);

        int result = JOptionPane.showConfirmDialog(this, formPanel, "Enroll New Member", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            String packageType = (String) packageComboBox.getSelectedItem();

            GymMember member = new GymMember(name, email, phone, packageType);
            members.add(member);

            refreshEnrolledMembersList();
            saveData();
        }
    }

    private void showBMICalculator() {
        JPanel bmiPanel = new JPanel(new GridBagLayout());
        bmiPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel weightLabel = new JLabel("Weight (kg):");
        JTextField weightField = new JTextField(10);
        bmiPanel.add(weightLabel, gbc);
        gbc.gridx++;
        bmiPanel.add(weightField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel heightLabel = new JLabel("Height (cm):");
        JTextField heightField = new JTextField(10);
        bmiPanel.add(heightLabel, gbc);
        gbc.gridx++;
        bmiPanel.add(heightField, gbc);

        int result = JOptionPane.showConfirmDialog(this, bmiPanel, "BMI Calculator", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                double weight = Double.parseDouble(weightField.getText());
                double height = Double.parseDouble(heightField.getText()) / 100;

                double bmi = weight / (height * height);
                String bmiCategory;

                if (bmi < 18.5) {
                    bmiCategory = "Underweight";
                } else if (bmi < 25) {
                    bmiCategory = "Normal weight";
                } else if (bmi < 30) {
                    bmiCategory = "Overweight";
                } else {
                    bmiCategory = "Obese";
                }

                String message = String.format("BMI: %.2f (%s)", bmi, bmiCategory);
                JOptionPane.showMessageDialog(this, message, "BMI Result", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter numeric values.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showDeleteMemberDialog() {
        if (members.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No members enrolled yet.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] memberNames = members.stream().map(GymMember::getName).toArray(String[]::new);
        String selectedMember = (String) JOptionPane.showInputDialog(this, "Select member to delete:", "Delete Member", JOptionPane.PLAIN_MESSAGE, null, memberNames, memberNames[0]);

        if (selectedMember != null) {
            GymMember memberToDelete = members.stream().filter(m -> m.getName().equals(selectedMember)).findFirst().orElse(null);
            if (memberToDelete != null) {
                int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete " + memberToDelete.getName() + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (confirm == JOptionPane.YES_OPTION) {
                    members.remove(memberToDelete);
                    refreshEnrolledMembersList();
                    saveData();
                }
            }
        }
    }

    private void showWorkoutSchedulingPanel() {
        JPanel schedulingPanel = new JPanel(new GridBagLayout());
        schedulingPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel memberLabel = new JLabel("Select Member:");
        JComboBox<GymMember> memberComboBox = new JComboBox<>(members.toArray(new GymMember[0]));
        schedulingPanel.add(memberLabel, gbc);
        gbc.gridx++;
        schedulingPanel.add(memberComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel trainerLabel = new JLabel("Select Trainer:");
        JComboBox<Trainer> trainerComboBox = new JComboBox<>(trainers.toArray(new Trainer[0]));
        schedulingPanel.add(trainerLabel, gbc);
        gbc.gridx++;
        schedulingPanel.add(trainerComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel dateLabel = new JLabel("Date (YYYY-MM-DD):");
        JTextField dateField = new JTextField(10);
        schedulingPanel.add(dateLabel, gbc);
        gbc.gridx++;
        schedulingPanel.add(dateField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel timeLabel = new JLabel("Time (HH:MM):");
        JTextField timeField = new JTextField(10);
        schedulingPanel.add(timeLabel, gbc);
        gbc.gridx++;
        schedulingPanel.add(timeField, gbc);

        JButton scheduleButton = new JButton("Schedule Workout");
        scheduleButton.addActionListener(e -> {
            GymMember selectedMember = (GymMember) memberComboBox.getSelectedItem();
            Trainer selectedTrainer = (Trainer) trainerComboBox.getSelectedItem();
            String date = dateField.getText();
            String time = timeField.getText();

            if (selectedTrainer != null && selectedMember != null && !date.isEmpty() && !time.isEmpty()) {
                WorkoutSession session = new WorkoutSession(selectedTrainer, selectedMember, date, time);
                workoutSessions.add(session);
                JOptionPane.showMessageDialog(this, "Workout scheduled for " + selectedMember.getName() + " with " + selectedTrainer.getName() + " on " + date + " at " + time + "!", "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshScheduledWorkoutsList(); // Refresh the scheduled workouts list
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        schedulingPanel.add(scheduleButton, gbc);

        JOptionPane.showMessageDialog(this, schedulingPanel, "Schedule Workout", JOptionPane.PLAIN_MESSAGE);
    }

    private void refreshEnrolledMembersList() {
        enrolledMembersPanel.removeAll();

        for (GymMember member : members) {
            JPanel memberPanel = new JPanel(new GridLayout(4, 1));
            memberPanel.setBackground(Color.WHITE);
            memberPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            memberPanel.setPreferredSize(new Dimension(550, 100));

            JLabel nameLabel = new JLabel("Name: " + member.getName());
            JLabel emailLabel = new JLabel("Email: " + member.getEmail());
            JLabel phoneLabel = new JLabel("Phone: " + member.getPhone());
            JLabel packageLabel = new JLabel("Package: " + member.getPackageType());

            nameLabel.setForeground(Color.BLACK);
            emailLabel.setForeground(Color.BLACK);
            phoneLabel.setForeground(Color.BLACK);
            packageLabel.setForeground(Color.BLACK);

            memberPanel.add(nameLabel);
            memberPanel.add(emailLabel);
            memberPanel.add(phoneLabel);
            memberPanel.add(packageLabel);

            enrolledMembersPanel.add(memberPanel);
            enrolledMembersPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        enrolledMembersPanel.revalidate();
        enrolledMembersPanel.repaint();
    }

    private void refreshScheduledWorkoutsList() {
        scheduledWorkoutsPanel.removeAll();

        for (WorkoutSession session : workoutSessions) {
            JPanel sessionPanel = new JPanel(new GridLayout(4, 1));
            sessionPanel.setBackground(Color.WHITE);
            sessionPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            sessionPanel.setPreferredSize(new Dimension(300, 100));

            JLabel memberLabel = new JLabel("Member: " + session.getMember().getName());
            JLabel trainerLabel = new JLabel("Trainer: " + session.getTrainer().getName());
            JLabel dateLabel = new JLabel("Date: " + session.getDate());
            JLabel timeLabel = new JLabel("Time: " + session.getTime());

            memberLabel.setForeground(Color.BLACK);
            trainerLabel.setForeground(Color.BLACK);
            dateLabel.setForeground(Color.BLACK);
            timeLabel.setForeground(Color.BLACK);

            sessionPanel.add(memberLabel);
            sessionPanel.add(trainerLabel);
            sessionPanel.add(dateLabel);
            sessionPanel.add(timeLabel);

            scheduledWorkoutsPanel.add(sessionPanel);
            scheduledWorkoutsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        scheduledWorkoutsPanel.revalidate();
        scheduledWorkoutsPanel.repaint();
    }

    private void showBillingPanel() {
        JPanel billingPanel = new JPanel(new GridBagLayout());
        billingPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel memberLabel = new JLabel("Select Member:");
        JComboBox<GymMember> memberComboBox = new JComboBox<>(members.toArray(new GymMember[0]));
        billingPanel.add(memberLabel, gbc);
        gbc.gridx++;
        billingPanel.add(memberComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel amountLabel = new JLabel("Amount:");
        JTextField amountField = new JTextField(10);
        billingPanel.add(amountLabel, gbc);
        gbc.gridx++;
        billingPanel.add(amountField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel dateLabel = new JLabel("Date (YYYY-MM-DD):");
        JTextField dateField = new JTextField(10);
        billingPanel.add(dateLabel, gbc);
        gbc.gridx++;
        billingPanel.add(dateField, gbc);

        JButton billButton = new JButton("Create Bill");
        billButton.addActionListener(e -> {
            GymMember selectedMember = (GymMember) memberComboBox.getSelectedItem();
            String amountText = amountField.getText();
            String date = dateField.getText();

            if (selectedMember != null && !amountText.isEmpty() && !date.isEmpty()) {
                try {
                    double amount = Double.parseDouble(amountText);
                    Billing billing = new Billing(selectedMember, amount, date);
                    billings.add(billing);
                    JOptionPane.showMessageDialog(this, "Bill created for " + selectedMember.getName() + " of amount $" + amount + " on " + date + "!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid amount. Please enter a numeric value.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        billingPanel.add(billButton, gbc);

        JOptionPane.showMessageDialog(this, billingPanel, "Billing", JOptionPane.PLAIN_MESSAGE);
    }

    private void showInventoryPanel() {
        JPanel inventoryPanel = new JPanel(new GridBagLayout());
        inventoryPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel itemLabel = new JLabel("Item Name:");
        JTextField itemField = new JTextField(10);
        inventoryPanel.add(itemLabel, gbc);
        gbc.gridx++;
        inventoryPanel.add(itemField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel quantityLabel = new JLabel("Quantity:");
        JTextField quantityField = new JTextField(10);
        inventoryPanel.add(quantityLabel, gbc);
        gbc.gridx++;
        inventoryPanel.add(quantityField, gbc);

        JButton addButton = new JButton("Add to Inventory");
        addButton.addActionListener(e -> {
            String itemName = itemField.getText();
            String quantityText = quantityField.getText();

            if (!itemName.isEmpty() && !quantityText.isEmpty()) {
                try {
                    int quantity = Integer.parseInt(quantityText);
                    InventoryItem item = new InventoryItem(itemName, quantity);
                    inventory.add(item);
                    JOptionPane.showMessageDialog(this, "Added " + quantity + " of " + itemName + " to inventory.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid quantity. Please enter a numeric value.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        inventoryPanel.add(addButton, gbc);

        // Display current inventory
        JTextArea inventoryDisplay = new JTextArea(10, 30);
        inventoryDisplay.setEditable(false);
        inventoryDisplay.setText("Current Inventory:\n");
        for (InventoryItem item : inventory) {
            inventoryDisplay.append(item.getItemName() + ": " + item.getQuantity() + "\n");
        }
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        inventoryPanel.add(new JScrollPane(inventoryDisplay), gbc);

        JOptionPane.showMessageDialog(this, inventoryPanel, "Inventory Management", JOptionPane.PLAIN_MESSAGE);
    }

    private void saveData() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("gym_data.ser"))) {
            outputStream.writeObject(members);
            outputStream.writeObject(workoutSessions);
            outputStream.writeObject(billings);
            outputStream.writeObject(inventory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("gym_data.ser"))) {
            members = (List<GymMember>) inputStream.readObject();
            workoutSessions = (List<WorkoutSession>) inputStream.readObject();
            billings = (List<Billing>) inputStream.readObject();
            inventory = (List<InventoryItem>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            members = new ArrayList<>();
            workoutSessions = new ArrayList<>();
            billings = new ArrayList<>();
            inventory = new ArrayList<>();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addMemberButton) {
            showEnrollmentForm();
        } else if (e.getSource() == bmiCalculatorButton) {
            showBMICalculator();
        } else if (e.getSource() == deleteButton) {
            showDeleteMemberDialog();
        } else if (e.getSource() == scheduleWorkoutButton) {
            showWorkoutSchedulingPanel();
        } else if (e.getSource() == viewBillingButton) {
            showBillingPanel();
        } else if (e.getSource() == manageInventoryButton) {
            showInventoryPanel();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }
}