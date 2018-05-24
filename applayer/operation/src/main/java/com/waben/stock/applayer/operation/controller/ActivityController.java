package com.waben.stock.applayer.operation.controller;

import com.waben.stock.applayer.operation.business.ActivityBusiness;
import com.waben.stock.interfaces.dto.activity.ActivityDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private ActivityBusiness activityBusiness;

    @RequestMapping("/index")
    public String index() {
        return "activity/manage/index";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Response<ActivityDto> fetchActivityById(@PathVariable long id) {
        ActivityDto activityDto = activityBusiness.findActivityById(id);
        return new Response<>(activityDto);
    }

    @GetMapping("/pages")
    @ResponseBody
    public Response<PageInfo<ActivityDto>> pages(int pageNo, Integer pageSize) {
        PageInfo<ActivityDto> pages = activityBusiness.pages(pageNo, pageSize);
        return new Response<>(pages);
    }

    @RequestMapping("/isvalid/{id}")
    @ResponseBody
    public Response<Void> isValid(@PathVariable long id) {
        Void valid = activityBusiness.isValid(id);
        return new Response<>(valid);
    }

    @RequestMapping("/view/{id}")
    public String view(@PathVariable long id, ModelMap map) {
        ActivityDto activity = activityBusiness.findActivityById(id);
        map.addAttribute("activity",activity);
        return "activity/manage/view";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable long id, ModelMap map) {
        ActivityDto activity = activityBusiness.findActivityById(id);
        map.addAttribute("activity",activity);
        return "activity/manage/edit";
    }


    @RequestMapping("/add")
    public String add() {
        return "activity/manage/add";
    }

    @RequestMapping("/save")
    @ResponseBody
    public Response<ActivityDto> add(ActivityDto activityDto) {
        ActivityDto result = activityBusiness.save(activityDto);
        return new Response<>(result);
    }

    @RequestMapping("/modify")
    @ResponseBody
    public Response<ActivityDto> modify(ActivityDto activityDto) {
        ActivityDto result = activityBusiness.revision(activityDto);
        return new Response<>(result);
    }
}
