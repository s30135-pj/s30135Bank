package pl.pjatk.s30135bank.storage;

import org.springframework.stereotype.Component;
import pl.pjatk.s30135bank.model.Client;

import java.util.ArrayList;
import java.util.List;

@Component
public class ClientStorage extends Storage<Client> {

    private final List<Client> clients;

    public ClientStorage() {
        clients = new ArrayList<Client>();
    }

    @Override
    public List<Client> getList() {
        return clients;
    }
}
