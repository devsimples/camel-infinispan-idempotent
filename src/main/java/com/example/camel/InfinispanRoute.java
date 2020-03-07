package com.example.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.infinispan.processor.idempotent.InfinispanIdempotentRepository;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.Configuration;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.springframework.stereotype.Component;

@Component
public class InfinispanRoute extends RouteBuilder{

	
	@Override
	public void configure() throws Exception {

		Configuration conf = new ConfigurationBuilder().addServers("localhost").forceReturnValues(true).build();
		RemoteCacheManager manager = new RemoteCacheManager(conf, true );
		
		from("direct:idempotentInfinispan").id("Your Idempotent")
			.log("Executing idempotentInfinispan")
			
	        .idempotentConsumer( header("MessageID"), new InfinispanIdempotentRepository(manager, "events-performance-cache"))
	        
	        .log("Processing idempotentInfinispan")
			.to("mock:foo");
		
	}

	
}
