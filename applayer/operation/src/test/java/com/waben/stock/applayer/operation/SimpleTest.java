package com.waben.stock.applayer.operation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sun.mail.util.MailSSLSocketFactory;
import com.waben.stock.applayer.operation.dto.websocket.StockRequestMessage;
import com.waben.stock.applayer.operation.warpper.mail.ExcelUtil;
import com.waben.stock.interfaces.pojo.stock.quotation.Resonse;
import com.waben.stock.interfaces.pojo.stock.quotation.StockMarket;
import com.waben.stock.interfaces.util.JacksonUtil;
import com.waben.stock.interfaces.web.HttpRest;
import org.junit.Test;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.File;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Created by yuyidi on 2017/11/15.
 * @desc
 */
public class SimpleTest {

    @Test
    public void testPasswordEncoder() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        System.out.println(passwordEncoder.encode("wangbei"));
    }

    @Test
    public void testStock() {
        String context = "http://lemi.esongbai.com/stk/stk";
        List<StockRequestMessage> requestMessages = new ArrayList<>();
        StockRequestMessage stockRequestMessage1 = new StockRequestMessage();
        stockRequestMessage1.setCode("000001");
        StockRequestMessage stockRequestMessage2 = new StockRequestMessage();
        stockRequestMessage2.setCode("000002");
        requestMessages.add(stockRequestMessage1);
        requestMessages.add(stockRequestMessage2);
        StringBuilder codes = new StringBuilder();
        for (StockRequestMessage stockRequestMessage : requestMessages) {
            codes.append(stockRequestMessage.getCode()).append(",");
        }
        String params = codes.toString().substring(0, codes.toString().length() - 1);
        String url = context + "/list.do?codes=" + params;
        System.out.println(url);
        Resonse<StockMarket> resonse = HttpRest.get(url, Resonse.class);
        List<StockMarket> result = JacksonUtil.decode("", new TypeReference<List<StockMarket>>() {
        });
        System.out.println();
    }

    @Test
    public void testJsonType() {
        String json = " {\"code\":200,\"data\":[{\"askPrice\":13.23,\"askPrice2\":13.24,\"askPrice3\":13.25," +
                "\"askPrice4\":13.26,\"askPrice5\":13.27,\"askVolume\":4700,\"askVolume2\":139220," +
                "\"askVolume3\":150900,\"askVolume4\":113500,\"askVolume5\":265969,\"bidPrice\":13.22," +
                "\"bidPrice2\":13.21,\"bidPrice3\":13.2,\"bidPrice4\":13.19,\"bidPrice5\":13.18,\"bidVolume\":361700," +
                "\"bidVolume2\":285200,\"bidVolume3\":201600,\"bidVolume4\":413200,\"bidVolume5\":242600," +
                "\"bps\":11.541,\"circulationMarketValue\":223655823186,\"circulationShares\":16917989651," +
                "\"downLimitPrice\":11.89,\"eps\":1.115,\"exchangeId\":\"4609\",\"highestPrice\":13.43," +
                "\"instrumentId\":\"000001\",\"inventory\":54681970,\"lastPrice\":13.22,\"lastVolume\":30600," +
                "\"lowestPrice\":13.16,\"marketValue\":226992838258,\"name\":\"平安银行\",\"openPrice\":13.21," +
                "\"pb\":1.145,\"pe\":8.889,\"preClsPrice\":13.21,\"preSetPrice\":13.21,\"settlePrice\":0," +
                "\"status\":1,\"totalShares\":17170411366,\"tradeDay\":\"2017-12-29\",\"turnover\":7.25864251E8," +
                "\"turnoverRate\":0.0032,\"upDropPrice\":0.01,\"upDropSpeed\":8.0E-4,\"upLimitPrice\":14.53," +
                "\"upTime\":1514524554000,\"upTimeFormat\":\"2017-12-29 13:15:54\",\"volRate\":0.757," +
                "\"volume\":5.468197E7}],\"msg\":\"success\"}";
        Resonse<StockMarket> result = JacksonUtil.decode(json, Resonse.class, StockMarket.class);
        System.out.println(result.getData().get(0).getName());
    }

    @Test
    public void testInquiry() {
        String url = "officetemplate" + File.separator + "excel" + File.separator + "inquiry.xlsx";
        InputStream is = ClassLoader.getSystemResourceAsStream(url);
        if (is == null) {
            System.out.println("null");
        }
//        ExcelUtil.renderInquiry();
    }

    @Test
    public void testPurchase() {
        //ExcelUtil.rendPurchase();
    }

    @Test
    public void testSendMail() {
        JavaMailSender mailSender = javaMailSender();
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("yyd@youguwang.com.cn");
        simpleMailMessage.setTo("lzh@youguwang.com.cn");
        simpleMailMessage.setSubject("测试邮件2222");
        simpleMailMessage.setText("这是测试邮件111");
        mailSender.send(simpleMailMessage);
    }

    public JavaMailSenderImpl javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.exmail.qq.com");
        javaMailSender.setPort(465);
        javaMailSender.setUsername("yyd@youguwang.com.cn");
        javaMailSender.setPassword("WangBei0605");
        Properties properties = new Properties();
        MailSSLSocketFactory sf = null;
        try {
            sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
        } catch (GeneralSecurityException e1) {
            e1.printStackTrace();
        }
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", sf);
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        javaMailSender.setJavaMailProperties(properties);
        return javaMailSender;
    }
}
