package com.waben.stock.applayer.operation.controller;


import com.waben.stock.applayer.operation.business.RoleBusiness;
import com.waben.stock.applayer.operation.util.SecurityAccount;
import com.waben.stock.interfaces.dto.manage.RoleDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.RoleQuery;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.vo.manage.RoleVo;
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
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleBusiness roleBusiness;

    @RequestMapping("/index")
    public String stock() {
        return "manage/role/index";
    }

    @GetMapping("/pages")
    @ResponseBody
    public Response<PageInfo<RoleVo>> pages(RoleQuery stockQuery) {
        PageInfo<RoleDto> pageInfo = roleBusiness.pages(stockQuery);
        List<RoleVo> roleVoContent = CopyBeanUtils.copyListBeanPropertiesToList(pageInfo.getContent(), RoleVo.class);
        PageInfo<RoleVo> response = new PageInfo<>(roleVoContent, pageInfo.getTotalPages(), pageInfo.getLast(), pageInfo.getTotalElements(), pageInfo.getSize(), pageInfo.getNumber(), pageInfo.getFrist());
        return new Response<>(response);
    }

    @RequestMapping("/view/{id}")
    public String view(@PathVariable Long id, ModelMap map){
        RoleDto roleDto = roleBusiness.fetchById(id);
        RoleVo roleVo = CopyBeanUtils.copyBeanProperties(RoleVo.class, roleDto, false);
        map.addAttribute("role", roleVo);
        return "manage/role/view";
    }

    @RequestMapping("/authorize/{id}")
    public String authorize(@PathVariable Long id, ModelMap map){
        RoleDto roleDto = roleBusiness.fetchById(id);
        map.addAttribute("permissions", roleDto.getPermissionDtos());
        return "manage/role/authorize";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Long id, ModelMap map){
        RoleDto roleDto = roleBusiness.fetchById(id);
        RoleVo roleVo = CopyBeanUtils.copyBeanProperties(RoleVo.class, roleDto, false);
        map.addAttribute("role", roleVo);
        return "manage/role/edit";
    }

    @RequestMapping("/modify")
    @ResponseBody
    public Response<RoleVo> modify(RoleVo vo){
        RoleDto requestDto = CopyBeanUtils.copyBeanProperties(RoleDto.class, vo, false);
        RoleDto responseDto = roleBusiness.revision(requestDto);
        RoleVo result = CopyBeanUtils.copyBeanProperties(RoleVo.class, responseDto, false);
        return new Response<>(result);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Response<Integer> delete(Long id){
        roleBusiness.delete(id);
        return new Response<>(1);
    }

    @RequestMapping("/add")
    public String add() {
        return "manage/role/add";
    }
    @RequestMapping("/save")
    @ResponseBody
    public Response<RoleVo> add(RoleVo vo){
        RoleDto requestDto = CopyBeanUtils.copyBeanProperties(RoleDto.class, vo, false);
        RoleDto roleDto = roleBusiness.save(requestDto);
        RoleVo roleVo = CopyBeanUtils.copyBeanProperties(RoleVo.class,roleDto , false);
        return new Response<>(roleVo);
    }

    @GetMapping("/permissions")
    public String permissions(ModelMap map) {
        SecurityAccount.current().getMenus();
        return null;
    }



}
