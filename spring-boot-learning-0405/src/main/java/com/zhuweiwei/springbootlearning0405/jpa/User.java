package com.zhuweiwei.springbootlearning0405.jpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author zww
 * @date 2020-04-14 22:08
 * @description
 **/
@Entity
@Table(name = "user")
@Setter
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(nullable = false)
    private String userAge;

}
