package com.waben.stock.applayer.operation.controller;

import com.waben.stock.applayer.operation.business.*;
import com.waben.stock.interfaces.dto.publisher.PublisherDto;
import com.waben.stock.interfaces.dto.stockoption.InquiryResultDto;
import com.waben.stock.interfaces.dto.stockoption.OfflineStockOptionTradeDto;
import com.waben.stock.interfaces.dto.stockoption.StockOptionOrgDto;
import com.waben.stock.interfaces.dto.stockoption.StockOptionTradeDto;
import com.waben.stock.interfaces.enums.OfflineStockOptionTradeState;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.StockOptionTradeQuery;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.vo.stockoption.InquiryResultVo;
import com.waben.stock.interfaces.vo.stockoption.OfflineStockOptionTradeVo;
import com.waben.stock.interfaces.vo.stockoption.StockOptionOrgVo;
import com.waben.stock.interfaces.vo.stockoption.StockOptionTradeVo;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/option")
public class OptionController {
    org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private StockOptionTradeBusiness stockOptionTradeBusiness;
    @Autowired
    private StockOptionOrgBusiness stockOptionOrgBusiness;
    @Autowired
    private OfflineStockOptionTradeBusiness offlineStockOptionTradeBusiness;
    @Autowired
    private InquiryResultBusiness inquiryResultBusiness;
    @Autowired
    private PublisherBusiness publisherBusiness;
    @RequestMapping("/index")
    public String index(ModelMap map){
        List<StockOptionOrgDto> stockOptionOrgDtos = stockOptionOrgBusiness.fetchStockOptionOrgs();
        List<StockOptionOrgVo> stockOptionOrgVos = CopyBeanUtils.copyListBeanPropertiesToList(stockOptionOrgDtos, StockOptionOrgVo.class);
        map.addAttribute("orgVo",stockOptionOrgVos);
        return "options/index";
    }

    @RequestMapping("/pages")
    @ResponseBody
    public Response<PageInfo<StockOptionTradeVo>> pages(StockOptionTradeQuery query){
        PageInfo<StockOptionTradeDto> pageInfo = stockOptionTradeBusiness.pages(query);
        List<StockOptionTradeVo> stockOptionTradeVoContent = CopyBeanUtils.copyListBeanPropertiesToList(pageInfo.getContent(), StockOptionTradeVo.class);
        PageInfo<StockOptionTradeVo> response = new PageInfo<>(stockOptionTradeVoContent, pageInfo.getTotalPages(), pageInfo.getLast(), pageInfo.getTotalElements(), pageInfo.getSize(), pageInfo.getNumber(), pageInfo.getFrist());
        for (int i = 0; i < pageInfo.getContent().size(); i++) {
            OfflineStockOptionTradeDto offlineTradeDto = pageInfo.getContent().get(i).getOfflineTradeDto();
            if(offlineTradeDto!=null) {
                OfflineStockOptionTradeVo offlineStockOptionTradeVo = CopyBeanUtils.copyBeanProperties(
                        OfflineStockOptionTradeVo.class,offlineTradeDto , false);
                response.getContent().get(i).setOfflineTrade(offlineStockOptionTradeVo);
            }
            try {
                PublisherDto publisherDto = publisherBusiness.fetchById(pageInfo.getContent().get(i).getPublisherId());
                response.getContent().get(i).setTest(publisherDto.getIsTest());
            }catch (Exception e) {
                logger.error("查询Publisher出错：{}",e.getMessage());
            }
            try {
                InquiryResultDto inquiryResultDto = inquiryResultBusiness.fetchByTrade(pageInfo.getContent().get(i).getId());
                response.getContent().get(i).setInquiryResultVo(CopyBeanUtils.copyBeanProperties(InquiryResultVo.class,inquiryResultDto , false));
            }catch (Exception e) {
                logger.error("该交易暂未有机构报价：{}",e.getMessage());
            }
        }
        return new Response<>(response);
    }


