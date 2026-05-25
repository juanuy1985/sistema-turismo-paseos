package com.turismo.msreservas.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${app.rabbitmq.reservas.exchange}")
    private String reservasExchangeName;

    @Value("${app.rabbitmq.reservas.queue}")
    private String reservasQueueName;

    @Value("${app.rabbitmq.reservas.routing-key}")
    private String reservasRoutingKey;

    @Bean
    public DirectExchange reservasExchange() {
        return new DirectExchange(reservasExchangeName);
    }

    @Bean
    public Queue reservasQueue() {
        return QueueBuilder.durable(reservasQueueName).build();
    }

    @Bean
    public Binding reservasBinding(Queue reservasQueue, DirectExchange reservasExchange) {
        return BindingBuilder.bind(reservasQueue).to(reservasExchange).with(reservasRoutingKey);
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
