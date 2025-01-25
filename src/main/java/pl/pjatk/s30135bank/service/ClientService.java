package pl.pjatk.s30135bank.service;

import org.springframework.stereotype.Component;
import pl.pjatk.s30135bank.model.Client;
import pl.pjatk.s30135bank.storage.ClientStorage;

import java.util.Optional;

@Component
public class ClientService {

    private final ClientStorage clientStorage;

    public ClientService(ClientStorage clientStorage) {
        this.clientStorage = clientStorage;
    }

    public void registerClient(double startingBalance) {
        int id = clientStorage.getMaxId() + 1;
        Client client = new Client(id, startingBalance);
        clientStorage.save(client);
    }

    public Optional<Client> getClientById(int id) {
        return clientStorage.getById(id);
    }

    public void reduceBalance(Client client, double amount) {
        client.setBalance(client.getBalance() - amount);
    }
}
