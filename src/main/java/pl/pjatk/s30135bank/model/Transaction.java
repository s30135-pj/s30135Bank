package pl.pjatk.s30135bank.model;

public class Transaction extends BaseEntity {

    private Status status;
    private double amount;
    private double balanceAfterTransaction;

    public Transaction(int id, Status status, double amount) {
        super(id);
        this.status = status;
        this.amount = amount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setBalanceAfterTransaction(double balanceAfterTransaction) {
        this.balanceAfterTransaction = balanceAfterTransaction;
    }

    public double getBalanceAfterTransaction() {
        return balanceAfterTransaction;
    }
}
