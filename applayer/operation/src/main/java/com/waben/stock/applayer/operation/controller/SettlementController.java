package com.waben.stock.applayer.operation.controller;

import com.waben.stock.applayer.operation.business.SettlementBusiness;
import com.waben.stock.interfaces.dto.buyrecord.SettlementDto;
import com.waben.stock.interfaces.dto.investor.InvestorDto;
import com.waben.stock.interfaces.dto.investor.SecurityAccountDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.SettlementQuery;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.vo.buyrecord.SettlementVo;
import com.waben.stock.interfaces.vo.investor.InvestorVo;
import com.waben.stock.interfaces.vo.investor.SecurityAccountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/settlement")
public class SettlementController {

    @Autowired
    private SettlementBusiness settlementBusiness;

    @GetMapping("/index")
    public String index() {
        return "strategytype/holding/index";
    }

    @GetMapping("/pages")
    @ResponseBody
    public Response<PageInfo<SettlementDto>> pages(SettlementQuery settlementQuery) {
        PageInfo<SettlementDto> response = settlementBusiness.pages(settlementQuery);
        return new Response<>(response);
    }

}
