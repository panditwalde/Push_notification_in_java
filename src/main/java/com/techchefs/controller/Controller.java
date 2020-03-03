package com.techchefs.controller;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techchefs.model.Message;
import com.techchefs.model.Publisher;
import com.techchefs.response.Response;
import com.techchefs.service.Serviceimp;

@RestController
@RequestMapping("/")
@CrossOrigin
@JsonComponent
public class Controller {

	@Autowired
	Serviceimp serviceimp;
	
	
	
	@PostMapping("/publisher")
	public ResponseEntity<Response> publishMessage(@RequestBody Publisher messagePublishModel,
			BindingResult bindingResult) throws org.eclipse.paho.client.mqttv3.MqttException {
	System.out.println(messagePublishModel);

		return new ResponseEntity<Response>(serviceimp.publisher(messagePublishModel, bindingResult), HttpStatus.OK);

	}

	@PostMapping("/subscribe")
	public ResponseEntity<Response> subscribe(@RequestBody Message messagePublishModel) throws InterruptedException, MqttException
			 {
          System.out.println(messagePublishModel);
		return new ResponseEntity<Response>(serviceimp.subscribe(messagePublishModel.getTopic()), HttpStatus.OK);

	}
	
	

}
