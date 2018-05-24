package com.waben.stock.applayer.operation.controller;


import com.waben.stock.applayer.operation.business.BindCardBusiness;
import com.waben.stock.applayer.operation.business.CapitalAccountBusiness;
import com.waben.stock.applayer.operation.business.CapitalFlowBusiness;
import com.waben.stock.applayer.operation.business.PublisherBusiness;
import com.waben.stock.interfaces.dto.publisher.BindCardDto;
import com.waben.stock.interfaces.dto.publisher.CapitalAccountDto;
import com.waben.stock.interfaces.dto.publisher.CapitalFlowDto;
import com.waben.stock.interfaces.dto.publisher.PublisherDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.CapitalFlowQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.PublisherQuery;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.vo.publisher.BindCardVo;
import com.waben.stock.interfaces.vo.publisher.CapitalAccountVo;
import com.waben.stock.interfaces.vo.publisher.PublisherVo;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/publisher")
public class PublisherController {

    @Autowired
    private PublisherBusiness publisherBusiness;
    
    @Autowired
    private CapitalFlowBusiness capitalFlowBusiness;
    
    @Autowired
    private BindCardBusiness bindCardBusiness;
    
    @Autowired
    private CapitalAccountBusiness capitalAccountBusiness;
    
    @RequestMapping("/index")
    public String index() {
        return "publisher/manage/index";
    }

    @GetMapping("/pages")
    @ResponseBody
    public Response<PageInfo<PublisherVo>> pages(PublisherQuery publisherQuery) {
        PageInfo<PublisherDto> pageInfo = publisherBusiness.pages(publisherQuery);
        List<PublisherVo> publisherVoContent = CopyBeanUtils.copyListBeanPropertiesToList(pageInfo.getContent(), PublisherVo.class);
        PageInfo<PublisherVo> response = new PageInfo<>(publisherVoContent, pageInfo.getTotalPages(), pageInfo.getLast(), pageInfo.getTotalElements(), pageInfo.getSize(), pageInfo.getNumber(), pageInfo.getFrist());
        return new Response<>(response);
    }
    
    @RequestMapping("/view/{id}")
    public String view(@PathVariable Long id,ModelMap map){
    	PublisherDto publisherDto = publisherBusiness.fetchById(id);
    	PublisherVo publisherVo = CopyBeanUtils.copyBeanProperties(PublisherVo.class, publisherDto, false);
    	map.addAttribute("publisher", publisherVo);
    	CapitalAccountDto capitalAccountDto = capitalAccountBusiness.findByPublisherId(id);
    	CapitalAccountVo capitalAccountVo = CopyBeanUtils.copyBeanProperties(CapitalAccountVo.class, capitalAccountDto, false);
    	map.addAttribute("capitalAccount", capitalAccountVo);
    	return "publisher/manage/view";
    }
    
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Long id,ModelMap map){
    	PublisherDto publisherDto = publisherBusiness.fetchById(id);
    	PublisherVo publisherVo = CopyBeanUtils.copyBeanProperties(PublisherVo.class, publisherDto, false);
    	map.addAttribute("publisher", publisherVo);
    	return "publisher/manage/edit";
    }
    @RequestMapping("/modify")
    @ResponseBody
    public Response<PublisherVo> modify(PublisherVo vo){
    	PublisherDto requestDto = CopyBeanUtils.copyBeanProperties(PublisherDto.class, vo, false);
    	PublisherDto responseDto = publisherBusiness.revision(requestDto);
    	PublisherVo result = CopyBeanUtils.copyBeanProperties(PublisherVo.class, responseDto, false);
    	return new Response<>(result);
    }
    
    
    @RequestMapping("/bindCards")
    @ResponseBody
    public Response<List<BindCardVo>> fetchBindCardsByPublisherId(Long publisherId){
    	List<BindCardDto> bindCardDtos = bindCardBusiness.fetchBindCardByPublisherId(publisherId);
    	List<BindCardVo> result = CopyBeanUtils.copyListBeanPropertiesToList(bindCardDtos, BindCardVo.class);
    	return new Response<List<BindCardVo>>(result);
    }
    
    @RequestMapping("/capitalflow/pages")
    @ResponseBody
    public Response<PageInfo<CapitalFlowDto>> fetchCapitalFlowPages(CapitalFlowQuery capitalFlowQuery){
    	PageInfo<CapitalFlowDto> response = capitalFlowBusiness.pages(capitalFlowQuery);
        return new Response<>(response);
    }
}
