package com.zhuweiwei.springbootlearning0405.commandLineRunner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author zww
 * @date 2020-04-18 13:24
 * @description
 **/
@Component
public class MyCommandLineRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("MyCommandLineRunner run......");
    }
}
