package com.example.demo.bean;

import com.example.demo.UserRegisteredEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
public class Component2 {
    private static final Logger logger = LoggerFactory.getLogger(Component2.class);

    @EventListener
    public void add(UserRegisteredEvent event) {
        System.out.println(event);
        System.out.println("send message ...");
    }
}
