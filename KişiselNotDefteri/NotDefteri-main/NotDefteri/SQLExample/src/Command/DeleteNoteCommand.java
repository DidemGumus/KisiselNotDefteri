package Command;

import Controller.NoteController;
import Model.Note;

public class DeleteNoteCommand implements Command {
    private NoteController noteController;
    private Note note;

    public DeleteNoteCommand(NoteController noteController, Note note) {
        this.noteController = noteController;
        this.note = note;
    }

    @Override
    public void execute() {
        noteController.deleteNoteById(note.getId());
    }

    @Override
    public void undo() {
        noteController.addNote(note.getUserId(), note.getTitle(), note.getContent(), note.getStatus());
    }
}
