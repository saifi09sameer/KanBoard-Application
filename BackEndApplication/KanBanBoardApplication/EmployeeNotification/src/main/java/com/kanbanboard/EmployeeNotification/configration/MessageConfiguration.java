package com.kanbanboard.EmployeeNotification.configration;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageConfiguration {

    @Bean
    public Jackson2JsonMessageConverter getJackson2JsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}
