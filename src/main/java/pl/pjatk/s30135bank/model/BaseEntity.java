package pl.pjatk.s30135bank.model;

public class BaseEntity {
    private int id;

    public BaseEntity() {

    }
    public BaseEntity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
