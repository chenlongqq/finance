package com.waben.stock.applayer.operation.dto.websocket;

public class StockResponseMessage {
    private String code;
    private String price;
    private String time;
    private String entrustPrice;

    public StockResponseMessage() {
    }

    public StockResponseMessage(String code, String price, String time) {
        this.code = code;
        this.price = price;
        this.time = time;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEntrustPrice() {
        return entrustPrice;
    }

    public void setEntrustPrice(String entrustPrice) {
        this.entrustPrice = entrustPrice;
    }
}
