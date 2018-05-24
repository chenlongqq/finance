package com.waben.stock.applayer.operation.warpper.mail;

/**
 * @author Created by yuyidi on 2018/3/4.
 * @desc
 */
public class QuotoInfo {

    private String underlying;
    private String code;
    private String strike;
    private String amount;

    public String getUnderlying() {
        return underlying;
    }

    public void setUnderlying(String underlying) {
        this.underlying = underlying;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStrike() {
        return strike;
    }

    public void setStrike(String strike) {
        this.strike = strike;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
