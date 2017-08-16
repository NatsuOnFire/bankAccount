package fr.laforest.bankaccount.controller;

import fr.laforest.bankaccount.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

@Controller
public class IndexController {
    private AccountService accountService;

    public IndexController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("myMoney", this.getMyMoney());
        return "index";
    }

    public String getMyMoney() {
        double money = this.accountService.getAccount(1).getMoney();
        DecimalFormat currencyFormatter = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
        currencyFormatter.setNegativePrefix("$-");
        currencyFormatter.setNegativeSuffix("");
        return currencyFormatter.format(money);
    }
}
