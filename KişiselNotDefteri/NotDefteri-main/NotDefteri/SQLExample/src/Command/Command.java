package Command;

public interface Command {
    void execute(); // İşlemi gerçekleştir
    void undo();    // İşlemi geri al
}
