package com.sariously.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DashboardView extends JFrame {

    public DashboardView() {

        setTitle("Sari-Ously - Dashboard");
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

        JButton logoutButton = new JButton("Log Out");

        logoutButton.setFocusPainted(false);
        logoutButton.setFont(new Font("SansSerif", Font.BOLD, 13));

        logoutButton.addActionListener(e -> {
            new LoginView();
            dispose();
        });

        headerPanel.add(logoutButton, BorderLayout.EAST);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(245, 247, 250));
        contentPanel.setBorder(new EmptyBorder(35, 45, 35, 45));

        JLabel welcomeLabel = new JLabel("Dashboard");

        welcomeLabel.setFont(
                new Font("SansSerif", Font.BOLD, 32)
        );

        welcomeLabel.setForeground(
                new Color(11, 72, 144)
        );

        contentPanel.add(
                welcomeLabel,
                BorderLayout.NORTH
        );

        JPanel cardsPanel = new JPanel(
                new GridLayout(1, 3, 20, 20)
        );

        cardsPanel.setBackground(
                new Color(245, 247, 250)
        );

        JPanel inventoryCard = new JPanel();
        inventoryCard.setLayout(
                new BoxLayout(
                        inventoryCard,
                        BoxLayout.Y_AXIS
                )
        );

        inventoryCard.setBackground(Color.WHITE);
        inventoryCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(220, 224, 230)
                        ),
                        new EmptyBorder(25, 25, 25, 25)
                )
        );

        JLabel inventoryTitle = new JLabel("Inventory");
        inventoryTitle.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        inventoryTitle.setFont(
                new Font("SansSerif", Font.BOLD, 20)
        );

        inventoryTitle.setForeground(
                new Color(11, 72, 144)
        );

        inventoryCard.add(inventoryTitle);

        inventoryCard.add(
                Box.createVerticalStrut(15)
        );

        JLabel inventoryText = new JLabel(
                "Manage your store products."
        );

        inventoryText.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        inventoryCard.add(inventoryText);

        JPanel salesCard = new JPanel();
        salesCard.setLayout(
                new BoxLayout(
                        salesCard,
                        BoxLayout.Y_AXIS
                )
        );

        salesCard.setBackground(Color.WHITE);
        salesCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(220, 224, 230)
                        ),
                        new EmptyBorder(25, 25, 25, 25)
                )
        );

        JLabel salesTitle = new JLabel("Sales");
        salesTitle.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        salesTitle.setFont(
                new Font("SansSerif", Font.BOLD, 20)
        );

        salesTitle.setForeground(
                new Color(11, 72, 144)
        );

        salesCard.add(salesTitle);

        salesCard.add(
                Box.createVerticalStrut(15)
        );

        JLabel salesText = new JLabel(
                "Record and monitor sales."
        );

        salesText.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        salesCard.add(salesText);

        JPanel reportsCard = new JPanel();
        reportsCard.setLayout(
                new BoxLayout(
                        reportsCard,
                        BoxLayout.Y_AXIS
                )
        );

        reportsCard.setBackground(Color.WHITE);
        reportsCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(220, 224, 230)
                        ),
                        new EmptyBorder(25, 25, 25, 25)
                )
        );

        JLabel reportsTitle = new JLabel("Reports");
        reportsTitle.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        reportsTitle.setFont(
                new Font("SansSerif", Font.BOLD, 20)
        );

        reportsTitle.setForeground(
                new Color(11, 72, 144)
        );

        reportsCard.add(reportsTitle);

        reportsCard.add(
                Box.createVerticalStrut(15)
        );

        JLabel reportsText = new JLabel(
                "View store reports."
        );

        reportsText.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        reportsCard.add(reportsText);

        cardsPanel.add(inventoryCard);
        cardsPanel.add(salesCard);
        cardsPanel.add(reportsCard);

        contentPanel.add(
                cardsPanel,
                BorderLayout.CENTER
        );

        mainPanel.add(
                contentPanel,
                BorderLayout.CENTER
        );

        add(mainPanel);

        setVisible(true);
    }
}