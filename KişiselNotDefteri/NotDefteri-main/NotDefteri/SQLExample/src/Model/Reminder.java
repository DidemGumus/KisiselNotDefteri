package Model;

import java.util.Date;

public class Reminder {
    private int id;
    private int noteId;
    private Date reminderTime;
    private String status;

    public Reminder(int id, int noteId, Date reminderTime, String status) {
        this.id = id;
        this.noteId = noteId;
        this.reminderTime = reminderTime;
        this.status = status;
    }

    // Getter ve Setter metotları
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getNoteId() { return noteId; }
    public void setNoteId(int noteId) { this.noteId = noteId; }

    public Date getReminderTime() { return reminderTime; }
    public void setReminderTime(Date reminderTime) { this.reminderTime = reminderTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
