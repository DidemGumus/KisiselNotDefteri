package View;

import Controller.UserController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        // JFrame oluşturuluyor
        JFrame frame = new JFrame("Kişisel Not Defteri - Kullanıcı Girişi");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new CardLayout());

        // UserController nesnesi
        UserController userController = new UserController();

        // Login Panel
        JPanel loginPanel = createLoginPanel(frame, userController);

        // Register Panel
        JPanel registerPanel = createRegisterPanel(frame, userController);

        // CardLayout için kartlar ekleniyor
        frame.add(loginPanel, "loginPanel");
        frame.add(registerPanel, "registerPanel");

        // Frame görünür yapılıyor
        frame.setVisible(true);
    }

    private static JPanel createLoginPanel(JFrame frame, UserController userController) {
        JPanel loginPanel = new JPanel(new GridLayout(4, 2));
        JLabel usernameLabel = new JLabel("Kullanıcı Adı:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Şifre:");
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Giriş Yap");
        JButton goToRegisterButton = new JButton("Kayıt Ol");

        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);
        loginPanel.add(goToRegisterButton);

        CardLayout cardLayout = (CardLayout) frame.getContentPane().getLayout();

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Kullanıcı adı ve şifre boş olamaz.", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int userId = userController.login(username, password);
                if (userId != -1) {
                    JOptionPane.showMessageDialog(frame, "Giriş başarılı!");
                    frame.dispose(); // Mevcut giriş ekranını kapat
                    new NoteManagementGUI(userId); // Not Yönetim Ekranı'na yönlendir
                } else {
                    JOptionPane.showMessageDialog(frame, "Kullanıcı adı veya şifre yanlış.", "Hata", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Giriş sırasında bir hata oluştu.", "Hata", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        goToRegisterButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "registerPanel"));

        return loginPanel;
    }

    private static JPanel createRegisterPanel(JFrame frame, UserController userController) {
        JPanel registerPanel = new JPanel(new GridLayout(5, 2));
        JLabel regUsernameLabel = new JLabel("Kullanıcı Adı:");
        JTextField regUsernameField = new JTextField();
        JLabel regPasswordLabel = new JLabel("Şifre:");
        JPasswordField regPasswordField = new JPasswordField();
        JLabel emailLabel = new JLabel("E-posta:");
        JTextField emailField = new JTextField();
        JButton registerButton = new JButton("Kaydol");
        JButton goToLoginButton = new JButton("Giriş Yap");

        registerPanel.add(regUsernameLabel);
        registerPanel.add(regUsernameField);
        registerPanel.add(regPasswordLabel);
        registerPanel.add(regPasswordField);
        registerPanel.add(emailLabel);
        registerPanel.add(emailField);
        registerPanel.add(registerButton);
        registerPanel.add(goToLoginButton);

        CardLayout cardLayout = (CardLayout) frame.getContentPane().getLayout();

        registerButton.addActionListener(e -> {
            String username = regUsernameField.getText();
            String password = new String(regPasswordField.getPassword());
            String email = emailField.getText();

            if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Tüm alanlar doldurulmalıdır.", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                if (userController.register(username, password, email, "user")) {
                    JOptionPane.showMessageDialog(frame, "Kayıt başarılı! Giriş yapabilirsiniz.");
                    cardLayout.show(frame.getContentPane(), "loginPanel");
                } else {
                    JOptionPane.showMessageDialog(frame, "Kayıt başarısız. Bilgileri kontrol edin.", "Hata", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Kayıt sırasında bir hata oluştu.", "Hata", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        goToLoginButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "loginPanel"));

        return registerPanel;
    }
}
