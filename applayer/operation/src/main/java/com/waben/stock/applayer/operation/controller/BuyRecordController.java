package com.waben.stock.applayer.operation.controller;

import com.waben.stock.applayer.operation.business.BuyRecordBusiness;
import com.waben.stock.applayer.operation.business.SettlementBusiness;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.dto.buyrecord.SettlementDto;
import com.waben.stock.interfaces.dto.investor.InvestorDto;
import com.waben.stock.interfaces.dto.publisher.CapitalFlowDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.BuyRecordQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.vo.buyrecord.BuyRecordVo;
import com.waben.stock.interfaces.vo.buyrecord.SettlementVo;
import com.waben.stock.interfaces.vo.investor.InvestorVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/buyrecord")
public class BuyRecordController {

    @Autowired
    private BuyRecordBusiness buyRecordBusiness;
    @Autowired
    private SettlementBusiness settlementBusiness;
    @RequestMapping("/index")
    public String index(){
    	return "investor/buyrecord/index";
    }

    @GetMapping("/pages")
    @ResponseBody
    public Response<PageInfo<BuyRecordVo>> pages(BuyRecordQuery buyRecordQuery) {
        PageInfo<BuyRecordDto> pageInfo = buyRecordBusiness.pages(buyRecordQuery);
        List<BuyRecordVo> buyRecordVoContent = CopyBeanUtils.copyListBeanPropertiesToList(pageInfo.getContent(), BuyRecordVo.class);
        PageInfo<BuyRecordVo> response = new PageInfo<>(buyRecordVoContent, pageInfo.getTotalPages(), pageInfo.getLast(), pageInfo.getTotalElements(), pageInfo.getSize(), pageInfo.getNumber(), pageInfo.getFrist());
        return new Response<>(response);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Response<Integer> delete(Long id){
        buyRecordBusiness.delete(id);
        return new Response<>(1);
    }

    @GetMapping("/{buyrecord}")
    @ResponseBody
    public Response<BuyRecordDto> buyRecord(@PathVariable Long buyrecord) {
        BuyRecordDto result = buyRecordBusiness.fetchBuyRecord(buyrecord);
        return new Response<>(result);
    }

    @RequestMapping("/view/{id}")
    public String view(@PathVariable Long id, ModelMap map){
        SettlementDto settlementDto = settlementBusiness.findByBuyRecord(id);
        SettlementVo settlementVo = CopyBeanUtils.copyBeanProperties(SettlementVo.class, settlementDto, false);
        map.addAttribute("settlement", settlementVo);
        return "investor/buyrecord/view";
    }
}
