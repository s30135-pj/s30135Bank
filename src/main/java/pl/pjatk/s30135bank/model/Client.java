package pl.pjatk.s30135bank.model;

public class Client extends BaseEntity {

    private double balance;

    public Client() {}

    public Client(int id, double balance) {
        super(id);
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }


    @Override
    public String toString() {
        return "Client id: " + getId() + ", balance: " + balance;
    }
}
