package com.sariously.view;

import com.sariously.database.UserDAO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginView extends JFrame {

    public LoginView() {

        setTitle("Sari-Ously - Login");
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

        JPanel loginCard = new JPanel();
        loginCard.setLayout(new BoxLayout(loginCard, BoxLayout.Y_AXIS));
        loginCard.setBackground(Color.WHITE);

        loginCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(220, 224, 230)
                        ),
                        new EmptyBorder(40, 45, 40, 45)
                )
        );

        loginCard.setPreferredSize(new Dimension(420, 480));
        loginCard.setMinimumSize(new Dimension(420, 480));
        loginCard.setMaximumSize(new Dimension(420, 480));

        JLabel loginTitle = new JLabel("Welcome Back");

        loginTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginTitle.setFont(new Font("SansSerif", Font.BOLD, 28));
        loginTitle.setForeground(new Color(11, 72, 144));

        loginCard.add(loginTitle);

        JLabel loginSubtitle = new JLabel(
                "Log in to manage your sari-sari store."
        );

        loginSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginSubtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        loginSubtitle.setForeground(new Color(100, 100, 100));

        loginCard.add(Box.createVerticalStrut(8));
        loginCard.add(loginSubtitle);
        loginCard.add(Box.createVerticalStrut(30));

        JLabel usernameLabel = new JLabel("Username");

        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        usernameLabel.setForeground(new Color(33, 37, 41));

        loginCard.add(usernameLabel);
        loginCard.add(Box.createVerticalStrut(8));

        JTextField usernameField = new JTextField();

        usernameField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        usernameField.setMaximumSize(new Dimension(300, 40));
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);

        usernameField.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(200, 205, 210)
                        ),
                        new EmptyBorder(8, 10, 8, 10)
                )
        );

        loginCard.add(usernameField);
        loginCard.add(Box.createVerticalStrut(20));

        JLabel passwordLabel = new JLabel("Password");

        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        passwordLabel.setForeground(new Color(33, 37, 41));

        loginCard.add(passwordLabel);
        loginCard.add(Box.createVerticalStrut(8));

        JPasswordField passwordField = new JPasswordField();

        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        passwordField.setMaximumSize(new Dimension(300, 40));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);

        passwordField.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(200, 205, 210)
                        ),
                        new EmptyBorder(8, 10, 8, 10)
                )
        );

        loginCard.add(passwordField);
        loginCard.add(Box.createVerticalStrut(8));

        JCheckBox showPassword = new JCheckBox("Show password");

        showPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        showPassword.setOpaque(false);
        showPassword.setFont(new Font("SansSerif", Font.PLAIN, 13));

        showPassword.addActionListener(e -> {

            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('•');
            }

        });

        loginCard.add(showPassword);
        loginCard.add(Box.createVerticalStrut(25));

        JButton loginButton = new JButton("Log In");

        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setPreferredSize(new Dimension(150, 42));
        loginButton.setMaximumSize(new Dimension(150, 42));
        loginButton.setBackground(new Color(13, 110, 253));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        loginButton.setFocusPainted(false);

        loginButton.addActionListener(e -> {

            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter your username and password."
                );

                return;
            }

            boolean loggedIn = UserDAO.loginUser(
                    username,
                    password
            );

            if (loggedIn) {

                JOptionPane.showMessageDialog(
                        this,
                        "Login successful!"
                );

                new DashboardView();
                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid username or password."
                );
            }

        });

        loginCard.add(loginButton);
        loginCard.add(Box.createVerticalStrut(25));

        JLabel registerLabel = new JLabel(
                "<html><center>Don't have an account? <b>Register</b></center></html>",
                SwingConstants.CENTER
        );

        registerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        registerLabel.setForeground(new Color(13, 110, 253));
        registerLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        registerLabel.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            java.awt.event.MouseEvent e
                    ) {

                        new RegisterView();
                        dispose();

                    }
                }
        );

        loginCard.add(registerLabel);
        loginCard.add(Box.createVerticalStrut(15));

        JButton backButton = new JButton("Back to Home");

        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setPreferredSize(new Dimension(130, 35));
        backButton.setMaximumSize(new Dimension(130, 35));
        backButton.setFocusPainted(false);

        backButton.addActionListener(e -> {

            new HomeView();
            dispose();

        });

        loginCard.add(backButton);

        centerPanel.add(loginCard);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        add(mainPanel);

        setVisible(true);
    }
}