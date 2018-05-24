package com.waben.stock.applayer.operation.warpper.mail;

import java.util.Date;

/**
 * @author Created by yuyidi on 2018/3/4.
 * @desc 期权行权
 */
public class QuotoExenise extends QuotoInfo {

    //行权日
    private Date exenise;
    //到期日
    private Date dueTo;

    public Date getExenise() {
        return exenise;
    }

    public void setExenise(Date exenise) {
        this.exenise = exenise;
    }

    public Date getDueTo() {
        return dueTo;
    }

    public void setDueTo(Date dueTo) {
        this.dueTo = dueTo;
    }
}
