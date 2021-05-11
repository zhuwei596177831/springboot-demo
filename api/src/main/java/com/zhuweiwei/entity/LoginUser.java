package com.zhuweiwei.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author zww
 * @date 2020-05-03 14:13
 * @description
 **/
@Getter
@Setter
@NoArgsConstructor
@TableName(value = "t_user")
public class LoginUser implements Serializable {
    private static final long serialVersionUID = 7081469670762320246L;
    /**
     * 主键id
     */
    private String id;
    /**
     * 登录名
     */
    private String loginId;
    /**
     * 密码
     */
    private String password;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 录入时间
     */
    private LocalDateTime inputTime;
}
