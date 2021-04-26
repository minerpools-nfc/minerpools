package com.dpool.app;

import java.nio.charset.Charset;
import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 */
@SpringBootApplication
@ComponentScan("com.dpool.app")
public class Application {

    private static ConfigurableApplicationContext applicationContext;


  

    public static void main(String[] args) {
        System.out.println("Current time zone: " + TimeZone.getDefault().getID());
        System.out.println("Current Charset: " + Charset.defaultCharset().name());
        //重新设置 user.dir
        String home = System.getProperty("agent.install.home");
        if(home!=null && home.length()>0) {
            System.setProperty("user.dir", home);
        }
        System.out.println("Current home: " + System.getProperty("user.dir"));
        applicationContext = SpringApplication.run(Application.class, args);
    }
}