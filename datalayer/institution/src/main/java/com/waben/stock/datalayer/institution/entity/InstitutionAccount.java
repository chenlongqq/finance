package com.waben.stock.datalayer.institution.entity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author Created by yuyidi on 2018/3/9.
 * @desc 机构账户
 */
@Entity
@Table(name = "institution_account")
public class InstitutionAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "institution")
    private Institution institution;
    @Column
    private BigDecimal amount;
    @Column
    private BigDecimal balance;
    //确认密码
    @Column
    private String confirm;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }
}
