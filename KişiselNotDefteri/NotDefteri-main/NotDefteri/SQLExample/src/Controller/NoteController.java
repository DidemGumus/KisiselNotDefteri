package Controller;

import Model.Note;
import Model.NoteModel;
import State.*;

import java.util.List;

public class NoteController {
    private NoteModel noteModel;
    private NoteContext noteContext;

    public NoteController() {
        this.noteModel = new NoteModel();
        this.noteContext = new NoteContext();

    }
    public boolean changeNoteState(int noteId, String newState) {
        switch (newState.toLowerCase()) {
            case "taslak":
                newState = "Taslak";
                noteContext.setState(new DraftState());
                break;
            case "tamamlandı":
                newState = "Tamamlandı";
                noteContext.setState(new CompletedState());
                break;
            default:
                System.out.println("Geçersiz durum: " + newState);
                return false;

        }
        noteContext.applyState(noteId);
        // Veritabanında güncelleme işlemi
        boolean isUpdated = noteModel.updateNoteState(noteId, newState);
        if (!isUpdated) {
            System.err.println("Veritabanında durum güncelleme başarısız oldu: Note ID = " + noteId + ", New State = " + newState);
        }
        return isUpdated;

    }
    public boolean isUserExists(int userId) {
        return noteModel.isUserExists(userId);
    }
    public boolean addNote(int userId, String title, String content, String status) {
        try {
            return noteModel.addNote(userId, title, content, status);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean updateNote(int noteId, String title, String content, String status) {
        return noteModel.updateNote(noteId, title, content, status);
    }
    public boolean deleteNoteById(int noteId) {
        try {
            return noteModel.deleteNote(noteId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<Note> getNotesByUserId(int userId) {
        try {
            return noteModel.getNotesByUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public Note getLatestNoteByUser(int userId) {
        try {
            return noteModel.getLatestNoteByUser(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
