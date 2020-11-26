package com.cts;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.SimpleRegistry;

import com.mysql.cj.jdbc.MysqlDataSource;

public class CamelDatabase {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		MysqlDataSource dataSource = new MysqlDataSource();
		
		dataSource.setUrl("jdbc:mysql://localhost:3306/camel");
		dataSource.setUser("root");
		dataSource.setPassword("root");
		
		SimpleRegistry registry = new SimpleRegistry();
		registry.bind("myDatasource", MysqlDataSource.class, dataSource);
		
		CamelContext camel = new DefaultCamelContext(registry);
		
		camel.addRoutes(new RouteBuilder(){
			
			
			public void configure() throws Exception{
				
				from("direct:start")
					.to("jdbc:myDatasource")
						.bean(new JdbcHandler() ,"display");
				
				
			}
			
			
			
		});

		camel.start();
		
		ProducerTemplate producer = camel.createProducerTemplate();
		producer.sendBody("direct:start", " select * from customer");
		
		

	}

}
