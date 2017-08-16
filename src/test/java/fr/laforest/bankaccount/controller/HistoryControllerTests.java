package fr.laforest.bankaccount.controller;

import fr.laforest.bankaccount.model.Account;
import fr.laforest.bankaccount.model.History;
import fr.laforest.bankaccount.repository.AccountRepository;
import fr.laforest.bankaccount.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
class HistoryControllerTests {

    @InjectMocks
    private HistoryController historyController;
    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private Account account;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        this.historyController = new HistoryController(accountService);
        this.account = new Account();
        this.account.setId(1);
    }

    @SuppressWarnings("unchecked")
    @Test
    void historyAccess() {
        Mockito.when(this.accountRepository.findOne(this.account.getId())).thenReturn(this.account);

        Model model = new ExtendedModelMap();
        this.historyController.history(model);
        boolean containsMoney = model.containsAttribute("myMoney");
        boolean containsHistories = model.containsAttribute("histories");
        Map<String, Object> map = model.asMap();

        assertTrue(containsMoney);
        assertTrue(containsHistories);
        assertEquals("$0.00", map.get("myMoney"));
        assertTrue(((Set<History>) map.get("histories")).isEmpty());
    }
}
