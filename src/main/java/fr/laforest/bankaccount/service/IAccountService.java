package fr.laforest.bankaccount.service;

import fr.laforest.bankaccount.model.Account;

public interface IAccountService {
    void deposit(long id, double money);

    void withdrawal(long id, double money);

    Account getAccount(long id);
}
