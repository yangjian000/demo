package com.example.demo.bean;

import com.example.demo.UserRegisteredEvent;
import org.apache.juli.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;


@Component
public class Component1 {

    private static final Logger logger = LoggerFactory.getLogger(Component1.class);

    @Autowired
    private ApplicationEventPublisher publisher;


    public void register() {
        System.out.println("user register ...");
        publisher.publishEvent(new UserRegisteredEvent(this));
    }
}
