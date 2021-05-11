package com.zhuweiwei.springbootlearning0405.applicationlistener;

import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author zww
 * @date 2020-04-05 16:15
 * @description
 **/
public class ApplicationStartingEventListener implements ApplicationListener<ApplicationStartingEvent> {
    @Override
    public void onApplicationEvent(ApplicationStartingEvent event) {
        System.out.println("ApplicationStartingEvent===>" + event);
    }
}
