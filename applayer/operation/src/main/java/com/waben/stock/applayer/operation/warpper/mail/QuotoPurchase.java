package com.waben.stock.applayer.operation.warpper.mail;

import java.util.Date;

/**
 * @author Created by yuyidi on 2018/3/4.
 * @desc 期权询价
 */
public class QuotoPurchase extends QuotoInfo {
    //起始日
    private Date begin;
    //到期日
    private Date end;
    //权力金率
    private String rate;

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
