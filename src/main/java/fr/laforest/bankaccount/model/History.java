package fr.laforest.bankaccount.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Column(name = "operation")
    private String operation;

    @NotNull
    @Column(name = "date")
    private Date date;

    @NotNull
    @Column(name = "amount")
    private String amount;

    @NotNull
    @Column(name = "balance")
    private String balance;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public History() {
    }

    public History(String operation, Date date, String amount, String balance, Account account) {
        this.operation = operation;
        this.date = date;
        this.amount = amount;
        this.balance = balance;
        this.account = account;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
