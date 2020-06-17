package com.zhuweiwei.springbootlearning0405.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zww
 * @date 2020-04-06 00:07
 * @description
 **/
@Getter
@Setter
public class MyException extends RuntimeException {
    public MyException(String message) {
        super(message);
    }
}
