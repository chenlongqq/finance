package com.waben.stock.applayer.operation.controller;

import com.waben.stock.applayer.operation.business.CapitalFlowBusiness;
import com.waben.stock.applayer.operation.business.PublisherBusiness;
import com.waben.stock.interfaces.dto.publisher.CapitalFlowDto;
import com.waben.stock.interfaces.dto.publisher.PublisherDto;
import com.waben.stock.interfaces.enums.CapitalFlowType;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.CapitalFlowQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.vo.publisher.CapitalFlowVo;
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
@RequestMapping("/capitalflow")
public class CapitalFlowController {

    @Autowired
    private CapitalFlowBusiness  capitalFlowBusiness;
    
    @Autowired
    private PublisherBusiness publisherBusiness;

    @RequestMapping("/index")
    public String index(ModelMap map) {
    	map.addAttribute("capitalFlowTypes", CapitalFlowType.values());
        return "publisher/account/index";
    }

    @GetMapping("/pages")
    @ResponseBody
    public Response<PageInfo<CapitalFlowVo>> pages(CapitalFlowQuery capitalFlowQuery) {
        PageInfo<CapitalFlowDto> capitalFlowDtos = capitalFlowBusiness.pages(capitalFlowQuery);
        List<CapitalFlowVo> capitalFlowVoContent = CopyBeanUtils.copyListBeanPropertiesToList(capitalFlowDtos.getContent(), CapitalFlowVo.class);
        PageInfo<CapitalFlowVo> result = new PageInfo<>(capitalFlowVoContent, capitalFlowDtos.getTotalPages(), capitalFlowDtos.getLast(), capitalFlowDtos.getTotalElements(), capitalFlowDtos.getSize(), capitalFlowDtos.getNumber(), capitalFlowDtos.getFrist());
        return new Response<>(result);
    }
    
    @RequestMapping("/{capitalFlowId}/view")
    public String view(@PathVariable Long capitalFlowId, ModelMap map) {
    	CapitalFlowDto capitalFlowDto = capitalFlowBusiness.findById(capitalFlowId);
    	CapitalFlowVo capitalFlowVo = CopyBeanUtils.copyBeanProperties(CapitalFlowVo.class, capitalFlowDto, false);
    	map.addAttribute("capitalFlow", capitalFlowVo);
    	PublisherDto publisherDto = publisherBusiness.fetchById(capitalFlowDto.getPublisherId());
    	PublisherVo publisherVo = CopyBeanUtils.copyBeanProperties(PublisherVo.class, publisherDto, false);
    	map.addAttribute("publisher", publisherVo);
    	return "publisher/account/view";
    }
}
