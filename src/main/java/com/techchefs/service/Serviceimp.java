
package com.techchefs.service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.techchefs.confing.Mqttconfing;
import com.techchefs.exception.Exceptionmessage;
import com.techchefs.exception.Mqexception;
import com.techchefs.model.Publisher;
import com.techchefs.model.Subscribe;
import com.techchefs.response.Response;

@Service


public class Serviceimp {
	
	
	@Autowired
	private AmqpAdmin admin;

	@Autowired
	private AmqpTemplate rabbitTemplate;

	@Value("${javainuse.rabbitmq.exchange}")
	private String exchange;

	@Value("${javainuse.rabbitmq.routingkey}")
	private String routingkey;
	
//	Publisher p1;
//	
//	@RabbitListener(queues = "mqtt-subscription-pandit_myclientid1qos1")
//	public void recievedMessage(Publisher messagePublishModel) throws MqttException {
//		
//		p1=messagePublishModel;
//		
//		System.out.println("Recieved Message From RabbitMQ: " + messagePublishModel);
//	}
	
	

	public Response publisher(Publisher messagePublishModel, BindingResult bindingResult)
			throws org.eclipse.paho.client.mqttv3.MqttException {

		if (bindingResult.hasErrors()) {
			throw new Mqexception(Exceptionmessage.SOME_PARAMETERS_INVALID);
			}

//		MqttMessage mqttMessage = new MqttMessage(messagePublishModel.getMessage().getBytes());		
		//Mqttconfing.getInstance().publish(messagePublishModel.getTopic(), mqttMessage);
		
//		Queue queue = new Queue(messagePublishModel.getClientid(), true, false, true);
//		Binding binding = new Binding(messagePublishModel.getClientid(), Binding.DestinationType.QUEUE, exchange, routingkey, null);
//		admin.declareQueue(queue);
//		admin.declareBinding(binding);
		rabbitTemplate.convertAndSend(messagePublishModel.getClientid(), messagePublishModel);

		return new Response(200, "message send ", true);

	}

	public Response subscribe(String topic)
			throws InterruptedException, org.eclipse.paho.client.mqttv3.MqttException {
		Subscribe mqttSubscribeModel = new Subscribe();

		CountDownLatch countDownLatch = new CountDownLatch(10);
		Mqttconfing.getInstance().subscribeWithResponse(topic, (s, mqttMessage) -> {
			mqttSubscribeModel.setMessage(new String(mqttMessage.getPayload()));
			countDownLatch.countDown();

		});

		countDownLatch.await(200, TimeUnit.MILLISECONDS);

		return new Response(200, "user message ",mqttSubscribeModel.getMessage());

	}

}
