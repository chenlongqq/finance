package com.waben.stock.applayer.operation.controller;

import com.waben.stock.applayer.operation.business.PublisherBusiness;
import com.waben.stock.applayer.operation.business.PublisherTicketBusiness;
import com.waben.stock.interfaces.dto.activity.PublisherTeleChargeDto;
import com.waben.stock.interfaces.dto.activity.PublisherTicketDto;
import com.waben.stock.interfaces.dto.publisher.PublisherDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/material")
public class PublisherTicketController {

    @Autowired
    private PublisherTicketBusiness publisherTicketBusiness;

    @Autowired
    private PublisherBusiness publisherBusiness;

    @RequestMapping("/index")
    public String index() {
        return "winning/material/index";
    }
    @GetMapping("/pages")
    @ResponseBody
    public Response<PageInfo<PublisherTicketDto>> pages(int pageNo, Integer pageSize) {
        PageInfo<PublisherTicketDto> pages = publisherTicketBusiness.pages(pageNo, pageSize);
        for(int i=0; i<pages.getContent().size(); i++) {
            PublisherDto publisherDto = publisherBusiness.fetchById(pages.getContent().get(i).getPublisherId());
            pages.getContent().get(i).setPublisherPhone(publisherDto.getPhone());
        }
        return new Response<>(pages);
    }

}
