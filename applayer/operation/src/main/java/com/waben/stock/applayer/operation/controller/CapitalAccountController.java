package com.waben.stock.applayer.operation.controller;

import com.waben.stock.applayer.operation.business.CapitalAccountBusiness;
import com.waben.stock.applayer.operation.business.PublisherBusiness;
import com.waben.stock.interfaces.dto.publisher.CapitalAccountDto;
import com.waben.stock.interfaces.dto.publisher.PublisherDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.CapitalAccountQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.vo.publisher.CapitalAccountVo;
import com.waben.stock.interfaces.vo.publisher.PublisherVo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/capitalaccount")
public class CapitalAccountController {

    @Autowired
    private CapitalAccountBusiness captailAccountBusiness;
    
    @Autowired
    private PublisherBusiness publisherBusiness;

    @RequestMapping("/index")
    public String index() {
        return "publisher/capitalaccount/index";
    }

    @GetMapping("/pages")
    @ResponseBody
    public Response<PageInfo<CapitalAccountVo>> pages(CapitalAccountQuery capitalAccountQuery) {
        PageInfo<CapitalAccountDto> capitalAccountDtos = captailAccountBusiness.pages(capitalAccountQuery);
        List<CapitalAccountVo> capitalAccountVoContent = CopyBeanUtils.copyListBeanPropertiesToList(capitalAccountDtos.getContent(), CapitalAccountVo.class);
        PageInfo<CapitalAccountVo> result = new PageInfo<>(capitalAccountVoContent, capitalAccountDtos.getTotalPages(), capitalAccountDtos.getLast(), capitalAccountDtos.getTotalElements(), capitalAccountDtos.getSize(), capitalAccountDtos.getNumber(), capitalAccountDtos.getFrist());
        return new Response<>(result);
    }
    
    @RequestMapping("/{capitalAccountId}/view")
    public String view(@PathVariable Long capitalAccountId , ModelMap map) {
    	CapitalAccountDto capitalAccountDto = captailAccountBusiness.findById(capitalAccountId);
    	CapitalAccountVo capitalAccountVo = CopyBeanUtils.copyBeanProperties(CapitalAccountVo.class, capitalAccountDto, false);
    	map.addAttribute("capitalAccount", capitalAccountVo);
    	PublisherDto publisherDto = publisherBusiness.fetchById(capitalAccountDto.getPublisherId());
    	PublisherVo publisherVo = CopyBeanUtils.copyBeanProperties(PublisherVo.class, publisherDto, false);
    	map.addAttribute("publisher", publisherVo);
    	return "publisher/capitalaccount/view";
    }
    
    @RequestMapping("/{capitalAccountId}/edit")
    public String edit(@PathVariable Long capitalAccountId , ModelMap map) {
    	CapitalAccountDto capitalAccountDto = captailAccountBusiness.findById(capitalAccountId);
    	CapitalAccountVo capitalAccountVo = CopyBeanUtils.copyBeanProperties(CapitalAccountVo.class, capitalAccountDto, false);
    	map.addAttribute("capitalAccount", capitalAccountVo);
    	return "publisher/capitalaccount/edit";
    }
    
    @RequestMapping("/modify")
    @ResponseBody
    public Response<CapitalAccountVo> modify(CapitalAccountVo capitalAccountVo){
    	CapitalAccountDto request = CopyBeanUtils.copyBeanProperties(CapitalAccountDto.class, capitalAccountVo, false);
    	CapitalAccountDto response = captailAccountBusiness.modifyCapitalAccount(request);
    	CapitalAccountVo result = CopyBeanUtils.copyBeanProperties(CapitalAccountVo.class, response, false);
    	return new Response<>(result);
    }
}
