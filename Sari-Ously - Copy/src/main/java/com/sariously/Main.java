package com.sariously;

import javax.swing.SwingUtilities;
import com.sariously.database.DatabaseConnection;
import com.sariously.view.HomeView;

public class Main {

    public static void main(String[] args) {
        DatabaseConnection.initializeDatabase();

        SwingUtilities.invokeLater(() -> {
            new HomeView();
        });
    }
}