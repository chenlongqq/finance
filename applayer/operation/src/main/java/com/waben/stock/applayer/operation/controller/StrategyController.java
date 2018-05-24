package com.waben.stock.applayer.operation.controller;

import com.waben.stock.applayer.operation.business.InvestorBusiness;
import com.waben.stock.applayer.operation.business.StrategyBusiness;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.*;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.vo.buyrecord.BuyRecordVo;
import com.waben.stock.interfaces.vo.investor.InvestorVo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/strategy")
public class StrategyController {

    @Autowired
    private StrategyBusiness strategyBusiness;
    
    @Autowired
    private InvestorBusiness investorBusiness;

    @RequestMapping("/posted/index")
    public String strategyPosted() {
        return "strategy/posted/index";
    }

    @RequestMapping("/holding/index")
    public String strategyHolding(ModelMap map) {
    	map.addAttribute("investors", CopyBeanUtils.copyListBeanPropertiesToList(investorBusiness.findAllInvestors(), InvestorVo.class));
        return "strategy/holding/index";
    }

    @RequestMapping("/unwind/index")
    public String strategyUnwind(ModelMap map) {
    	map.addAttribute("investors", CopyBeanUtils.copyListBeanPropertiesToList(investorBusiness.findAllInvestors(), InvestorVo.class));
        return "strategy/unwind/index";
    }

    @RequestMapping("/withdraw/index")
    public String strategyWithdraw(ModelMap map) {
        map.addAttribute("investors", CopyBeanUtils.copyListBeanPropertiesToList(investorBusiness.findAllInvestors(), InvestorVo.class));
        return "strategy/withdraw/index";
    }

    @GetMapping("/posted/pages")
    @ResponseBody
    public Response<PageInfo<BuyRecordVo>> postedPages(StrategyPostedQuery strategyPostedQuery) {
        PageInfo<BuyRecordDto> pages = strategyBusiness.postedPages(strategyPostedQuery);
        List<BuyRecordVo> buyRecordVos = CopyBeanUtils.copyListBeanPropertiesToList(pages.getContent(), BuyRecordVo.class);
        PageInfo<BuyRecordVo> result = new PageInfo<>(buyRecordVos, pages.getTotalPages(), pages.getLast(), pages.getTotalElements(), pages.getSize(), pages.getNumber(), pages.getFrist());
        return new Response<>(result);
    }

    @GetMapping("/holding/pages")
    @ResponseBody
    public Response<PageInfo<BuyRecordVo>> holdingPages(StrategyHoldingQuery strategyHoldingQuery) {
        PageInfo<BuyRecordDto> pages = strategyBusiness.holdingPages(strategyHoldingQuery);
        List<BuyRecordVo> buyRecordVos = CopyBeanUtils.copyListBeanPropertiesToList(pages.getContent(), BuyRecordVo.class);
        PageInfo<BuyRecordVo> result = new PageInfo<>(buyRecordVos, pages.getTotalPages(), pages.getLast(), pages.getTotalElements(), pages.getSize(), pages.getNumber(), pages.getFrist());
        return new Response<>(result);
    }

    @GetMapping("/unwind/pages")
    @ResponseBody
    public Response<PageInfo<BuyRecordVo>> unwindPages(StrategyUnwindQuery strategyUnwindQuery) {
        PageInfo<BuyRecordDto> pages = strategyBusiness.unwindPages(strategyUnwindQuery);
        List<BuyRecordVo> buyRecordVos = CopyBeanUtils.copyListBeanPropertiesToList(pages.getContent(), BuyRecordVo.class);
        PageInfo<BuyRecordVo> result = new PageInfo<>(buyRecordVos, pages.getTotalPages(), pages.getLast(), pages.getTotalElements(), pages.getSize(), pages.getNumber(), pages.getFrist());
        return new Response<>(result);
    }

    @GetMapping("/withdraw/pages")
    @ResponseBody
    public Response<PageInfo<BuyRecordVo>> withdrawPages(StrategyUnwindQuery strategyUnwindQuery) {
        PageInfo<BuyRecordDto> pages = strategyBusiness.withdrawPages(strategyUnwindQuery);
        List<BuyRecordVo> buyRecordVos = CopyBeanUtils.copyListBeanPropertiesToList(pages.getContent(), BuyRecordVo.class);
        PageInfo<BuyRecordVo> result = new PageInfo<>(buyRecordVos, pages.getTotalPages(), pages.getLast(), pages.getTotalElements(), pages.getSize(), pages.getNumber(), pages.getFrist());
        return new Response<>(result);
    }
}
