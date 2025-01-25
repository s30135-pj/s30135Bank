package pl.pjatk.s30135bank.storage;

import org.springframework.stereotype.Component;
import pl.pjatk.s30135bank.model.Transaction;

import java.util.ArrayList;
import java.util.List;

@Component
public class TransactionStorage extends Storage<Transaction> {

    private final List<Transaction> transactions;

    public TransactionStorage() {
        transactions = new ArrayList<>();
    }

    @Override
    public List<Transaction> getList() {
        return transactions;
    }
}
