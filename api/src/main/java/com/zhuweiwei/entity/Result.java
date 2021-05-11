package com.zhuweiwei.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author zww
 * @date 2020-05-03 14:40
 * @description
 **/
@Getter
@Setter
@NoArgsConstructor
public class Result<T> {

    private int errStat;

    private String errMsg;

    private T data;

    public Result(int errStat, String errMsg, T data) {
        this.errStat = errStat;
        this.errMsg = errMsg;
        this.data = data;
    }

    public static <T> Result ok(T data) {
        Result result = new Result();
        result.setErrStat(0);
        result.setErrMsg("成功");
        result.setData(data);
        return result;
    }

    public static Result ok() {
        Result result = new Result();
        result.setErrStat(0);
        result.setErrMsg("成功");
        return result;
    }

    public static Result fail(String errMsg) {
        Result result = new Result();
        result.setErrStat(1);
        result.setErrMsg(errMsg);
        return result;
    }

}
