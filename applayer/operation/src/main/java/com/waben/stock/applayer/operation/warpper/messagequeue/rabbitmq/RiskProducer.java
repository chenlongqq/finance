package com.waben.stock.applayer.operation.warpper.messagequeue.rabbitmq;

import com.waben.stock.applayer.operation.warpper.messagequeue.RabbitMQProducer;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.enums.ExponentEnum;
import com.waben.stock.interfaces.pojo.mq.BuyRecordMessage;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import org.springframework.stereotype.Component;

/**
 * @author Created by yuyidi on 2017/12/1.
 * @desc
 */
@Component
public class RiskProducer extends RabbitMQProducer<BuyRecordMessage> {

    public void risk(BuyRecordDto buyRecord) {
        BuyRecordMessage buyRecordMessage = CopyBeanUtils.copyBeanProperties(buyRecord, new BuyRecordMessage(),
                false);
        //获取点买记录股票信息的指数信息
        String exponent = buyRecord.getStockDto().getExponent().getExponentCode();
        super.topic("buyRecord", ExponentEnum.getByCode(exponent).getType(), buyRecordMessage);
    }

}
