package Command;

import Controller.NoteController;
import Model.Note;

public class AddNoteCommand implements Command {
    private NoteController noteController;
    private Note note;

    public AddNoteCommand(NoteController noteController, Note note) {
        this.noteController = noteController;
        this.note = note;
    }

    @Override
    public void execute() {
        noteController.addNote(note.getUserId(), note.getTitle(), note.getContent(), note.getStatus());
    }

    @Override
    public void undo() {
        noteController.deleteNoteById(note.getId());
    }
}
