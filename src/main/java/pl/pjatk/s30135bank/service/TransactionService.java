package pl.pjatk.s30135bank.service;

import org.springframework.stereotype.Component;
import pl.pjatk.s30135bank.model.Client;
import pl.pjatk.s30135bank.model.Deposit;
import pl.pjatk.s30135bank.model.Status;
import pl.pjatk.s30135bank.model.Transfer;
import pl.pjatk.s30135bank.storage.TransactionStorage;

import java.util.Optional;

@Component
public class TransactionService {

    private final TransactionStorage transactionStorage;
    private final ClientService clientService;

    public TransactionService(TransactionStorage transactionStorage, ClientService clientService) {
        this.transactionStorage = transactionStorage;
        this.clientService = clientService;
    }

    public Transfer createTransfer(double amount, int clientId) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        int id = transactionStorage.getMaxId() + 1;
        Transfer transfer = new Transfer(id, Status.PENDING, amount);
        Optional<Client> optionalClient = clientService.getClientById(clientId);
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            Boolean success = clientService.reduceBalance(client, amount);
            if (success) {
                transfer.setClient(client);
                transfer.setBalanceAfterTransaction(client.getBalance());
                transfer.setStatus(Status.ACCEPTED);
            } else {
                transfer.setStatus(Status.DECLINED);
            }
        } else {
            transfer.setStatus(Status.DECLINED);
        }
        transactionStorage.save(transfer);
        return transfer;
    }

    public Deposit createDeposit(double amount, int clientId) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        int id = transactionStorage.getMaxId() + 1;
        Deposit deposit = new Deposit(id, Status.PENDING, amount);
        Optional<Client> optionalClient = clientService.getClientById(clientId);
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            clientService.addBalance(client, amount);
            deposit.setBalanceAfterTransaction(client.getBalance());
            deposit.setStatus(Status.ACCEPTED);
        } else {
            deposit.setStatus(Status.DECLINED);
        }
        transactionStorage.save(deposit);
        return deposit;
    }
}
