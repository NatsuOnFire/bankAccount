package fr.laforest.bankaccount.model;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
class AccountTests {

    @Test
    void createAccountWithoutMoney() {
        Account account = new Account();
        assertEquals(0, account.getMoney());
        assertTrue(account.getHistories().isEmpty());

        account.setId(1);
        account.setMoney(500);
        assertEquals(1, account.getId());
        assertEquals(500, account.getMoney());
    }

    @Test
    void createAccountWithMoneyAndHistories() {
        Account account = new Account(1000);
        assertEquals(1000, account.getMoney());

        Set<History> histories = new HashSet<>();
        History history = new History("+", new Date(), "$100", "$1100", account);
        histories.add(history);
        account.setHistories(histories);

        assertFalse(account.getHistories().isEmpty());
    }
}
