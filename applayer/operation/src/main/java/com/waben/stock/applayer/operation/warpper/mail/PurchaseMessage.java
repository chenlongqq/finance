package com.waben.stock.applayer.operation.warpper.mail;

import org.apache.http.client.utils.DateUtils;

/**
 * @author Created by yuyidi on 2018/3/4.
 * @desc
 */
public class PurchaseMessage implements MailMessage {


    @Override
    public String message(QuotoInfo quotoInfo) {
        QuotoPurchase quotoPurchase = (QuotoPurchase) quotoInfo;
        StringBuilder sb = new StringBuilder();
        sb.append("标的名称：").append(quotoPurchase.getUnderlying()).append("\n");
        sb.append("标的代码：").append(quotoPurchase.getCode()).append("\n");
        sb.append("起始日：").append(DateUtils.formatDate(quotoPurchase.getBegin(),"yyyy/MM/dd")).append("\n");
        sb.append("到期日：").append(DateUtils.formatDate(quotoPurchase.getEnd(),"yyyy/MM/dd")).append("\n");
        sb.append("结构：ATM平值\n");
        sb.append("执行价 : ").append(quotoPurchase.getStrike()).append("\n");
        sb.append("名义本金 : ").append(quotoPurchase.getAmount()).append("\n");
        sb.append("权力金率 : ").append(quotoPurchase.getRate()).append("\n");
        sb.append("成交方式 : 市价\n");
        return sb.toString();
    }
}
