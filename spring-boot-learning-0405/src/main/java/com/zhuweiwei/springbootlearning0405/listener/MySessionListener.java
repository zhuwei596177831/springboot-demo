package com.zhuweiwei.springbootlearning0405.listener;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

/**
 * @author zww
 * @date 2020-05-04 17:59
 * @description
 **/
public class MySessionListener implements SessionListener {
    @Override
    public void onStart(Session session) {
        System.out.println("会话创建：" + session.getId());
    }

    @Override
    public void onStop(Session session) {
        System.out.println("会话停止：" + session.getId());
    }

    @Override
    public void onExpiration(Session session) {
        System.out.println("会话过期：" + session.getId());
    }
}
