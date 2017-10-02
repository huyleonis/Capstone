package com.fpt.capstone.Entities;

import javax.persistence.*;

@Entity(name = "transaction")
public class TransactionEntity {
    @Id
    @Column
    private Integer IdTransaction;

    @ManyToOne
    @JoinColumn(name = "Username")
    private AccountEntity Account;

    @ManyToOne
    @JoinColumn(name = "IdPrice")
    private PriceEntity Price;

    @Column
    private Long DateTime;

    @Column
    private Long Fee;

    public Integer getIdTransaction() {
        return IdTransaction;
    }

    public void setIdTransaction(Integer idTransaction) {
        IdTransaction = idTransaction;
    }

    public AccountEntity getAccount() {
        return Account;
    }

    public void setAccount(AccountEntity account) {
        Account = account;
    }

    public PriceEntity getPrice() {
        return Price;
    }

    public void setPrice(PriceEntity price) {
        Price = price;
    }

    public Long getDateTime() {
        return DateTime;
    }

    public void setDateTime(Long dateTime) {
        DateTime = dateTime;
    }

    public Long getFee() {
        return Fee;
    }

    public void setFee(Long fee) {
        Fee = fee;
    }
}
