package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import State.*;

public class NoteModel {

    // Not Ekle
    public boolean addNote(int userId, String title, String content, String status) {
        String query = "INSERT INTO notes (user_id, title, content, status) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, title);
            preparedStatement.setString(3, content);
            preparedStatement.setString(4, status);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean updateNoteState(int noteId, String state) {
        String query = "UPDATE notes SET status = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, state);
            ps.setInt(2, noteId);
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Durum başarıyla güncellendi: Note ID = " + noteId + ", Yeni Durum = " + state);
            } else {
                System.err.println("Durum güncellenemedi: Note ID = " + noteId + ", Yeni Durum = " + state);
            }
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.err.println("Veritabanı hatası: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }




    // Not Güncelle
    public boolean updateNote(int noteId, String title, String content, String status) {
        String query = "UPDATE notes SET title = ?, content = ?, status = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, content);
            preparedStatement.setString(3, status);
            preparedStatement.setInt(4, noteId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Not Sil
    public boolean deleteNote(int noteId) {
        String query = "DELETE FROM notes WHERE id = ?";
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, noteId);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Kullanıcıya Ait Notları Listele
    public List<Note> getNotesByUserId(int userId) {
        String query = "SELECT * FROM notes WHERE user_id = ?";
        List<Note> notes = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Note note = new Note(
                        resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getString("title"),
                        resultSet.getString("content"),
                        resultSet.getString("status"),
                        resultSet.getTimestamp("created_at"),
                        resultSet.getTimestamp("updated_at")
                );
                notes.add(note);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notes;
    }
    public boolean isUserExists(int userId) {
        String query = "SELECT COUNT(*) FROM users WHERE id = ?";
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0; // Kullanıcı varsa true döner
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Kullanıcı yoksa false döner
    }


    // Kullanıcıya Ait Son Eklenen Not
    public Note getLatestNoteByUser(int userId) {
        String query = "SELECT * FROM notes WHERE user_id = ? ORDER BY created_at DESC LIMIT 1";
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Note(
                        resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getString("title"),
                        resultSet.getString("content"),
                        resultSet.getString("status"),
                        resultSet.getTimestamp("created_at"),
                        resultSet.getTimestamp("updated_at")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
