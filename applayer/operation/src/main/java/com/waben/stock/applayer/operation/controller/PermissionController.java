package com.waben.stock.applayer.operation.controller;

import com.waben.stock.applayer.operation.business.PermissionBusiness;
import com.waben.stock.interfaces.dto.manage.PermissionDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.PermissionQuery;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.vo.manage.PermissionVo;
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
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionBusiness permissionBusiness;

    @RequestMapping("/index")
    public String stock() {
        return "manage/permission/index";
    }

    @GetMapping("/pages")
    @ResponseBody
    public Response<PageInfo<PermissionVo>> pages(PermissionQuery query) {
        PageInfo<PermissionDto> pageInfo = permissionBusiness.pages(query);
        List<PermissionVo> permissionVoContent = CopyBeanUtils.copyListBeanPropertiesToList(pageInfo.getContent(), PermissionVo.class);
        PageInfo<PermissionVo> response = new PageInfo<>(permissionVoContent, pageInfo.getTotalPages(), pageInfo.getLast(), pageInfo.getTotalElements(), pageInfo.getSize(), pageInfo.getNumber(), pageInfo.getFrist());
        return new Response<>(response);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Response<Integer> delete(Long id){
        permissionBusiness.delete(id);
        return new Response<>(1);
    }

    @RequestMapping("/add")
    public String add(ModelMap map) {
        List<PermissionDto> permissionDtos = permissionBusiness.fetchPermissions();
        List<PermissionVo> permissionVos = CopyBeanUtils.copyListBeanPropertiesToList(permissionDtos, PermissionVo.class);
        map.addAttribute("permissions",permissionVos);
        return "manage/permission/add";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Long id, ModelMap map){
        List<PermissionDto> permissionDtos = permissionBusiness.fetchPermissions();
        PermissionDto permissionDto = permissionBusiness.fetchById(id);
        PermissionVo permissionVo = CopyBeanUtils.copyBeanProperties(PermissionVo.class, permissionDto, false);
        List<PermissionVo> permissionVos = CopyBeanUtils.copyListBeanPropertiesToList(permissionDtos, PermissionVo.class);
        map.addAttribute("permission", permissionVo);
        map.addAttribute("permissions", permissionVos);
        return "manage/permission/edit";
    }

    @RequestMapping("/modify")
    @ResponseBody
    public Response<PermissionVo> modify(PermissionVo vo){
        PermissionDto requestDto = CopyBeanUtils.copyBeanProperties(PermissionDto.class, vo, false);
        PermissionDto responseDto = permissionBusiness.revision(requestDto);
        PermissionVo result = CopyBeanUtils.copyBeanProperties(PermissionVo.class, responseDto, false);
        return new Response<>(result);
    }

    @RequestMapping("/save")
    @ResponseBody
    public Response<PermissionVo> add(PermissionVo vo){
        PermissionDto requestDto = CopyBeanUtils.copyBeanProperties(PermissionDto.class, vo, false);
        PermissionDto permissionDto = permissionBusiness.save(requestDto);
        PermissionVo permissionVo = CopyBeanUtils.copyBeanProperties(PermissionVo.class,permissionDto , false);
        return new Response<>(permissionVo);
    }
}
