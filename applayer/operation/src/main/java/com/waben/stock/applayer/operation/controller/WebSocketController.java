package com.waben.stock.applayer.operation.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.waben.stock.applayer.operation.dto.websocket.StockRequestMessage;
import com.waben.stock.applayer.operation.dto.websocket.StockResponseMessage;
import com.waben.stock.applayer.operation.web.StockQuotationHttp;
import com.waben.stock.interfaces.pojo.stock.quotation.StockMarket;
import com.waben.stock.interfaces.util.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class WebSocketController {

    Logger logger = LoggerFactory.getLogger(getClass());

    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private StockQuotationHttp stockQuotationHttp;

    private Map<String, List<StockRequestMessage>> stocks = new ConcurrentHashMap<>();

    /**
     * 股票最新行情
     *
     * @param codes
     * @return
     */
    @MessageMapping("/publish")
    public void publish(String codes) {
        List<StockRequestMessage> stockRequestMessages = JacksonUtil.decode(codes, new TypeReference<List
                <StockRequestMessage>>() {});
        stocks.put("investor", stockRequestMessages);
    }

    @Scheduled(fixedRate = 6*1000)
    public void callback() {
        //logger.info("推送股票最新行情");
        for (Map.Entry<String, List<StockRequestMessage>> entry : stocks.entrySet()) {
            List<StockMarket> result = stockQuotationHttp.fetQuotationByCode(entry.getValue());
            List<StockResponseMessage> stockMarkets = new ArrayList<>();
            for (StockMarket stockMarket : result) {
                StockResponseMessage stockResponseMessage = new StockResponseMessage();
                stockResponseMessage.setCode(stockMarket.getInstrumentId());
                stockResponseMessage.setPrice(String.valueOf(stockMarket.getLastPrice()));
                stockResponseMessage.setEntrustPrice(String.valueOf(stockMarket.getDownLimitPrice()));
                stockMarkets.add(stockResponseMessage);
            }
            messagingTemplate.convertAndSendToUser(entry.getKey(), "/callback", stockMarkets);
        }
    }
}
