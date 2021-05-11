package com.zhuweiwei.springbootlearning0405.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author zww
 * @date 2020-04-14 22:12
 * @description
 **/
public interface UserRepository extends JpaRepository<User, Long> {
}
