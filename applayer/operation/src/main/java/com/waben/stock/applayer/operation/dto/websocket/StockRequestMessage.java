package com.waben.stock.applayer.operation.dto.websocket;

import java.util.Date;

public class StockRequestMessage {

    String code;

    Date time;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
