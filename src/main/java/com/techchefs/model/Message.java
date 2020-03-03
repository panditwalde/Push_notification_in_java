package com.techchefs.model;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
@JsonDeserialize(as = Message.class)
public class Message  implements Serializable {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String topic;
}
  