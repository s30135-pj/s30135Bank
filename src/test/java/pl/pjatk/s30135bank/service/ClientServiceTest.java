package pl.pjatk.s30135bank.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pjatk.s30135bank.storage.ClientStorage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

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
}