package fr.laforest.bankaccount.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Column(name = "money")
    private double money;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private Set<History> histories;

    public Account() {
        this.money = 0;
        this.histories = new HashSet<>();
    }

    public Account(long money) {
        this.money = money;
        this.histories = new HashSet<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public Set<History> getHistories() {
        return histories;
    }

    public void setHistories(Set<History> histories) {
        this.histories = histories;
    }
}
