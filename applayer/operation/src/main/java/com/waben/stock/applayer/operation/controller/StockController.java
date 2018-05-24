package com.waben.stock.applayer.operation.controller;

import com.waben.stock.applayer.operation.business.StockBusiness;
import com.waben.stock.interfaces.dto.investor.InvestorDto;
import com.waben.stock.interfaces.dto.investor.SecurityAccountDto;
import com.waben.stock.interfaces.dto.publisher.PublisherDto;
import com.waben.stock.interfaces.dto.stockcontent.StockDto;
import com.waben.stock.interfaces.dto.stockcontent.StockExponentDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.StockQuery;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.vo.investor.InvestorVo;
import com.waben.stock.interfaces.vo.investor.SecurityAccountVo;
import com.waben.stock.interfaces.vo.publisher.PublisherVo;
import com.waben.stock.interfaces.vo.stockcontent.StockExponentVo;
import com.waben.stock.interfaces.vo.stockcontent.StockVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Created by yuyidi on 2017/11/22.
 * @desc
 */
@Controller
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private StockBusiness stockBusiness;

    @RequestMapping("/index")
    public String stock() {
        return "stock/manage/index";
    }

    @GetMapping("/pages")
    @ResponseBody
    public Response<PageInfo<StockVo>> pages(StockQuery stockQuery) {
        PageInfo<StockDto> pageInfo = stockBusiness.pages(stockQuery);
        List<StockVo> stockVoContent = CopyBeanUtils.copyListBeanPropertiesToList(pageInfo.getContent(), StockVo.class);
        PageInfo<StockVo> response = new PageInfo<>(stockVoContent, pageInfo.getTotalPages(), pageInfo.getLast(), pageInfo.getTotalElements(), pageInfo.getSize(), pageInfo.getNumber(), pageInfo.getFrist());
        return new Response<>(response);
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Long id, ModelMap map){
        StockDto stockDto = stockBusiness.fetchById(id);
        StockVo stockVo = CopyBeanUtils.copyBeanProperties(StockVo.class, stockDto, false);
        map.addAttribute("stock", stockVo);
        return "stock/manage/edit";
    }

    @RequestMapping("/add")
    public String add(ModelMap map) {
        List<StockExponentDto> stockExponentDtos = stockBusiness.fetchStockExponents();
        List<StockExponentVo> stockExponentVos = CopyBeanUtils.copyListBeanPropertiesToList(stockExponentDtos, StockExponentVo.class);
        map.addAttribute("stockExponentVos",stockExponentVos);
        return "stock/manage/add";
    }

    @RequestMapping("/modify")
    @ResponseBody
    public Response<Integer> modify(StockVo vo){
        StockDto requestDto = CopyBeanUtils.copyBeanProperties(StockDto.class, vo, false);
        Integer result = stockBusiness.revision(requestDto);
        return new Response<>(result);
    }

    @RequestMapping("/save")
    @ResponseBody
    public Response<StockVo> add(StockVo vo){
        StockDto requestDto = CopyBeanUtils.copyBeanProperties(StockDto.class, vo, false);
        requestDto.setStockExponent(CopyBeanUtils.copyBeanProperties(StockExponentDto.class, vo.getStockExponent(), false));
        StockDto stockDto = stockBusiness.save(requestDto);
        StockVo stockVo = CopyBeanUtils.copyBeanProperties(StockVo.class,stockDto , false);
        return new Response<>(stockVo);
    }

    @RequestMapping("/view/{id}")
    public String view(@PathVariable Long id,ModelMap map){
        StockDto stockDto = stockBusiness.fetchById(id);
        StockVo stockVo = CopyBeanUtils.copyBeanProperties(StockVo.class, stockDto, false);
        StockExponentVo stockExponentVo = CopyBeanUtils.copyBeanProperties(StockExponentVo.class, stockDto.getExponent(), false);
        stockVo.setStockExponent(stockExponentVo);
        map.addAttribute("stock", stockVo);
        return "stock/manage/view";
    }
    @RequestMapping("/delete")
    @ResponseBody
    public Response<Integer> delete(Long id){
        stockBusiness.delete(id);
        return new Response<>(1);
    }
}
