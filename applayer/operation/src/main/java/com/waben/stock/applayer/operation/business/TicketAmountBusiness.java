package com.waben.stock.applayer.operation.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.activity.TicketAmountDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.service.activity.TicketMngInterface;

@Service
public class TicketAmountBusiness {
	@Autowired
	@Qualifier("ticketMngInterface")
	private TicketMngInterface ticketAmountService;

	public PageInfo<TicketAmountDto> pages(int pageNo, Integer pageSize) {
		Response<PageInfo<TicketAmountDto>> response = ticketAmountService.getTicketAmountList(pageNo, pageSize);
		String code = response.getCode();
		if ("200".equals(code)) {
			return response.getResult();
		} else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
			throw new NetflixCircuitException(code);
		}
		throw new ServiceException(response.getCode());
	}

	public TicketAmountDto save(TicketAmountDto ticketAmountDto) {
		Response<TicketAmountDto> response = ticketAmountService.saveTicketAmount(ticketAmountDto);
		String code = response.getCode();
		if ("200".equals(code)) {
			return response.getResult();
		} else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
			throw new NetflixCircuitException(code);
		}
		throw new ServiceException(response.getCode());
	}

	public Void remove(long ticketAmountId) {
		Response<Void> response = ticketAmountService.deleteTicket(ticketAmountId);
		String code = response.getCode();
		if ("200".equals(code)) {
			return response.getResult();
		} else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
			throw new NetflixCircuitException(code);
		}
		throw new ServiceException(response.getCode());
	}

	public TicketAmountDto findTicketAmountById(long ticketAmountId) {
		Response<TicketAmountDto> response = ticketAmountService.getTicketAmount(ticketAmountId);
		String code = response.getCode();
		if ("200".equals(code)) {
			return response.getResult();
		} else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
			throw new NetflixCircuitException(code);
		}
		throw new ServiceException(response.getCode());
	}

	public TicketAmountDto revision(TicketAmountDto ticketAmountDto) {
		Response<TicketAmountDto> response = ticketAmountService.saveTicketAmount(ticketAmountDto);
		String code = response.getCode();
		if ("200".equals(code)) {
			return response.getResult();
		} else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
			throw new NetflixCircuitException(code);
		}
		throw new ServiceException(response.getCode());
	}
}
