package com.waben.stock.applayer.operation.controller;

import com.waben.stock.applayer.operation.OperationApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Created by yuyidi on 2017/12/4.
 * @desc
 */
public class InvestorBuyRecordTest extends OperationApplicationTests {

//    @Test
    public void investorBuyBuyrecordEntrust() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post
                ("/buyrecord/{buyrecord}/buyIn", "1", "1")).andExpect(status().isOk()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

}
