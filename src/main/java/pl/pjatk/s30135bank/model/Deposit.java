package pl.pjatk.s30135bank.model;

public class Deposit extends Transaction {

    private Client target;

    public Deposit(int id, Status status, Client target, double amount) {
        super(id, status, amount);
        this.target = target;
    }

    public Client getTarget() {
        return target;
    }

    public void setTarget(Client target) {
        this.target = target;
    }
}
