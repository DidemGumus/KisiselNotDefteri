package Model;

import java.sql.Timestamp;

public class Note {
    private int id;
    private int userId;
    private String title;
    private String content;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Constructor
    public Note(int id, int userId, String title, String content, String status, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getter ve Setter'lar
    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getStatus() {
        return status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
}
