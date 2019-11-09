package com.example.demo.advice;

import com.alibaba.fastjson.JSON;
import com.example.demo.dto.ResultDTO;
import com.example.demo.exception.CustomizedErrorCode;
import com.example.demo.exception.CustomizedException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizedExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request, Throwable ex, Model model, HttpServletResponse response) {
        HttpStatus status = getStatus(request);
        ResultDTO resultDTO = null;
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)) {
            //返回JSON API
            if (ex instanceof CustomizedException) {
                resultDTO = ResultDTO.errorOf((CustomizedException) ex);
            } else {
                resultDTO = ResultDTO.errorOf(CustomizedErrorCode.SYS_ERROR);
            }
            try (PrintWriter writer = response.getWriter()) {
                response.setCharacterEncoding("UTF-8");
                response.setStatus(200);
                response.setContentType("application/json");
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException e) {
            }
            return null;
        } else {
            //返回页面跳转 HTML
            if (ex instanceof CustomizedException) {
                model.addAttribute("messages", ex.getMessage());
            } else {
                model.addAttribute("messages", CustomizedErrorCode.SYS_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
