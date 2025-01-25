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

    public void registerClient(double startingBalance) throws IllegalArgumentException {
        if (startingBalance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        int id = clientStorage.getMaxId() + 1;
        Client client = new Client(id, startingBalance);
        clientStorage.save(client);
    }

    public Optional<Client> getClientById(int id) {
        if (clientStorage.getById(id).isEmpty()) {
            System.out.println("Nie znaleziono klienta o podanym id.");
        }
        return clientStorage.getById(id);
    }

    public Boolean reduceBalance(Client client, double amount) {
        if (client.getBalance() >= amount) {
            client.setBalance(client.getBalance() - amount);
            return true;
        } else {
            System.out.println("Nie ma wystarczająco środków.");
            return false;
        }
    }

    public void addBalance(Client client, double amount) {
        client.setBalance(client.getBalance() + amount);
    }
}
