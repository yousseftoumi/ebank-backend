package com.example.ebankbackend;

import com.example.ebankbackend.entities.AccountOperation;
import com.example.ebankbackend.entities.CurrentAccount;
import com.example.ebankbackend.entities.Customer;
import com.example.ebankbackend.entities.SavingAccount;
import com.example.ebankbackend.enums.AccountStatus;
import com.example.ebankbackend.enums.OperationType;
import com.example.ebankbackend.repositories.AccountOperationRepository;
import com.example.ebankbackend.repositories.BankAccountRepository;
import com.example.ebankbackend.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankBackendApplication {

    public static void main(String[] args) {

        SpringApplication.run(EbankBackendApplication.class, args);
    }
    @Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            BankAccountRepository bankAccountRepository,
                            AccountOperationRepository accountOperationRepository){
        return args -> {
            Stream.of("Hassan","Yassine","Aicha").forEach(name->{
                Customer customer=new Customer();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                customerRepository.save(customer);
            });

        customerRepository.findAll().forEach(cust->{
            CurrentAccount currentAccount=new CurrentAccount();
            currentAccount.setId(UUID.randomUUID().toString());
            currentAccount.setBalance(Math.random()*90000);
            currentAccount.setCreatedAt(new Date());
            currentAccount.setStatus(AccountStatus.CREATED);
            currentAccount.setCustomer(cust);
            currentAccount.setOverDraft(9000);
            bankAccountRepository.save(currentAccount);

            SavingAccount savingAccount=new SavingAccount();
            savingAccount.setId(UUID.randomUUID().toString());
            savingAccount.setBalance(Math.random()*90000);
            savingAccount.setCreatedAt(new Date());
            savingAccount.setStatus(AccountStatus.CREATED);
            savingAccount.setCustomer(cust);
            savingAccount.setInterestRate(5.5);
            bankAccountRepository.save(savingAccount);

        });

        bankAccountRepository.findAll().forEach(acc->{
            for (int i = 0; i <10 ; i++) {
                AccountOperation accountOperation=new AccountOperation();
                accountOperation.setOperationDate(new Date());
                accountOperation.setAmount(Math.random()*12000);
                accountOperation.setType(Math.random()>0.5? OperationType.DEBIT: OperationType.CREDIT);
                accountOperation.setBankAccount(acc);
                accountOperationRepository.save(accountOperation);
            }

        });
        };
    }
}
