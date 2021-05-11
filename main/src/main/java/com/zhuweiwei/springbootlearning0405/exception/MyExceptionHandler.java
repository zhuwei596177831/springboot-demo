package com.zhuweiwei.springbootlearning0405.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author zww
 * @date 2020-04-06 00:06
 * @description
 **/
@ControllerAdvice
public class MyExceptionHandler {

    private static final String APPLICATION_JSON_UTF_8 = "application/json;charset=utf-8";

    @ExceptionHandler(value = {MyException.class})
    public void exceptionHandler(MyException e, HttpServletResponse response) throws IOException {
        writeMessage(response, APPLICATION_JSON_UTF_8, e.getMessage());
    }

    private void writeMessage(HttpServletResponse response, String contentType, String message) throws IOException {
        response.setContentType(contentType);
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(message);
    }

}
