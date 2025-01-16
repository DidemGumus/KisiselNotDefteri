package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;

    private static final String URL = "jdbc:mysql://localhost:3306/personal_notes";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private DatabaseConnection() {
        connect();
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }

    private void connect() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("Bağlantı başarılı bir şekilde sağlandı.");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver bulunamadı. Lütfen doğru sürücüyü yüklediğinizden emin olun.");
            throw new RuntimeException("JDBC Driver hatası.", e);
        } catch (SQLException e) {
            System.err.println("Veritabanına bağlanırken bir hata oluştu. Lütfen bağlantı bilgilerini kontrol edin.");
            throw new RuntimeException("SQL Bağlantı hatası.", e);
        }
    }

    public Connection getConnection() {
        connect();
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Bağlantı başarıyla kapatıldı.");
            }
        } catch (SQLException e) {
            System.err.println("Bağlantı kapatılırken bir hata oluştu.");
            throw new RuntimeException("Bağlantı kapatma hatası.", e);
        }
    }
}
