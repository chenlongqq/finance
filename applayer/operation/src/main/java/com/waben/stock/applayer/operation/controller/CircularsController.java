package com.waben.stock.applayer.operation.controller;

import com.waben.stock.applayer.operation.business.CircularsBusiness;
import com.waben.stock.interfaces.dto.manage.CircularsDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.CircularsQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.vo.manage.CircularsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Created by yuyidi on 2017/12/11.
 * @desc
 */
@Controller
@RequestMapping("/circulars")
public class CircularsController {

    @Autowired
    private CircularsBusiness circularsBusiness;

    @RequestMapping("/index")
    public String stock() {
        return "manage/circulars/index";
    }

    @GetMapping("/pages")
    @ResponseBody
    public Response<PageInfo<CircularsVo>> pages(CircularsQuery query) {
        PageInfo<CircularsDto> pageInfo = circularsBusiness.pages(query);
        List<CircularsVo> circularsVoContent = CopyBeanUtils.copyListBeanPropertiesToList(pageInfo.getContent(), CircularsVo.class);
        PageInfo<CircularsVo> response = new PageInfo<>(circularsVoContent, pageInfo.getTotalPages(), pageInfo.getLast(), pageInfo.getTotalElements(), pageInfo.getSize(), pageInfo.getNumber(), pageInfo.getFrist());
        return new Response<>(response);
    }

    @RequestMapping("/view/{id}")
    public String view(@PathVariable Long id, ModelMap map){
        CircularsDto circularsDto = circularsBusiness.fetchById(id);
        CircularsVo circularsVo = CopyBeanUtils.copyBeanProperties(CircularsVo.class, circularsDto, false);
        map.addAttribute("circulars", circularsVo);
        return "manage/circulars/view";
    }
    @RequestMapping("/delete")
    @ResponseBody
    public Response<Integer> delete(Long id){
        circularsBusiness.delete(id);
        return new Response<>(1);
    }
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Long id,ModelMap map){
        CircularsDto circularsDto = circularsBusiness.fetchById(id);
        CircularsVo circularsVo = CopyBeanUtils.copyBeanProperties(CircularsVo.class, circularsDto, false);
        map.addAttribute("circulars", circularsVo);
        return "manage/circulars/edit";
    }

    @RequestMapping("/modify")
    @ResponseBody
    public Response<CircularsDto> modify(CircularsVo vo){
        CircularsDto requestDto = CopyBeanUtils.copyBeanProperties(CircularsDto.class, vo, false);
        CircularsDto result = circularsBusiness.revision(requestDto);
        return new Response<>(result);
    }

    @RequestMapping("/add")
    public String add() {
        return "manage/circulars/add";
    }

    @RequestMapping("/save")
    @ResponseBody
    public Response<CircularsVo> add(CircularsVo vo){
        CircularsDto requestDto = CopyBeanUtils.copyBeanProperties(CircularsDto.class, vo, false);
        CircularsDto circularsDto = circularsBusiness.save(requestDto);
        CircularsVo circularsVo = CopyBeanUtils.copyBeanProperties(CircularsVo.class,circularsDto , false);
        return new Response<>(circularsVo);
    }
}
