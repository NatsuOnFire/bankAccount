package fr.laforest.bankaccount.service;

import fr.laforest.bankaccount.model.Account;
import fr.laforest.bankaccount.model.History;
import fr.laforest.bankaccount.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Service
public class AccountService implements IAccountService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private AccountRepository accountRepository;
    private DecimalFormat decimalFormat;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        this.decimalFormat = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
        this.decimalFormat.setNegativePrefix("$-");
        this.decimalFormat.setNegativeSuffix("");
    }

    @Override
    public void deposit(long id, double money) {
        Account account = this.getAccount(id);
        BigDecimal current = BigDecimal.valueOf(account.getMoney());
        BigDecimal depot = BigDecimal.valueOf(money);
        account.setMoney(current.add(depot).doubleValue());

        History history = this.createHistory("+", money, account);
        account.getHistories().add(history);

        accountRepository.save(account);
    }

    @Override
    public void withdrawal(long id, double money) {
        Account account = this.getAccount(id);
        BigDecimal current = BigDecimal.valueOf(account.getMoney());
        BigDecimal withdraw = BigDecimal.valueOf(money);
        account.setMoney(current.subtract(withdraw).doubleValue());

        History history = this.createHistory("-", -money, account);
        account.getHistories().add(history);

        accountRepository.save(account);
    }

    @Override
    public Account getAccount(long id) {
        return this.accountRepository.findOne(id);
    }

    private History createHistory(String operation, double money, Account account) {
        String amount = this.decimalFormat.format(money);
        String balance = this.decimalFormat.format(account.getMoney());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date now = new Date();
        try {
            String dateFormat = simpleDateFormat.format(now);
            now = simpleDateFormat.parse(dateFormat);
        } catch (ParseException e){
            logger.error(e.getMessage(), e);
        }

        return new History(operation, now, amount, balance, account);
    }
}
