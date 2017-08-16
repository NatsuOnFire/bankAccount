package fr.laforest.bankaccount.model;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
class HistoryTests {

    @Test
    void createHistoryWithoutParameter() {
        Date date = new Date();
        Account account = new Account();
        History history = new History();
        history.setId(1);
        history.setOperation("+");
        history.setDate(date);
        history.setAmount("$100");
        history.setBalance("$500");
        history.setAccount(account);

        assertEquals(1, history.getId());
        assertEquals("+", history.getOperation());
        assertEquals(date, history.getDate());
        assertEquals("$100", history.getAmount());
        assertEquals("$500", history.getBalance());
        assertEquals(account, history.getAccount());
    }

    @Test
    void createHistoryWithParameters() {
        Date date = new Date();
        Account account = new Account();
        History history = new History("-", date, "$-500", "$0", account);

        assertEquals("-", history.getOperation());
        assertEquals(date, history.getDate());
        assertEquals("$-500", history.getAmount());
        assertEquals("$0", history.getBalance());
        assertEquals(account, history.getAccount());
    }
}
