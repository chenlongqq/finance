package com.waben.stock.applayer.operation.controller;

import com.waben.stock.applayer.operation.business.BuyRecordBusiness;
import com.waben.stock.applayer.operation.business.InvestorBusiness;
import com.waben.stock.applayer.operation.business.SecurityAccountBusiness;
import com.waben.stock.applayer.operation.util.SecurityAccount;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.dto.investor.InvestorDto;
import com.waben.stock.interfaces.dto.investor.SecurityAccountDto;
import com.waben.stock.interfaces.dto.publisher.BindCardDto;
import com.waben.stock.interfaces.dto.publisher.CapitalAccountDto;
import com.waben.stock.interfaces.dto.publisher.PublisherDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.InvestorQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.stock.SecuritiesStockEntrust;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.vo.investor.InvestorVo;
import com.waben.stock.interfaces.vo.investor.SecurityAccountVo;
import com.waben.stock.interfaces.vo.publisher.BindCardVo;
import com.waben.stock.interfaces.vo.publisher.CapitalAccountVo;
import com.waben.stock.interfaces.vo.publisher.PublisherVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Created by yuyidi on 2017/11/29.
 * @desc
 */
@Controller
@RequestMapping("/investor")
public class InvestorController {

    @Autowired
    private InvestorBusiness investorBusiness;
    @Autowired
    private BuyRecordBusiness buyRecordBusiness;

    @RequestMapping("/index")
    public String index() {
        return "investor/manage/index";
    }

    @GetMapping("/pages")
    @ResponseBody
    public Response<PageInfo<InvestorVo>> pages(InvestorQuery investorQuery) {
        PageInfo<InvestorDto> pageInfo = investorBusiness.investors(investorQuery);
        List<InvestorVo> investorVoContent = CopyBeanUtils.copyListBeanPropertiesToList(pageInfo.getContent(), InvestorVo.class);
        PageInfo<InvestorVo> response = new PageInfo<>(investorVoContent, pageInfo.getTotalPages(), pageInfo.getLast(), pageInfo.getTotalElements(), pageInfo.getSize(), pageInfo.getNumber(), pageInfo.getFrist());
        return new Response<>(response);
    }

    @RequestMapping("/view/{id}")
    public String view(@PathVariable Long id,ModelMap map){
        InvestorDto investorDto = investorBusiness.findById(id);
        InvestorVo investorVo = CopyBeanUtils.copyBeanProperties(InvestorVo.class, investorDto, false);
        map.addAttribute("investor", investorVo);
        SecurityAccountDto securityAccountDto = investorDto.getSecurityAccountDto();
        SecurityAccountVo securityAccountVo = CopyBeanUtils.copyBeanProperties(SecurityAccountVo.class, securityAccountDto, false);
        map.addAttribute("securityAccount", securityAccountVo);
        return "investor/manage/view";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Long id,ModelMap map){
        InvestorDto investorDto = investorBusiness.findById(id);
        InvestorVo investorVo = CopyBeanUtils.copyBeanProperties(InvestorVo.class, investorDto, false);
        map.addAttribute("investor", investorVo);
        return "investor/manage/edit";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Response<Integer> delete(Long id){
        investorBusiness.delete(id);
        return new Response<>(1);
    }
    @RequestMapping("/modify")
    @ResponseBody
    public Response<Integer> modify(InvestorVo vo){
        InvestorDto requestDto = CopyBeanUtils.copyBeanProperties(InvestorDto.class, vo, false);
        Integer result = investorBusiness.revision(requestDto);
        return new Response<>(result);
    }
    /***
     * @author yuyidi 2017-12-02 20:16:51
     * @method buyRecordEntrust
     * @param buyrecord
     * @return com.waben.stock.interfaces.pojo.Response<com.waben.stock.interfaces.pojo.stock.SecuritiesStockEntrust>
     * @description 投资点买记录买入
     */
    @RequestMapping("/buyrecord/{buyrecord}/buyin")
    @ResponseBody
    public Response<SecuritiesStockEntrust> buyRecordBuyIn(@PathVariable("buyrecord") Long buyrecord) {
        InvestorDto investorDto = (InvestorDto) SecurityAccount.current().getSecurity();
        BuyRecordDto buyRecordDto = buyRecordBusiness.fetchBuyRecord(buyrecord);
        SecuritiesStockEntrust result = investorBusiness.buyIn(investorDto, buyRecordDto);
        return new Response<>(result);
    }

    @RequestMapping("/buyrecord/{buyrecord}/sellout")
    @ResponseBody
    public Response<SecuritiesStockEntrust> buyRecordSellOut(@PathVariable("buyrecord") Long buyrecord, BigDecimal entrustPrice) {
        InvestorDto investorDto = (InvestorDto) SecurityAccount.current().getSecurity();
        BuyRecordDto buyRecordDto = buyRecordBusiness.fetchBuyRecord(buyrecord);
        SecuritiesStockEntrust result = investorBusiness.sellOut(investorDto, buyRecordDto,entrustPrice);
        return new Response<>(result);
    }

}
