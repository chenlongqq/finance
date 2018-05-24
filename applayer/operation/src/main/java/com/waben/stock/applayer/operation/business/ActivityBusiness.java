package com.waben.stock.applayer.operation.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.activity.ActivityDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.service.activity.ActivityMngInterface;

@Service
public class ActivityBusiness {
	@Autowired
	@Qualifier("activityMngInterface")
	private ActivityMngInterface activityService;

	public ActivityDto findActivityById(long activityId) {
		Response<ActivityDto> response = activityService.getActivity(activityId);
		String code = response.getCode();
		if ("200".equals(code)) {
			return response.getResult();
		} else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
			throw new NetflixCircuitException(code);
		}
		throw new ServiceException(response.getCode());
	}

	public PageInfo<ActivityDto> pages(int pageNo, Integer pageSize) {
		Response<PageInfo<ActivityDto>> response = activityService.getActivityList(pageNo, pageSize);
		String code = response.getCode();
		if ("200".equals(code)) {
			return response.getResult();
		} else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
			throw new NetflixCircuitException(code);
		}
		throw new ServiceException(response.getCode());
	}

	public Void isValid(long id) {
		Response<Void> response = activityService.setValid(id);
		String code = response.getCode();
		if ("200".equals(code)) {
			return response.getResult();
		} else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
			throw new NetflixCircuitException(code);
		}
		throw new ServiceException(response.getCode());
	}

	public ActivityDto save(ActivityDto activityDto) {
		Response<ActivityDto> response = activityService.saveActivity(activityDto);
		String code = response.getCode();
		if ("200".equals(code)) {
			return response.getResult();
		} else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
			throw new NetflixCircuitException(code);
		}
		throw new ServiceException(response.getCode());
	}

	public ActivityDto revision(ActivityDto activityDto) {
		Response<ActivityDto> response = activityService.saveActivity(activityDto);
		String code = response.getCode();
		if ("200".equals(code)) {
			return response.getResult();
		} else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
			throw new NetflixCircuitException(code);
		}
		throw new ServiceException(response.getCode());
	}
}
