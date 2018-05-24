package com.waben.stock.applayer.invest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Created by yuyidi on 2017/12/12.
 * @desc
 */
@Controller
public class SystemController {

    @RequestMapping("/")
    public String index() {
        System.out.println(3333);
        return "index";
    }
}
