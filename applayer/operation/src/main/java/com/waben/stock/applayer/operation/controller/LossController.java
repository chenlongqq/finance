package com.waben.stock.applayer.operation.controller;

import com.waben.stock.applayer.operation.business.LossBusiness;
import com.waben.stock.interfaces.dto.stockcontent.AmountValueDto;
import com.waben.stock.interfaces.dto.stockcontent.LossDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.AmountValueQuery;
import com.waben.stock.interfaces.pojo.query.LossQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.vo.stockcontent.AmountValueVo;
import com.waben.stock.interfaces.vo.stockcontent.LossVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/loss")
public class LossController {

    @Autowired
    private LossBusiness lossBusiness;

    @GetMapping("/index")
    public String index() {
        return "stock/loss/index";
    }

    @GetMapping("/pages")
    @ResponseBody
    public Response<PageInfo<LossVo>> pages(LossQuery query) {
        PageInfo<LossDto> pageInfo = lossBusiness.pages(query);
        List<LossVo> lossVoContent = CopyBeanUtils.copyListBeanPropertiesToList(pageInfo.getContent(), LossVo.class);
        PageInfo<LossVo> response = new PageInfo<>(lossVoContent, pageInfo.getTotalPages(), pageInfo.getLast(), pageInfo.getTotalElements(), pageInfo.getSize(), pageInfo.getNumber(), pageInfo.getFrist());
        return new Response<>(response);
    }

    @RequestMapping("/view/{id}")
    public String view(@PathVariable Long id, ModelMap map){
        LossDto lossDto = lossBusiness.fetchById(id);
        LossVo lossVo = CopyBeanUtils.copyBeanProperties(LossVo.class, lossDto, false);
        map.addAttribute("loss", lossVo);
        return "stock/loss/view";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Long id, ModelMap map){
        LossDto lossDto = lossBusiness.fetchById(id);
        LossVo lossVo = CopyBeanUtils.copyBeanProperties(LossVo.class, lossDto, false);
        map.addAttribute("loss", lossVo);
        return "stock/loss/edit";
    }

    @RequestMapping("/add")
    public String add(){
        return "stock/loss/add";
    }

    @RequestMapping("/modify")
    @ResponseBody
    public Response<LossVo> modify(LossVo vo){
        LossDto requestDto = CopyBeanUtils.copyBeanProperties(LossDto.class, vo, false);
        LossDto responseDto = lossBusiness.revision(requestDto);
        LossVo result = CopyBeanUtils.copyBeanProperties(LossVo.class, responseDto, false);
        return new Response<>(result);
    }

    @RequestMapping("/save")
    @ResponseBody
    public Response<LossVo> add(LossVo vo){
        LossDto requestDto = CopyBeanUtils.copyBeanProperties(LossDto.class, vo, false);
        LossDto responseDto = lossBusiness.save(requestDto);
        LossVo result = CopyBeanUtils.copyBeanProperties(LossVo.class, responseDto, false);
        return new Response<>(result);
    }
}
