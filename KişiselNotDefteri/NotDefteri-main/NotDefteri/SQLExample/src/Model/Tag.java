package Model;

public class Tag {
    private int id;
    private String name;
    private int userId;

    public Tag(int id, String name, int userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return this.name; // Tag modelindeki 'name' alanını döndürür.
    }

}
