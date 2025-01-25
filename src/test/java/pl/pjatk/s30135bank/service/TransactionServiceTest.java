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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.*;

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

    @Test
    void createNewTransactionForNonExistingClient() {
        when(clientService.getClientById(1)).thenReturn(Optional.empty());
        Transaction transaction = transactionService.createTransfer(50.00, 1);

        verify(transactionStorage).save(transaction);
        assertThat(transaction.getStatus()).isEqualTo(Status.DECLINED);
    }

    @Test
    void createNewDepositForNonExistingClient() {
        when(clientService.getClientById(1)).thenReturn(Optional.empty());
        Transaction transaction = transactionService.createDeposit(50.00, 1);

        verify(transactionStorage).save(transaction);
        assertThat(transaction.getStatus()).isEqualTo(Status.DECLINED);
    }

    @Test
    void createNewDepositWithNegativeAmountThrowsError() {
        assertThatThrownBy(
                () -> transactionService.createTransfer(-50.00, 1)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void createNewTransferWithNegativeAmountThrowsError() {
        assertThatThrownBy(
                () -> transactionService.createTransfer(-50.00, 1)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void createNewDepositWithZeroBalanceThrowsError() {
        assertThatThrownBy(
                () -> transactionService.createTransfer(0.00, 1)
        ).isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    void createNewTransferWithZeroBalanceThrowsError() {
        assertThatThrownBy(
                () -> transactionService.createTransfer(0.00, 1)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}