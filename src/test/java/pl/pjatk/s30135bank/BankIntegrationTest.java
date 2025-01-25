package pl.pjatk.s30135bank;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import pl.pjatk.s30135bank.model.Client;
import pl.pjatk.s30135bank.model.Deposit;
import pl.pjatk.s30135bank.model.Status;
import pl.pjatk.s30135bank.model.Transfer;
import pl.pjatk.s30135bank.service.ClientService;
import pl.pjatk.s30135bank.service.TransactionService;
import pl.pjatk.s30135bank.storage.ClientStorage;
import pl.pjatk.s30135bank.storage.TransactionStorage;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BankIntegrationTest {

    @MockitoBean
    ClientStorage clientStorage;

    @MockitoBean
    TransactionStorage transactionStorage;

    @Autowired
    TransactionService transactionService;
    @Test
    void addsBalanceToExistingClient() {
        Optional<Client> client = Optional.of(new Client(1, 100.00));
        when(clientStorage.getById(1)).thenReturn(client);

        Deposit deposit = transactionService.createDeposit(50.00, 1);

        assertThat(deposit.getBalanceAfterTransaction()).isEqualTo(150.00);
        assertThat(deposit.getStatus()).isEqualTo(Status.ACCEPTED);
    }

    @Test
    void doesNotAddBalanceIfClientIsNotExisting() {
        when(clientStorage.getById(1)).thenReturn(Optional.empty());

        Deposit deposit = transactionService.createDeposit(50.00, 1);
        
        assertThat(deposit.getStatus()).isEqualTo(Status.DECLINED);
    }


    @Test
    void transferSuccessWithExistingClient() {
        Optional<Client> client = Optional.of(new Client(1, 100.00));
        when(clientStorage.getById(1)).thenReturn(client);
        Transfer transfer = transactionService.createTransfer(50.00, 1);

        assertThat(transfer.getBalanceAfterTransaction()).isEqualTo(50.00);
        assertThat(transfer.getStatus()).isEqualTo(Status.ACCEPTED);
    }

    @Test
    void transferFailsWithoutExistingClient() {
        when(clientStorage.getById(1)).thenReturn(Optional.empty());

        Transfer transfer = transactionService.createTransfer(50.00, 1);

        assertThat(transfer.getStatus()).isEqualTo(Status.DECLINED);
    }

    @Test
    void transferFailsWhenAmountIsBiggerThanBalance() {
        Optional<Client> client = Optional.of(new Client(1, 100.00));
        when(clientStorage.getById(1)).thenReturn(client);

        Transfer transfer = transactionService.createTransfer(150.00, 1);

        assertThat(transfer.getStatus()).isEqualTo(Status.DECLINED);

    }
}
