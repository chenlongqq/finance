package com.waben.stock.applayer.operation.warpper.mail;

import java.util.Date;

/**
 * @author Created by yuyidi on 2018/3/4.
 * @desc
 */
public class QuotoInquiry extends QuotoInfo{

    private Date date;
    private String tenor;
    private String price;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTenor() {
        return tenor;
    }

    public void setTenor(String tenor) {
        this.tenor = tenor;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
