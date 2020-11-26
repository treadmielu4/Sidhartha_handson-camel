package com.cts;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.SimpleRegistry;

public class CamelTimer {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		MyBean myBean = new MyBean();
		SimpleRegistry registry = new SimpleRegistry();
		registry.bind("bean1", MyBean.class, myBean);
		
		CamelContext camel = new DefaultCamelContext();
		
			camel.addRoutes(new RouteBuilder(){
			
			public void configure() throws Exception{
				from("timer:myTimer?period=1000")
				.setBody(simple("Hello from camel at ${header.firedTime}")).
					to("stream:out")
					.to("bean:bean1?method=sayHello");
					
//					.bean(new MyBean(),"sayHello");
				}
			
		});
		
		
		camel.start();
		Thread.sleep(5000);
		camel.stop();
		
		
	}

}
