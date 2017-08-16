package fr.laforest.bankaccount.controller;

import fr.laforest.bankaccount.model.Account;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest
class DepositControllerTests {

    @InjectMocks
    private DepositController depositController;
    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private Account account;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
        this.depositController = new DepositController(accountService);
        this.account = new Account(200);
        this.account.setId(1);
    }

    @Test
    void depositAccess() {
        Mockito.when(this.accountRepository.findOne(this.account.getId())).thenReturn(this.account);

        Model model = new ExtendedModelMap();
        String response = this.depositController.deposit(model, this.account);
        boolean containsMoney = model.containsAttribute("myMoney");
        boolean containsAccount = model.containsAttribute("account");
        Map<String, Object> map = model.asMap();

        assertTrue(containsMoney);
        assertTrue(containsAccount);
        assertEquals("$200.00", map.get("myMoney"));
        assertEquals("deposit", response);
    }

    @Test
    void deposit300ToMyAccount() {
        Mockito.when(this.accountRepository.findOne(this.account.getId())).thenReturn(this.account);
        Mockito.when(this.accountRepository.save(any(Account.class))).thenReturn(this.account);

        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        Account accountForm = new Account(300);
        String response = this.depositController.depositValidation(accountForm, redirectAttributes);

        assertEquals(1, this.account.getHistories().size());
        assertEquals(500, this.account.getMoney());
        assertEquals("redirect:/deposit", response);
    }

    @Test
    void depositNegativeValueError() {
        Mockito.when(this.accountRepository.findOne(this.account.getId())).thenReturn(this.account);
        Mockito.when(this.accountRepository.save(any(Account.class))).thenReturn(this.account);

        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        Account accountForm = new Account(-300);
        this.depositController.depositValidation(accountForm, redirectAttributes);

        assertTrue(this.account.getHistories().isEmpty());
        assertEquals(200, this.account.getMoney());
    }
}
