package com.waben.stock.applayer.operation.controller;

import com.waben.stock.applayer.operation.business.InvestorBusiness;
import com.waben.stock.applayer.operation.business.SecurityAccountBusiness;
import com.waben.stock.interfaces.dto.investor.InvestorDto;
import com.waben.stock.interfaces.dto.investor.SecurityAccountDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.InvestorQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.SecurityAccountQuery;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.vo.investor.InvestorVo;
import com.waben.stock.interfaces.vo.investor.SecurityAccountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Created by yuyidi on 2017/11/30.
 * @desc
 */
@Controller
@RequestMapping("/securityaccount")
public class SecurityAccountController {

    @Autowired
    private SecurityAccountBusiness securityAccountBusiness;

    @RequestMapping("/index")
    public String index() {
        return "investor/securities/index";
    }

    @GetMapping("/pages")
    @ResponseBody
    public Response<PageInfo<SecurityAccountVo>> pages(SecurityAccountQuery securityAccountQuery) {
        PageInfo<SecurityAccountDto> pageInfo = securityAccountBusiness.securityAccounts(securityAccountQuery);
        List<SecurityAccountVo> securityAccountVoContent = CopyBeanUtils.copyListBeanPropertiesToList(pageInfo.getContent(), SecurityAccountVo.class);
        PageInfo<SecurityAccountVo> response = new PageInfo<>(securityAccountVoContent, pageInfo.getTotalPages(), pageInfo.getLast(), pageInfo.getTotalElements(), pageInfo.getSize(), pageInfo.getNumber(), pageInfo.getFrist());
        return new Response<>(response);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public Response<Integer> delete(Long id){
        securityAccountBusiness.delete(id);
        return new Response<>(1);
    }
}
