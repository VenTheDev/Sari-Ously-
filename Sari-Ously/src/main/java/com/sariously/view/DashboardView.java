package com.sariously.view;

import javax.swing.*;
import java.awt.Dimension;

public class DashboardView extends JFrame {

    public DashboardView() {

        setTitle("Sari-Ously - Dashboard");
        setSize(1280, 760);
        setMinimumSize(new Dimension(1200, 720));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setContentPane(new DashboardPanel(() -> {
            new LoginView();
            dispose();
        }));

        setVisible(true);
    }
}
