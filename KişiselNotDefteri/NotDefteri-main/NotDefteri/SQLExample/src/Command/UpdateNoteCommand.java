package Command;

import Controller.NoteController;

public class UpdateNoteCommand implements Command {
    private NoteController noteController;
    private int noteId;
    private String oldTitle;
    private String oldContent;
    private String newTitle;
    private String newContent;

    public UpdateNoteCommand(NoteController noteController, int noteId, String oldTitle, String oldContent, String newTitle, String newContent) {
        this.noteController = noteController;
        this.noteId = noteId;
        this.oldTitle = oldTitle;
        this.oldContent = oldContent;
        this.newTitle = newTitle;
        this.newContent = newContent;
    }

    @Override
    public void execute() {
        noteController.updateNote(noteId, newTitle, newContent, "Active");
    }

    @Override
    public void undo() {
        noteController.updateNote(noteId, oldTitle, oldContent, "Active");
    }
}
