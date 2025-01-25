package pl.pjatk.s30135bank.service;

import org.springframework.stereotype.Component;
import pl.pjatk.s30135bank.model.Client;
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
        int id = transactionStorage.getMaxId() + 1;
        Transfer transfer = new Transfer(id, Status.PENDING, amount);
        Optional<Client> optionalClient = clientService.getClientById(clientId);
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            Boolean success = clientService.reduceBalance(client, amount);
            if (success) {
                transfer.setClient(client);
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
}
