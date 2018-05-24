package com.waben.stock.applayer.operation.controller;

import com.waben.stock.applayer.operation.business.TicketAmountBusiness;
import com.waben.stock.interfaces.dto.activity.ActivityDto;
import com.waben.stock.interfaces.dto.activity.TicketAmountDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/ticket")
public class TicketAmountController {
    @Autowired
    private TicketAmountBusiness  ticketAmountBusiness;
    @RequestMapping("/index")
    public String index() {
        return "activity/ticket/index";
    }

    @GetMapping("/pages")
    @ResponseBody
    public Response<PageInfo<TicketAmountDto>> pages(int pageNo, Integer pageSize) {
        PageInfo<TicketAmountDto> pages = ticketAmountBusiness.pages(pageNo, pageSize);
        return new Response<>(pages);
    }

    @RequestMapping("/add")
    public String add() {
        return "activity/ticket/add";
    }

    @RequestMapping("/save")
    @ResponseBody
    public Response<TicketAmountDto> add(TicketAmountDto ticketAmountDto) {
        TicketAmountDto result = ticketAmountBusiness.save(ticketAmountDto);
        return new Response<>(result);
    }

    @RequestMapping("/delete/{ticketAmountId}")
    @ResponseBody
    public Response<Void> drop(@PathVariable long ticketAmountId){
        Void result = ticketAmountBusiness.remove(ticketAmountId);
        return new Response<>(result);
    }

    @RequestMapping("/edit/{ticketAmountId}")
    public String edit(@PathVariable long ticketAmountId, ModelMap map) {
        TicketAmountDto ticketAmountDto = ticketAmountBusiness.findTicketAmountById(ticketAmountId);
        map.addAttribute("ticket",ticketAmountDto);
        return "activity/ticket/edit";
    }

    @RequestMapping("/modify")
    @ResponseBody
    public Response<TicketAmountDto> modify(TicketAmountDto ticketAmountDto) {
        TicketAmountDto result = ticketAmountBusiness.revision(ticketAmountDto);
        return new Response<>(result);
    }

    @RequestMapping("/view/{ticketAmountId}")
    public String view(@PathVariable long ticketAmountId, ModelMap map) {
        TicketAmountDto ticket = ticketAmountBusiness.findTicketAmountById(ticketAmountId);
        map.addAttribute("ticket",ticket);
        return "activity/ticket/view";
    }
}
