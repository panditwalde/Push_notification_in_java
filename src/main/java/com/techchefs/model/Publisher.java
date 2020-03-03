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
@JsonDeserialize(as = Publisher	.class)

public class Publisher  implements Serializable  {
	

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String topic;
    private String message;
    private String clientid;
 
    
    

}
