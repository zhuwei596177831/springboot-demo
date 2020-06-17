package com.zhuweiwei.springbootlearning0405.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zww
 * @date 2020-04-12 18:18
 * @description
 **/
@Data
public class Test implements Serializable {
    private static final long serialVersionUID = -2760259806912837683L;
    private String id;
    private String name;
    private String accNo;
}
