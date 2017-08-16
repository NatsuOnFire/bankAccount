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
class WithdrawalControllerTests {

    @InjectMocks
    private WithdrawalController withdrawalController;
    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private Account account;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
        this.withdrawalController = new WithdrawalController(accountService);
        this.account = new Account(200);
        this.account.setId(1);
    }

    @Test
    void withdrawalAccess() {
        Mockito.when(this.accountRepository.findOne(this.account.getId())).thenReturn(this.account);

        Model model = new ExtendedModelMap();
        String response = this.withdrawalController.withdrawal(model, this.account);
        boolean containsMoney = model.containsAttribute("myMoney");
        boolean containsAccount = model.containsAttribute("account");
        Map<String, Object> map = model.asMap();

        assertTrue(containsMoney);
        assertTrue(containsAccount);
        assertEquals("$200.00", map.get("myMoney"));
        assertEquals("withdrawal", response);
    }

    @Test
    void withdrawal200ToMyAccount() {
        Mockito.when(this.accountRepository.findOne(this.account.getId())).thenReturn(this.account);
        Mockito.when(this.accountRepository.save(any(Account.class))).thenReturn(this.account);

        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        Account accountForm = new Account(200);
        String response = this.withdrawalController.withdrawalValidation(accountForm, redirectAttributes);

        assertEquals(1, this.account.getHistories().size());
        assertEquals(0, this.account.getMoney());
        assertEquals("redirect:/withdrawal", response);
    }

    @Test
    void withdrawalNegativeValueError() {
        Mockito.when(this.accountRepository.findOne(this.account.getId())).thenReturn(this.account);
        Mockito.when(this.accountRepository.save(any(Account.class))).thenReturn(this.account);

        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        Account accountForm = new Account(-200);
        this.withdrawalController.withdrawalValidation(accountForm, redirectAttributes);

        assertTrue(this.account.getHistories().isEmpty());
        assertEquals(200, this.account.getMoney());
    }
}
