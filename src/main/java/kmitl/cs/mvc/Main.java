package kmitl.cs.mvc;

import kmitl.cs.mvc.controller.MainController;
import kmitl.cs.mvc.view.LoginView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Run UI on EDT
        SwingUtilities.invokeLater(() -> {
            try {
                MainController mc = new MainController();
                mc.loadAll(); // load CSV data
                LoginView login = new LoginView(mc);
                login.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to start: " + e.getMessage());
            }
        });
    }
}