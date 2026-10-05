package com.sariously.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class HomeView extends JFrame {

    public HomeView() {

        setTitle("Sari-Ously");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Theme.CREAM);



        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 10, 5)
        );

        buttonPanel.setOpaque(false);

        JButton loginButton = new Ui.Btn("Log In", null, Color.WHITE, new Color(120, 160, 220), 60);
        JButton registerButton = new Ui.Btn("Register", Theme.YELLOW, Theme.NAVY, null, 60);

        loginButton.setPreferredSize(
                new Dimension(90, 35)
        );

        registerButton.setPreferredSize(
                new Dimension(100, 35)
        );

        loginButton.addActionListener(e -> {
            new LoginView();
            dispose();
        });

        registerButton.addActionListener(e -> {
            new RegisterView();
            dispose();
        });

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        mainPanel.add(Ui.topBar(null, buttonPanel, 0, 0), BorderLayout.NORTH);

        JPanel landingPanel = new JPanel();

        landingPanel.setLayout(
                new BoxLayout(
                        landingPanel,
                        BoxLayout.Y_AXIS
                )
        );

        landingPanel.setBackground(
                Theme.CREAM
        );

        landingPanel.setBorder(
                new EmptyBorder(30, 40, 30, 40)
        );

        JPanel heroCard = new JPanel(
                new BorderLayout()
        );

        heroCard.setBackground(
                new Color(254, 203, 76)
        );

        heroCard.setBorder(
                new EmptyBorder(35, 45, 35, 45)
        );

        heroCard.setPreferredSize(
                new Dimension(0, 250)
        );

        heroCard.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        250
                )
        );

        JPanel heroText = new JPanel();

        heroText.setLayout(
                new BoxLayout(
                        heroText,
                        BoxLayout.Y_AXIS
                )
        );

        heroText.setOpaque(false);

        JLabel tagline = new JLabel(
                "WELCOME TO SARI-OUSLY!"
        );

        tagline.setFont(
                new Font("SansSerif", Font.BOLD, 14)
        );

        tagline.setForeground(
                new Color(33, 37, 41)
        );

        heroText.add(tagline);

        heroText.add(
                Box.createVerticalStrut(8)
        );

        JLabel mainHeading = new JLabel(
                "<html>Tingi-tingi system for your<br>Barangay Store.</html>"
        );

        mainHeading.setFont(
                new Font("SansSerif", Font.BOLD, 32)
        );

        mainHeading.setForeground(
                new Color(11, 72, 144)
        );

        heroText.add(mainHeading);

        heroText.add(
                Box.createVerticalStrut(10)
        );

        JLabel description = new JLabel(
                "Manage stock, track sales, and calculate profits effortlessly."
        );

        description.setFont(
                new Font("SansSerif", Font.PLAIN, 15)
        );

        description.setForeground(
                new Color(33, 37, 41)
        );

        heroText.add(description);

        heroText.add(
                Box.createVerticalStrut(20)
        );

        JButton getStartedButton = new JButton(
                "Get Started"
        );

        getStartedButton.setPreferredSize(
                new Dimension(130, 40)
        );

        getStartedButton.addActionListener(e -> {
            new LoginView();
            dispose();
        });

        heroText.add(getStartedButton);

        heroCard.add(
                heroText,
                BorderLayout.CENTER
        );

        landingPanel.add(heroCard);

        landingPanel.add(
                Box.createVerticalStrut(25)
        );

        JPanel featuresPanel = new JPanel(
                new GridLayout(1, 3, 20, 0)
        );

        featuresPanel.setOpaque(false);

        featuresPanel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        150
                )
        );

        JPanel salesCard = createFeatureCard(
                "Quick POS Sales",
                "Process customer purchases quickly and easily."
        );

        JPanel inventoryCard = createFeatureCard(
                "Inventory Management",
                "Keep track of products and available stock."
        );

        JPanel analyticsCard = createFeatureCard(
                "Sales Analytics",
                "Monitor sales and understand your store performance."
        );

        featuresPanel.add(salesCard);
        featuresPanel.add(inventoryCard);
        featuresPanel.add(analyticsCard);

        landingPanel.add(featuresPanel);

        landingPanel.add(
                Box.createVerticalStrut(25)
        );

        JPanel infoSection = new JPanel(
                new BorderLayout()
        );

        infoSection.setBackground(
                new Color(11, 72, 144)
        );

        infoSection.setBorder(
                new EmptyBorder(45, 60, 45, 60)
        );

        infoSection.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        300
                )
        );

        JLabel infoTitle = new JLabel(
                "<html><center>Everything You Need<br>for Your Store</center></html>",
                SwingConstants.CENTER
        );

        infoTitle.setForeground(
                Color.WHITE
        );

        infoTitle.setFont(
                new Font("SansSerif", Font.BOLD, 28)
        );

        infoSection.add(
                infoTitle,
                BorderLayout.NORTH
        );

        JLabel infoDescription = new JLabel(
                "<html><center>From managing your products to tracking sales,<br>"
                + "Sari-Ously helps make your daily store operations easier.</center></html>",
                SwingConstants.CENTER
        );

        infoDescription.setForeground(
                Color.WHITE
        );

        infoDescription.setFont(
                new Font("SansSerif", Font.PLAIN, 15)
        );

        infoSection.add(
                infoDescription,
                BorderLayout.CENTER
        );

        JPanel infoCards = new JPanel(
                new GridLayout(1, 3, 40, 0)
        );

        infoCards.setOpaque(false);

        JLabel stockInfo = new JLabel(
                "<html><center><b>Manage Stock</b><br>"
                + "Keep your products organized.</center></html>",
                SwingConstants.CENTER
        );

        stockInfo.setForeground(Color.WHITE);

        stockInfo.setFont(
                new Font("SansSerif", Font.PLAIN, 14)
        );

        JLabel salesInfo = new JLabel(
                "<html><center><b>Track Sales</b><br>"
                + "Record every transaction.</center></html>",
                SwingConstants.CENTER
        );

        salesInfo.setForeground(Color.WHITE);

        salesInfo.setFont(
                new Font("SansSerif", Font.PLAIN, 14)
        );

        JLabel profitInfo = new JLabel(
                "<html><center><b>Monitor Profits</b><br>"
                + "Understand your store performance.</center></html>",
                SwingConstants.CENTER
        );

        profitInfo.setForeground(Color.WHITE);

        profitInfo.setFont(
                new Font("SansSerif", Font.PLAIN, 14)
        );

        infoCards.add(stockInfo);
        infoCards.add(salesInfo);
        infoCards.add(profitInfo);

        infoSection.add(
                infoCards,
                BorderLayout.SOUTH
        );

        landingPanel.add(infoSection);

        JPanel footer = new JPanel(
                new BorderLayout()
        );

        footer.setBackground(
                new Color(11, 72, 144)
        );

        footer.setBorder(
                new EmptyBorder(20, 30, 20, 30)
        );

        footer.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        80
                )
        );

        JLabel footerText = new JLabel(
                "<html><b>SARI-OUSLY?</b> &nbsp; Tindahan ng Barangay<br>"
                + "<small>Simple tools for better store management.</small></html>"
        );

        footerText.setForeground(
                Color.WHITE
        );

        footerText.setFont(
                new Font("SansSerif", Font.PLAIN, 13)
        );

        footer.add(
                footerText,
                BorderLayout.WEST
        );

        JLabel copyright = new JLabel(
                "Sari-Ously • Java Project"
        );

        copyright.setForeground(
                new Color(220, 230, 240)
        );

        copyright.setFont(
                new Font("SansSerif", Font.PLAIN, 12)
        );

        footer.add(
                copyright,
                BorderLayout.EAST
        );

        ScrollablePanel pagePanel = new ScrollablePanel();

        pagePanel.setLayout(
                new BoxLayout(
                        pagePanel,
                        BoxLayout.Y_AXIS
                )
        );

        pagePanel.setBackground(
                Theme.CREAM
        );

        pagePanel.add(landingPanel);

        pagePanel.add(
                Box.createVerticalGlue()
        );

        pagePanel.add(footer);

        JScrollPane scrollPane = new JScrollPane(
                pagePanel
        );

        scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        );

        scrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        scrollPane.setBorder(null);

        scrollPane.getVerticalScrollBar().setUnitIncrement(30);

        scrollPane.getVerticalScrollBar().setBlockIncrement(120);

        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        scrollPane.getViewport().addChangeListener(e -> {

            int viewportHeight =
                    scrollPane.getViewport().getHeight();

            int contentHeight =
                    pagePanel.getPreferredSize().height;

            int requiredHeight =
                    Math.max(
                            viewportHeight,
                            contentHeight
                    );

            Dimension size =
                    new Dimension(
                            pagePanel.getWidth(),
                            requiredHeight
                    );

            pagePanel.setPreferredSize(size);

            pagePanel.revalidate();
        });

        add(mainPanel);

        setVisible(true);
    }

    private JPanel createFeatureCard(
            String title,
            String description
    ) {

        JPanel card = new JPanel();

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );

        card.setBackground(
                Color.WHITE
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(220, 224, 230)
                        ),
                        new EmptyBorder(
                                25,
                                25,
                                25,
                                25
                        )
                )
        );

        JLabel titleLabel = new JLabel(title);

        titleLabel.setFont(
                new Font("SansSerif", Font.BOLD, 18)
        );

        titleLabel.setForeground(
                new Color(11, 72, 144)
        );

        JLabel descriptionLabel = new JLabel(
                "<html>" + description + "</html>"
        );

        descriptionLabel.setFont(
                new Font("SansSerif", Font.PLAIN, 14)
        );

        descriptionLabel.setForeground(
                new Color(33, 37, 41)
        );

        card.add(titleLabel);

        card.add(
                Box.createVerticalStrut(10)
        );

        card.add(descriptionLabel);

        return card;
    }

    private static class ScrollablePanel extends JPanel implements Scrollable {

        @Override
        public Dimension getPreferredScrollableViewportSize() {
            return getPreferredSize();
        }

        @Override
        public int getScrollableUnitIncrement(
                Rectangle visibleRect,
                int orientation,
                int direction
        ) {
            return 30;
        }

        @Override
        public int getScrollableBlockIncrement(
                Rectangle visibleRect,
                int orientation,
                int direction
        ) {
            return 120;
        }

        @Override
        public boolean getScrollableTracksViewportWidth() {
            return true;
        }

        @Override
        public boolean getScrollableTracksViewportHeight() {
            return false;
        }
    }
}