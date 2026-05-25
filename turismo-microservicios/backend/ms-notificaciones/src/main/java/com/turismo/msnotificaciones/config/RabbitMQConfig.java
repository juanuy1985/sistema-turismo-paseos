package com.turismo.msnotificaciones.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${app.rabbitmq.exchange}")
    private String exchangeName;

    @Value("${app.rabbitmq.reservas.queue}")
    private String reservasQueueName;

    @Value("${app.rabbitmq.reservas.routing-key}")
    private String reservasRoutingKey;

    @Value("${app.rabbitmq.pagos.queue}")
    private String pagosQueueName;

    @Value("${app.rabbitmq.pagos.routing-key}")
    private String pagosRoutingKey;

    @Bean
    public DirectExchange turismoExchange() {
        return new DirectExchange(exchangeName);
    }

    @Bean
    public Queue reservasQueue() {
        return QueueBuilder.durable(reservasQueueName).build();
    }

    @Bean
    public Queue pagosQueue() {
        return QueueBuilder.durable(pagosQueueName).build();
    }

    @Bean
    public Binding reservasBinding(Queue reservasQueue, DirectExchange turismoExchange) {
        return BindingBuilder.bind(reservasQueue).to(turismoExchange).with(reservasRoutingKey);
    }

    @Bean
    public Binding pagosBinding(Queue pagosQueue, DirectExchange turismoExchange) {
        return BindingBuilder.bind(pagosQueue).to(turismoExchange).with(pagosRoutingKey);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
