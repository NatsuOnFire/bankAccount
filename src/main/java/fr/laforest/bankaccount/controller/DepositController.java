package fr.laforest.bankaccount.controller;

import fr.laforest.bankaccount.model.Account;
import fr.laforest.bankaccount.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.text.NumberFormat;
import java.util.Locale;

@Controller
public class DepositController {
    private AccountService accountService;
    private NumberFormat numberFormat;

    public DepositController(AccountService accountService) {
        this.accountService = accountService;
        this.numberFormat = NumberFormat.getCurrencyInstance(Locale.US);
    }

    @GetMapping("/deposit")
    public String deposit(Model model, Account account) {
        model.addAttribute("myMoney", new IndexController(this.accountService).getMyMoney());
        model.addAttribute("account", account);
        return "deposit";
    }

    @PostMapping("/deposit")
    public String depositValidation(@Valid Account account, RedirectAttributes redirectAttributes) {
        if (account.getMoney() > 0) {
            this.accountService.deposit(1, account.getMoney());
            redirectAttributes.addFlashAttribute("response", "You deposited " + this.numberFormat.format(account.getMoney()) + " to your account.");
            account.setMoney(0);
        } else {
            redirectAttributes.addFlashAttribute("error", "Wrong value. It must be greater than 0");
        }
        return "redirect:/deposit";
    }
}
