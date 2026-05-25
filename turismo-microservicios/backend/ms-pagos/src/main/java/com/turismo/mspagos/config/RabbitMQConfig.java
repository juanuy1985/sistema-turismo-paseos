package com.turismo.mspagos.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${app.rabbitmq.pagos.exchange}")
    private String pagosExchangeName;

    @Value("${app.rabbitmq.pagos.queue}")
    private String pagosQueueName;

    @Value("${app.rabbitmq.pagos.routing-key}")
    private String pagosRoutingKey;

    @Bean
    public DirectExchange pagosExchange() {
        return new DirectExchange(pagosExchangeName);
    }

    @Bean
    public Queue pagosQueue() {
        return QueueBuilder.durable(pagosQueueName).build();
    }

    @Bean
    public Binding pagosBinding(Queue pagosQueue, DirectExchange pagosExchange) {
        return BindingBuilder.bind(pagosQueue).to(pagosExchange).with(pagosRoutingKey);
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
