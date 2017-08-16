package fr.laforest.bankaccount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"fr.laforest.bankaccount.controller", "fr.laforest.bankaccount.service"})
@EnableJpaRepositories(basePackages = {"fr.laforest.bankaccount.repository"})
public class BankAccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankAccountApplication.class, args);
    }
}
