package com.turismo.msreservas.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String RESERVAS_EXCHANGE = "reservas.exchange";
    public static final String RESERVAS_QUEUE = "reservas.queue";
    public static final String RESERVAS_ROUTING_KEY = "reservas.routing.key";

    @Bean
    public DirectExchange reservasExchange() {
        return new DirectExchange(RESERVAS_EXCHANGE);
    }

    @Bean
    public Queue reservasQueue() {
        return QueueBuilder.durable(RESERVAS_QUEUE).build();
    }

    @Bean
    public Binding reservasBinding(Queue reservasQueue, DirectExchange reservasExchange) {
        return BindingBuilder.bind(reservasQueue).to(reservasExchange).with(RESERVAS_ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}
