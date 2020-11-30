package com.el.switcher.controller.handler;

import com.el.base.utils.support.exception.AbstractDomainRuntimeException;
import com.el.base.utils.support.exception.data.ErrorMessage;
import com.el.base.utils.support.exception.handler.ExceptionHandlerManager;
import com.el.switcher.entity.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * </br>
 * since 2020/11/30
 *
 * @author eddie
 */
@Slf4j
@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AbstractDomainRuntimeException.class)
    public Response<ErrorMessage> exceptionHandler(HttpServletRequest request, AbstractDomainRuntimeException e){
        log.error("Global Exception Handler intercept error, happen url: [{}] ", request.getRequestURI(), e);
        ErrorMessage errorMessage = ExceptionHandlerManager.handlerException(e);
        return Response.ofGlobalError(errorMessage);
    }

}
