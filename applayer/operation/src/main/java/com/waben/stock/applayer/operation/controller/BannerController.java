package com.waben.stock.applayer.operation.controller;

import com.waben.stock.applayer.operation.business.BannerBusiness;
import com.waben.stock.interfaces.dto.manage.BannerDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.BannerQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.vo.manage.BannerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/banner")
public class BannerController {

    @Autowired
    private BannerBusiness bannerBusiness;

    @RequestMapping("/index")
    public String index() {
        return "manage/banner/index";
    }

    @GetMapping("/pages")
    @ResponseBody
    public Response<PageInfo<BannerVo>> pages(BannerQuery query) {
        PageInfo<BannerDto> pageInfo = bannerBusiness.pages(query);
        List<BannerVo> bannerVoContent = CopyBeanUtils.copyListBeanPropertiesToList(pageInfo.getContent(), BannerVo.class);
        PageInfo<BannerVo> response = new PageInfo<>(bannerVoContent, pageInfo.getTotalPages(), pageInfo.getLast(), pageInfo.getTotalElements(), pageInfo.getSize(), pageInfo.getNumber(), pageInfo.getFrist());
        return new Response<>(response);
    }

    @RequestMapping("/view/{id}")
    public String view(@PathVariable Long id, ModelMap map){
        BannerDto bannerDto = bannerBusiness.fetchById(id);
        BannerVo bannerVo = CopyBeanUtils.copyBeanProperties(BannerVo.class, bannerDto, false);
        map.addAttribute("banner", bannerVo);
        return "manage/banner/view";
    }
    @RequestMapping("/delete")
    @ResponseBody
    public Response<Integer> delete(Long id){
        bannerBusiness.delete(id);
        return new Response<>(1);
    }

    @RequestMapping("/add")
    public String add() {
        return "manage/banner/add";
    }

    @RequestMapping("/save")
    @ResponseBody
    public Response<BannerVo> add(BannerVo vo){
        BannerDto requestDto = CopyBeanUtils.copyBeanProperties(BannerDto.class, vo, false);
        BannerDto bannerDto = bannerBusiness.save(requestDto);
        BannerVo bannerVo = CopyBeanUtils.copyBeanProperties(BannerVo.class,bannerDto , false);
        return new Response<>(bannerVo);
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Long id,ModelMap map){
        BannerDto bannerDto = bannerBusiness.fetchById(id);
        BannerVo circularsVo = CopyBeanUtils.copyBeanProperties(BannerVo.class, bannerDto, false);
        map.addAttribute("banner", circularsVo);
        return "manage/banner/edit";
    }

    @RequestMapping("/modify")
    @ResponseBody
    public Response<BannerVo> modify(BannerVo vo){
        BannerDto requestDto = CopyBeanUtils.copyBeanProperties(BannerDto.class, vo, false);
        BannerDto responseDto = bannerBusiness.revision(requestDto);
        BannerVo result = CopyBeanUtils.copyBeanProperties(BannerVo.class, responseDto, false);
        return new Response<>(result);
    }
}
