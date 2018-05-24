package com.waben.stock.applayer.operation.controller;

import org.apache.commons.codec.digest.DigestUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class PaymentDemo {
    private String context = "http://211.149.180.207/gateWay/service/another/pay";

    public void payment(String orderNo) {
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String,String> map = new TreeMap();
        map.put("mchNo","MER1000157");
        map.put("payChannel","1_jhdr");
        map.put("orderNo",orderNo);
        map.put("amount","0.01");
        map.put("bankType","0");
        map.put("accNo","");
        map.put("accName","");
        map.put("bankName","");
        map.put("timeStamp",time.format(new Date()));
        //签名
        String toSign = "";
        for (String key : map.keySet()) {
            toSign += key + "=" + map.get(key) + "&";
        }
        String sign = DigestUtils.md5Hex(toSign+"key=0f7acd3d53920783c61ed1a99ed58c89");
        map.put("sign",sign);
    }

}
