package com.techchefs.confing;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
	
	

	@Value("${javainuse.rabbitmq.exchange}")
	String exchange;

	@Value("${javainuse.rabbitmq.routingkey}")
	private String routingkey;
    @Autowired
	private ConnectionFactory connectionFactory;

	@Bean
	Queue queue() {
		
		return new Queue("mqtt-subscription-pandit_myclientid1qos1\n" + 
				"", true, false, true);
	}

	@Bean
	DirectExchange exchange() {
		return new DirectExchange(exchange);
	}

	@Bean
	Binding binding(Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(routingkey);
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
	
	@Bean
	SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
	    SimpleRabbitListenerContainerFactoryConfigurer containerFactoryConfigurer, 
	    ConnectionFactory connectionFactory) {

	    SimpleRabbitListenerContainerFactory listenerContainerFactory =
	            new SimpleRabbitListenerContainerFactory();
	    containerFactoryConfigurer.configure(listenerContainerFactory, connectionFactory);

	    return listenerContainerFactory;
	}

	
	@Bean
	public AmqpAdmin amqpAdmin() {
	    return new RabbitAdmin(connectionFactory);
	}
	


}
