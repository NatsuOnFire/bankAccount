package fr.laforest.bankaccount.controller;

import fr.laforest.bankaccount.model.Account;
import fr.laforest.bankaccount.repository.AccountRepository;
import fr.laforest.bankaccount.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
class IndexControllerTests {

    @InjectMocks
    private IndexController indexController;
    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private Account account;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
        this.indexController = new IndexController(accountService);
        this.account = new Account();
        this.account.setId(1);
    }

    @Test
    void indexAccess() {
        Mockito.when(this.accountRepository.findOne(this.account.getId())).thenReturn(this.account);

        Model model = new ExtendedModelMap();
        this.indexController.index(model);
        boolean containsMoney = model.containsAttribute("myMoney");
        Map<String, Object> map = model.asMap();

        assertTrue(containsMoney);
        assertEquals("$0.00", map.get("myMoney"));
    }
}