    @RequestMapping("/settlement/{id}")
    @ResponseBody
    public Response<StockOptionTradeVo> settlement(@PathVariable Long id){
        logger.info("结算id:{}",id);
        StockOptionTradeDto stockOptionTradeDto = stockOptionTradeBusiness.settlement(id);
        StockOptionTradeVo stockOptionTradeVo = CopyBeanUtils.copyBeanProperties(StockOptionTradeVo.class, stockOptionTradeDto, false);
        return new Response<>(stockOptionTradeVo);
    }

    /**
     * 询价  根据订单信息，发送询价单邮件
     * @param id
     * @return
     */
    @RequestMapping("/inquiry/{id}")
    @ResponseBody
    public Response<Boolean> inquiry(@PathVariable Long id){
        Boolean result = stockOptionTradeBusiness.inquiry(id);
        return new Response<>(result);
    }

    /**
     * 申购
     * @param id
     * @return
     */
    @RequestMapping("/purchase/{id}")
    @ResponseBody
    public Response<Boolean> purchase(@PathVariable Long id){
        Boolean result = stockOptionTradeBusiness.purchase(id);
        return new Response<>(result);
    }

    /**
     * 行权
     * @param id
     * @return
     */
    @RequestMapping("/exercise/{id}")
    @ResponseBody
    public Response<Boolean> exercise(@PathVariable Long id){
        Boolean result = stockOptionTradeBusiness.exercise(id);
        return new Response<>(result);
    }

    @RequestMapping("/success/{id}")
    @ResponseBody
    public Response<StockOptionTradeVo> success(@PathVariable Long id){
        StockOptionTradeDto stockOptionTradeDto = stockOptionTradeBusiness.success(id);
        StockOptionTradeVo stockOptionTradeVo = CopyBeanUtils.copyBeanProperties(StockOptionTradeVo.class, stockOptionTradeDto, false);
        return new Response<>(stockOptionTradeVo);
    }

    @RequestMapping("/{id}")
    @ResponseBody
    public Response<StockOptionTradeVo> fetchById(@PathVariable Long id){
        StockOptionTradeDto stockOptionTradeDto = stockOptionTradeBusiness.findById(id);
        StockOptionTradeVo stockOptionTradeVo = CopyBeanUtils.copyBeanProperties(StockOptionTradeVo.class, stockOptionTradeDto, false);
        if(stockOptionTradeDto.getOfflineTradeDto()!=null) {
            OfflineStockOptionTradeVo offlineStockOptionTradeVo = CopyBeanUtils.copyBeanProperties(OfflineStockOptionTradeVo.class, stockOptionTradeDto.getOfflineTradeDto(), false);
            stockOptionTradeVo.setOfflineTrade(offlineStockOptionTradeVo);
        }
        return new Response<>(stockOptionTradeVo);
    }

    @RequestMapping("/fail/{id}")
    @ResponseBody
    public Response<StockOptionTradeVo> fail(@PathVariable Long id){
        StockOptionTradeDto stockOptionTradeDto = stockOptionTradeBusiness.fail(id);
        StockOptionTradeVo stockOptionTradeVo = CopyBeanUtils.copyBeanProperties(StockOptionTradeVo.class, stockOptionTradeDto, false);
        return new Response<>(stockOptionTradeVo);
    }

    /**
     * 机构单确认
     * @param id 机构单号
     */
    @GetMapping("/institution/{id}")
    @ResponseBody
    public Response<Boolean> confirm(@PathVariable Long id) {
        OfflineStockOptionTradeDto offlineStockOptionTradeDto = offlineStockOptionTradeBusiness.find(id);
        OfflineStockOptionTradeState state = offlineStockOptionTradeDto.getState();
        if (state.equals(OfflineStockOptionTradeState.TURNOVER)) {
            //询价状态下，点击确认
        } else if (state.equals(OfflineStockOptionTradeState.APPLYRIGHT)) {
            //申购状态下 点击确认
        }
        return null;
    }
}
