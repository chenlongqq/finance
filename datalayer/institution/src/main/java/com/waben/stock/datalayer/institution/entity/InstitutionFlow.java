package com.waben.stock.datalayer.institution.entity;

import com.waben.stock.interfaces.enums.CapitalSource;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author Created by yuyidi on 2018/3/9.
 * @desc 机构流水
 */
@Entity
@Table(name = "institution_flow")
public class InstitutionFlow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "flow_no")
    private String flowNo;
    @Column
    private BigDecimal amount;
    @Column
    private CapitalSource source;
    @Column
    private String tradeNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public CapitalSource getSource() {
        return source;
    }

    public void setSource(CapitalSource source) {
        this.source = source;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }
}
