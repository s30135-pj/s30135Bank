package pl.pjatk.s30135bank.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pjatk.s30135bank.model.Client;
import pl.pjatk.s30135bank.storage.ClientStorage;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    ClientStorage clientStorage;

    @InjectMocks
    ClientService clientService;

    @Test
    void registersNewClient() {
        clientService.registerClient(100.00);

        verify(clientStorage).save(any());
    }

    @Test
    void registersClientWithNegativeBalanceThrowsError() {
        assertThatThrownBy(() -> clientService.registerClient(-10.00)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void reduceBalanceSuccess() {
        Client client = new Client(1, 100.00);

        Boolean result = clientService.reduceBalance(client, 100.00);

        assertThat(result).isTrue();
    }

    @Test
    void reduceBalanceFail() {
        Client client = new Client(1, 100.00);
        Boolean result = clientService.reduceBalance(client, 150.00);
        assertThat(result).isFalse();
    }

    @Test
    void readsClientData() {
        Optional<Client> client = Optional.of(new Client(1, 100.00));
        when(clientStorage.getById(1)).thenReturn(client);

        String clientData = clientService.getClientData(1);

        assertThat(clientData).isEqualTo(client.get().toString());
    }

    @Test
    void returnsMessageIfNotFound() {
        when(clientStorage.getById(1)).thenReturn(Optional.empty());

        String clientData = clientService.getClientData(1);

        assertThat(clientData).isEqualTo("Nie znaleziono klienta o podanym id.");
    }
}