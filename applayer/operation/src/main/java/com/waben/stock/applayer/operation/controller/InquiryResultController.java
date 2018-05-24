package com.waben.stock.applayer.operation.controller;

import com.waben.stock.applayer.operation.business.InquiryResultBusiness;
import com.waben.stock.interfaces.dto.stockoption.InquiryResultDto;
import com.waben.stock.interfaces.dto.stockoption.StockOptionOrgDto;
import com.waben.stock.interfaces.dto.stockoption.StockOptionTradeDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.vo.stockoption.InquiryResultVo;
import com.waben.stock.interfaces.vo.stockoption.StockOptionOrgVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/inquiryresult")
public class InquiryResultController {

    @Autowired
    private InquiryResultBusiness inquiryResultBusiness;

    @GetMapping("/add")
    @ResponseBody
    public Response<InquiryResultVo> add(InquiryResultVo inquiryResultVo) {
        InquiryResultDto inquiryResultDto = CopyBeanUtils.copyBeanProperties(InquiryResultDto.class, inquiryResultVo, false);
        inquiryResultDto.setOrg(CopyBeanUtils.copyBeanProperties(StockOptionOrgDto.class, inquiryResultVo.getOrg(), false));
        inquiryResultDto.setTrade(CopyBeanUtils.copyBeanProperties(StockOptionTradeDto.class, inquiryResultVo.getTrade(), false));
        InquiryResultDto inquiry =  inquiryResultBusiness.add(inquiryResultDto);
        InquiryResultVo result = CopyBeanUtils.copyBeanProperties(InquiryResultVo.class, inquiry, false);
        return new Response<>(result);
    }
}
