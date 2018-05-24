package com.waben.stock.applayer.operation.warpper.mail;

import org.apache.http.client.utils.DateUtils;

/**
 * @author Created by yuyidi on 2018/3/4.
 * @desc
 */
public class ExeriseMessage implements MailMessage {

    @Override
    public String message(QuotoInfo quotoInfo) {
        QuotoExenise quotoExenise = (QuotoExenise) quotoInfo;
        StringBuilder sb = new StringBuilder();
        sb.append("标的名称：").append(quotoExenise.getUnderlying()).append("\n");
        sb.append("标的代码：").append(quotoExenise.getCode()).append("\n");
        sb.append("结构：ATM平值\n");
        sb.append("执行价 : ").append(quotoExenise.getStrike()).append("\n");
        sb.append("名义本金 : ").append(quotoExenise.getAmount()).append("\n");
        sb.append("到期日：").append(DateUtils.formatDate(quotoExenise.getDueTo(),"yyyy/MM/dd")).append("\n");
        sb.append("行权日：").append(DateUtils.formatDate(quotoExenise.getExenise(),"yyyy/MM/dd")).append("\n");
        sb.append("行权价确定方式 : 市价\n");
        return sb.toString();
    }
}
