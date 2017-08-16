package fr.laforest.bankaccount.controller;

import fr.laforest.bankaccount.model.Account;
import fr.laforest.bankaccount.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HistoryController {
    private AccountService accountService;

    public HistoryController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/history")
    public String history(Model model) {
        Account account = this.accountService.getAccount(1);
        model.addAttribute("myMoney", new IndexController(this.accountService).getMyMoney());
        model.addAttribute("histories", account.getHistories());
        return "history";
    }
}
