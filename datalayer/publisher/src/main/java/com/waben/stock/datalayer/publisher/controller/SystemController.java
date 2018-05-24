package com.waben.stock.datalayer.publisher.controller;

import com.netflix.appinfo.EurekaInstanceConfig;
import com.waben.stock.interfaces.service.EchoInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Created by yuyidi on 2017/11/13.
 * @desc
 */
public class SystemController implements EchoInterface {

    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private EurekaInstanceConfig instanceConfig;

    public String echo() {
        logger.info("实例Id:{},Host{}", instanceConfig.getInstanceId(), instanceConfig.getHostName(false));
        return instanceConfig.getInstanceId();
    }

}
