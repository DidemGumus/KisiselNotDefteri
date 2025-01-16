package Command;

import Controller.NoteController;

public class ChangeNoteStateCommand implements Command {
    private NoteController noteController;
    private int noteId;
    private String newState;
    private String previousState;

    public ChangeNoteStateCommand(NoteController noteController, int noteId, String previousState, String newState) {
        this.noteController = noteController;
        this.noteId = noteId;
        this.newState = newState;
        this.previousState = previousState;
    }

    @Override
    public void execute() {
        noteController.changeNoteState(noteId, newState);
    }

    @Override
    public void undo() {
        noteController.changeNoteState(noteId, previousState);
    }
}
