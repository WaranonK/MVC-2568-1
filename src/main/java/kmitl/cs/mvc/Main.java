package kmitl.cs.mvc;

import kmitl.cs.mvc.controller.MainController;
import kmitl.cs.mvc.model.FontUtil;
import kmitl.cs.mvc.view.LoginView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                FontUtil.setGlobalFont("/fonts/Sarabun-Regular.ttf", "Tahoma", 14f);
                MainController mc = new MainController();
                mc.loadAll(); // load CSV data
                LoginView login = new LoginView(mc);
                login.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "ไม่สามารถเริ่มการทำงานได้: " + e.getMessage());
            }
        });
    }
}