package com.waben.stock.applayer.operation.warpper.auth.handler;

import com.waben.stock.interfaces.pojo.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yuyidi 2017-07-20 16:35:08
 * @class com.wangbei.awre.auth.handler.ForbiddenAccessDeniedHandler
 * @description 当方法被调用时，如果没有相应角色或权限
 */
public class ForbiddenAccessDeniedHandler extends ResponseHandler implements AccessDeniedHandler {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException
            accessDeniedException) throws IOException, ServletException {
        logger.warn("用户调用{},被禁止", request.getRequestURI());
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        Response<Object> result = new Response<>();
        result.setMessage("权限不足，请联系管理员");
        result.setCode("403");
        response(result, request, response, "/403");
    }
}
