package pl.pjatk.s30135bank.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pjatk.s30135bank.model.Client;
import pl.pjatk.s30135bank.model.Status;
import pl.pjatk.s30135bank.model.Transaction;
import pl.pjatk.s30135bank.storage.TransactionStorage;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    TransactionStorage transactionStorage;

    @Mock
    ClientService clientService;

    @InjectMocks
    TransactionService transactionService;

    @Test
    void createNewTransactionForExistingClient() {
        Optional<Client> client = Optional.of(new Client(1, 100.00));
        when(clientService.getClientById(1)).thenReturn(client);
        when(clientService.reduceBalance(any(), anyDouble())).thenReturn(true);
        Transaction transaction = transactionService.createTransfer(50.00, 1);

        verify(transactionStorage).save(transaction);
        assertThat(transaction.getStatus()).isEqualTo(Status.ACCEPTED);
    }

    @Test
    void createNewDepositForExistingClient() {
        Optional<Client> client = Optional.of(new Client(1, 100.00));
        when(clientService.getClientById(1)).thenReturn(client);
        Transaction transaction = transactionService.createDeposit(50.00, 1);

        verify(transactionStorage).save(transaction);
        assertThat(transaction.getStatus()).isEqualTo(Status.ACCEPTED);
    }
}