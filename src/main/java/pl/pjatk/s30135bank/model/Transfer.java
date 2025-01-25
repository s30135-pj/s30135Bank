package pl.pjatk.s30135bank.model;

public class Transfer extends Transaction {

    private Client client;

    public Transfer(int id, Status status, double amount, Client client) {
        super(id, status, amount);
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getType() {
        return "Przelew";
    }
}
