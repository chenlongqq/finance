package com.waben.stock.applayer.operation.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.waben.stock.applayer.operation.business.RoleBusiness;
import com.waben.stock.applayer.operation.business.StaffBusiness;
import com.waben.stock.applayer.operation.util.SecurityAccount;
import com.waben.stock.interfaces.dto.manage.RoleDto;
import com.waben.stock.interfaces.dto.manage.StaffDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.StaffQuery;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.vo.manage.RoleVo;
import com.waben.stock.interfaces.vo.manage.StaffVo;

/**
 * @author Created by yuyidi on 2017/11/19.
 * @desc
 */
@Controller
@RequestMapping("/staff")
public class StaffController {

    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private RoleBusiness roleBusiness;
    @Autowired
    private StaffBusiness staffBusiness;

    @RequestMapping("/index")
    public String user() {
        return "manage/staff/index";
    }

    @RequestMapping("/pages")
    @ResponseBody
    public Response<PageInfo<StaffVo>> pages(StaffQuery staffQuery) {
        PageInfo<StaffDto> pageInfo = staffBusiness.staffs(staffQuery);
        List<StaffVo> staffVoContent = CopyBeanUtils.copyListBeanPropertiesToList(pageInfo.getContent(), StaffVo.class);
        PageInfo<StaffVo> response = new PageInfo<>(staffVoContent, pageInfo.getTotalPages(), pageInfo.getLast(), pageInfo.getTotalElements(), pageInfo.getSize(), pageInfo.getNumber(), pageInfo.getFrist());
        return new Response<>(response);
    }

    @RequestMapping("/view/{id}")
    public String view(@PathVariable Long id, ModelMap map){
        StaffDto staffDto = staffBusiness.fetchById(id);
        StaffVo staffVo = CopyBeanUtils.copyBeanProperties(StaffVo.class, staffDto, false);
        staffVo.setRoleVo(CopyBeanUtils.copyBeanProperties(RoleVo.class, staffDto.getRoleDto(), false));
        map.addAttribute("staff", staffVo);
        return "manage/staff/view";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Long id,ModelMap map){
        StaffDto staffDto = staffBusiness.findById(id);
        StaffVo staffVo = CopyBeanUtils.copyBeanProperties(StaffVo.class, staffDto, false);
        map.addAttribute("staff", staffVo);
        return "manage/staff/edit";
    }

    @RequestMapping("/modify")
    @ResponseBody
    public Response<StaffDto> modify(StaffVo vo){
        StaffDto requestDto = CopyBeanUtils.copyBeanProperties(StaffDto.class, vo, false);
        StaffDto result = staffBusiness.revision(requestDto);
        return new Response<>(result);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Response<Integer> delete(Long id){
        staffBusiness.delete(id);
        return new Response<>(1);
    }

    @RequestMapping("/add")
    public String add(ModelMap map) {
        List<RoleDto> roleDtos = roleBusiness.fetchRoles();
        List<RoleVo> roleVos = CopyBeanUtils.copyListBeanPropertiesToList(roleDtos, RoleVo.class);
        map.addAttribute("roleVo",roleVos);
        return "manage/staff/add";
    }

    @RequestMapping("/save")
    @ResponseBody
    public Response<StaffVo> add(StaffVo vo){
        StaffDto requestDto = CopyBeanUtils.copyBeanProperties(StaffDto.class, vo, false);
        requestDto.setRoleDto(CopyBeanUtils.copyBeanProperties(RoleDto.class, vo.getRoleVo(), false));
        StaffDto staffDto = staffBusiness.save(requestDto);
        StaffVo staffVo = CopyBeanUtils.copyBeanProperties(StaffVo.class,staffDto , false);
        return new Response<>(staffVo);
    }

    @PostMapping("/password/{password}")
    @ResponseBody
    public Response<StaffDto> password(@PathVariable String password) {
       StaffDto staffDto = (StaffDto) SecurityAccount.current().getSecurity();
       staffDto.setPassword(password);
       staffBusiness.modif(staffDto);
       return new Response<>(staffDto);
    }

    @GetMapping("/password")
    public String password() {
        return "manage/staff/password";
    }
}
