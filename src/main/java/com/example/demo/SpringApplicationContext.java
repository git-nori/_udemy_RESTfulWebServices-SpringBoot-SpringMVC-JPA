package com.example.demo;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

// @Autowiredするためには、使用するクラスもDIコンテナで管理されていなければならない(@Controller, @Component, etc...)
/*
 * 使用する側がDIコンテナで管理されておらず、DIコンテナに管理されているBeanを取得するために使用するクラス
 * ex. AuthenticationFilterクラスに@Componentとし、@Autowiredを使ってuserServiceImplをinjectionすることも可能(このクラスは不要)
 */
public class SpringApplicationContext implements ApplicationContextAware{
    private static ApplicationContext CONTEXT;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        CONTEXT = applicationContext;
    }

    public static Object getBean(String beanName) {
        return CONTEXT.getBean(beanName);
    }
}
