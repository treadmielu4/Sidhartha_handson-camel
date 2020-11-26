package com.cts;

import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class CamelProducerConsumer {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		CamelContext camel = new DefaultCamelContext();
		
		camel.addRoutes(new RouteBuilder(){
			
			public void configure() throws Exception{
				from("direct:start").to("seda:end");	
			}
		});
		
		camel.start();
		
		ProducerTemplate producer = camel.createProducerTemplate();
		
		producer.sendBody("direct:start","Hello from Apache Camel");
		
		ConsumerTemplate consumer = camel.createConsumerTemplate();
		
		String message = consumer.receiveBody("seda:end", String.class);
		
		System.out.println(message);
	}

}
