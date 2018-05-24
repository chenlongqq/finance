package com.waben.stock.applayer.operation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.waben.stock.applayer.operation.business.LossBusiness;
import com.waben.stock.applayer.operation.business.StrategyTypeBusiness;
import com.waben.stock.interfaces.dto.stockcontent.LossDto;
import com.waben.stock.interfaces.dto.stockcontent.StrategyTypeDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.StrategyTypeQuery;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.vo.stockcontent.LossVo;
import com.waben.stock.interfaces.vo.stockcontent.StrategyTypeVo;

/**
 * @author Created by yuyidi on 2017/12/6.
 * @desc
 */
@Controller
@RequestMapping("/strategytype")
public class StrategyTypeController {

    @Autowired
    private StrategyTypeBusiness strategyTypeBusiness;

    @Autowired
    private LossBusiness lossBusiness;

    @RequestMapping("/index")
    public String stock() {
        return "stock/strategytype/index";
    }

    @GetMapping("/pages")
    @ResponseBody
    public Response<PageInfo<StrategyTypeVo>> pages(StrategyTypeQuery strategyTypeQuery) {
        PageInfo<StrategyTypeDto> pageInfo = strategyTypeBusiness.pages(strategyTypeQuery);
        List<StrategyTypeVo> strategyTypeVoContent = CopyBeanUtils.copyListBeanPropertiesToList(pageInfo.getContent(), StrategyTypeVo.class);
        PageInfo<StrategyTypeVo> response = new PageInfo<>(strategyTypeVoContent, pageInfo.getTotalPages(), pageInfo.getLast(), pageInfo.getTotalElements(), pageInfo.getSize(), pageInfo.getNumber(), pageInfo.getFrist());
        return new Response<>(response);
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Long id, ModelMap map){
        StrategyTypeDto strategyTypeDto = strategyTypeBusiness.fetchById(id);
        StrategyTypeVo strategyTypeVo = CopyBeanUtils.copyBeanProperties(StrategyTypeVo.class, strategyTypeDto, false);
        List<LossDto> lossDtos = lossBusiness.findAllLoss();
        List<LossVo> lossVos = CopyBeanUtils.copyListBeanPropertiesToList(lossDtos, LossVo.class);
        map.addAttribute("strategyType", strategyTypeVo);
        map.addAttribute("lossVos", lossVos);
        return "stock/strategytype/edit";
    }

    @RequestMapping("/view/{id}")
    public String view(@PathVariable Long id, ModelMap map){
        StrategyTypeDto strategyTypeDto = strategyTypeBusiness.fetchById(id);
        StrategyTypeVo strategyTypeVo = CopyBeanUtils.copyBeanProperties(StrategyTypeVo.class, strategyTypeDto, false);
        map.addAttribute("strategyType", strategyTypeVo);
        return "stock/strategytype/view";
    }

    @RequestMapping("/modify")
    @ResponseBody
    public Response<StrategyTypeVo> modify(StrategyTypeVo vo){
        StrategyTypeDto requestDto = CopyBeanUtils.copyBeanProperties(StrategyTypeDto.class, vo, false);
        StrategyTypeDto responseDto = strategyTypeBusiness.revision(requestDto, vo.getLoss());
        StrategyTypeVo result = CopyBeanUtils.copyBeanProperties(StrategyTypeVo.class, responseDto, false);
        return new Response<>(result);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Response<Integer> delete(Long id){
        strategyTypeBusiness.delete(id);
        return new Response<>(1);
    }
    @RequestMapping("/add")
    public String add(ModelMap map) {
        return "stock/strategytype/add";
    }

    @RequestMapping("/save")
    @ResponseBody
    public Response<StrategyTypeVo> add(StrategyTypeVo vo){
        StrategyTypeDto requestDto = CopyBeanUtils.copyBeanProperties(StrategyTypeDto.class, vo, false);
        StrategyTypeDto responseDto = strategyTypeBusiness.save(requestDto);
        StrategyTypeVo result = CopyBeanUtils.copyBeanProperties(StrategyTypeVo.class, responseDto, false);
        return new Response<>(result);
    }
}
