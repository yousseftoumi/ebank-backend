package com.example.ebankbackend.dtos;

import lombok.Data;
import com.example.ebankbackend.enums.AccountStatus;
import java.util.Date;
@Data
public class SavingBankAccountDTO extends BankAccountDTO {
    private String id;
    private double balance;
    private Date createdAt;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private double interestRate;
}
