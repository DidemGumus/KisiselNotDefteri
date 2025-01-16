package Controller;

import Model.DatabaseConnection;
import Model.Tag;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TagController {
    private final DatabaseConnection databaseConnection;

    public TagController() {
        this.databaseConnection = DatabaseConnection.getInstance();
    }

    // Etiket Ekle
    public boolean addTag(Tag tag) {
        String query = "INSERT INTO tags (name, user_id) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = databaseConnection.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, tag.getName());
            preparedStatement.setInt(2, tag.getUserId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                System.err.println("Error adding tag: Duplicate entry for tag name.");
            } else {
                System.err.println("Error adding tag: " + e.getMessage());
            }
            return false;
        }
    }

    // Etiket Güncelle
    public boolean updateTag(int tagId, String newName) {
        String query = "UPDATE tags SET name = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = databaseConnection.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, newName);
            preparedStatement.setInt(2, tagId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating tag: " + e.getMessage());
            return false;
        }
    }

    // Kullanıcıya Ait Etiketleri Getir
    public List<Tag> getTagsByUserId(int userId) {
        String query = "SELECT * FROM tags WHERE user_id = ?";
        List<Tag> tags = new ArrayList<>();
        try (PreparedStatement preparedStatement = databaseConnection.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                tags.add(new Tag(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("user_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tags;
    }



    // Etiket Sil
    public boolean deleteTag(int tagId) {
        String query = "DELETE FROM tags WHERE id = ?";
        try (PreparedStatement preparedStatement = databaseConnection.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, tagId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting tag: " + e.getMessage());
            return false;
        }
    }

    // Etiketi Nota Bağla
    public boolean addTagToNote(int noteId, int tagId) {
        String query = "INSERT INTO note_tags (note_id, tag_id) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = databaseConnection.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, noteId);
            preparedStatement.setInt(2, tagId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                System.err.println("Error adding tag to note: Duplicate entry for this tag and note.");
            } else {
                System.err.println("Error adding tag to note: " + e.getMessage());
            }
            return false;
        }
    }

    // Belirli Bir Not İçin Etiketleri Getir
    public List<Tag> getTagsForNote(int noteId) {
        String query = "SELECT t.id, t.name, t.user_id FROM tags t " +
                "JOIN note_tags nt ON t.id = nt.tag_id WHERE nt.note_id = ?";
        List<Tag> tags = new ArrayList<>();
        try (PreparedStatement preparedStatement = databaseConnection.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, noteId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                tags.add(new Tag(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("user_id")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching tags for note: " + e.getMessage());
        }
        return tags;
    }
}