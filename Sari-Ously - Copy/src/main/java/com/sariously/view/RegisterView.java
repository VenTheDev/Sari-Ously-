package com.sariously.view;

import com.sariously.database.UserDAO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RegisterView extends JFrame {

    public RegisterView() {
        setTitle("Sari-Ously - Register");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 247, 250));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(11, 72, 144));
        headerPanel.setBorder(new EmptyBorder(20, 40, 20, 40));
        headerPanel.setPreferredSize(new Dimension(0, 90));

        JLabel brandLabel = new JLabel(
                "<html><b>SARI-OUSLY?</b><br><small>TINDAHAN NG BARANGAY</small></html>"
        );
        brandLabel.setForeground(Color.WHITE);
        brandLabel.setFont(new Font("SansSerif", Font.BOLD, 20));

        headerPanel.add(brandLabel, BorderLayout.WEST);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(245, 247, 250));

        JPanel registerCard = new JPanel();
        registerCard.setLayout(new BoxLayout(registerCard, BoxLayout.Y_AXIS));
        registerCard.setBackground(Color.WHITE);
        registerCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(220, 224, 230)),
                        new EmptyBorder(35, 45, 35, 45)
                )
        );

        registerCard.setPreferredSize(new Dimension(420, 560));
        registerCard.setMinimumSize(new Dimension(420, 560));
        registerCard.setMaximumSize(new Dimension(420, 560));

        JLabel registerTitle = new JLabel("Create Account");
        registerTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerTitle.setFont(new Font("SansSerif", Font.BOLD, 28));
        registerTitle.setForeground(new Color(11, 72, 144));

        registerCard.add(registerTitle);
        registerCard.add(Box.createVerticalStrut(8));

        JLabel registerSubtitle = new JLabel(
                "Register to start managing your store."
        );
        registerSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerSubtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        registerSubtitle.setForeground(new Color(100, 100, 100));

        registerCard.add(registerSubtitle);
        registerCard.add(Box.createVerticalStrut(25));

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        usernameLabel.setForeground(new Color(33, 37, 41));

        registerCard.add(usernameLabel);
        registerCard.add(Box.createVerticalStrut(8));

        JTextField usernameField = new JTextField();
        usernameField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        usernameField.setMaximumSize(new Dimension(300, 40));
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernameField.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(200, 205, 210)),
                        new EmptyBorder(8, 10, 8, 10)
                )
        );

        registerCard.add(usernameField);
        registerCard.add(Box.createVerticalStrut(18));

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        passwordLabel.setForeground(new Color(33, 37, 41));

        registerCard.add(passwordLabel);
        registerCard.add(Box.createVerticalStrut(8));

        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        passwordField.setMaximumSize(new Dimension(300, 40));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordField.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(200, 205, 210)),
                        new EmptyBorder(8, 10, 8, 10)
                )
        );

        registerCard.add(passwordField);
        registerCard.add(Box.createVerticalStrut(18));

        JLabel confirmPasswordLabel = new JLabel("Confirm Password");
        confirmPasswordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmPasswordLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        confirmPasswordLabel.setForeground(new Color(33, 37, 41));

        registerCard.add(confirmPasswordLabel);
        registerCard.add(Box.createVerticalStrut(8));

        JPasswordField confirmPasswordField = new JPasswordField();
        confirmPasswordField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        confirmPasswordField.setMaximumSize(new Dimension(300, 40));
        confirmPasswordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmPasswordField.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(200, 205, 210)),
                        new EmptyBorder(8, 10, 8, 10)
                )
        );

        registerCard.add(confirmPasswordField);
        registerCard.add(Box.createVerticalStrut(8));

        JCheckBox showPassword = new JCheckBox("Show password");
        showPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        showPassword.setOpaque(false);
        showPassword.setFont(new Font("SansSerif", Font.PLAIN, 13));

        showPassword.addActionListener(e -> {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
                confirmPasswordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('•');
                confirmPasswordField.setEchoChar('•');
            }
        });

        registerCard.add(showPassword);
        registerCard.add(Box.createVerticalStrut(20));

        JButton registerButton = new JButton("Register");
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerButton.setPreferredSize(new Dimension(150, 42));
        registerButton.setMaximumSize(new Dimension(150, 42));
        registerButton.setBackground(new Color(13, 110, 253));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        registerButton.setFocusPainted(false);

        registerButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please fill in all fields."
                );
                return;
            }

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(
                        this,
                        "Passwords do not match."
                );
                return;
            }

            boolean registered = UserDAO.registerUser(username, password);

            if (registered) {
                JOptionPane.showMessageDialog(
                        this,
                        "Registration successful!"
                );

                new LoginView();
                dispose();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Username already exists."
                );
            }
        });

        registerCard.add(registerButton);
        registerCard.add(Box.createVerticalStrut(20));

        JLabel loginLabel = new JLabel(
                "<html><center>Already have an account? <b>Log In</b></center></html>",
                SwingConstants.CENTER
        );

        loginLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        loginLabel.setForeground(new Color(13, 110, 253));
        loginLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        loginLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                new LoginView();
                dispose();
            }
        });

        registerCard.add(loginLabel);
        registerCard.add(Box.createVerticalStrut(15));

        JButton backButton = new JButton("Back to Home");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setPreferredSize(new Dimension(130, 35));
        backButton.setMaximumSize(new Dimension(130, 35));
        backButton.setFocusPainted(false);

        backButton.addActionListener(e -> {
            new HomeView();
            dispose();
        });

        registerCard.add(backButton);

        centerPanel.add(registerCard);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        add(mainPanel);

        setVisible(true);
    }
}