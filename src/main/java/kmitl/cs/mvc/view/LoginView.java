package kmitl.cs.mvc.view;

import kmitl.cs.mvc.controller.AuthController;
import kmitl.cs.mvc.controller.MainController;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private final MainController main;
    private final AuthController auth;

    public LoginView(MainController main) {
        this.main = main;
        this.auth = new AuthController(main);
        init();
    }

    private void init() {
        setTitle("JobFair - ลงทะเบียน");
        setSize(360, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6,6,6,6);
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblUser = new JLabel("ชื่อผู้ใช้งาน:");
        JLabel lblPass = new JLabel("รหัสผ่าน:");
        JTextField txtUser = new JTextField();
        JPasswordField txtPass = new JPasswordField();
        JButton btnLogin = new JButton("เข้าสู่ระบบ");
        JButton btnExit = new JButton("ออก");

        c.gridx=0; c.gridy=0; p.add(lblUser,c);
        c.gridx=1; c.gridy=0; c.weightx=1.0; p.add(txtUser,c);
        c.gridx=0; c.gridy=1; c.weightx=0; p.add(lblPass,c);
        c.gridx=1; c.gridy=1; c.weightx=1.0; p.add(txtPass,c);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(btnLogin); buttons.add(btnExit);
        c.gridx=0; c.gridy=2; c.gridwidth=2; p.add(buttons,c);

        add(p);

        btnLogin.addActionListener(e -> {
            String user = txtUser.getText().trim();
            String pass = new String(txtPass.getPassword());
            var cand = auth.login(user, pass);
            if (cand != null) {
                main.setCurrentUser(cand);
                // open main view
                SwingUtilities.invokeLater(() -> {
                    MainView mv = new MainView(main);
                    mv.setVisible(true);
                    dispose();
                });
            } else {
                JOptionPane.showMessageDialog(this, "ชื่อผู้ใช้หรือรหัสผ่านไม่ถูกต้อง");
            }
        });

        btnExit.addActionListener(e -> System.exit(0));
    }
}