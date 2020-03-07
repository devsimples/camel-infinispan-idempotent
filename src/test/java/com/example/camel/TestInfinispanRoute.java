package com.example.camel;

import org.apache.camel.CamelContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestInfinispanRoute {
	
	@Autowired
    protected CamelContext camelContext;

	private static final String ROUTE_IDEMPOTENT_INFINISPAN = "direct:idempotentInfinispan";
	private static final String HEADER_NAME = "MessageID";
	
	@Test
	public void test_idempotent() {
		
		camelContext.createProducerTemplate().requestBodyAndHeader(ROUTE_IDEMPOTENT_INFINISPAN, "body", HEADER_NAME, "1");
		camelContext.createProducerTemplate().requestBodyAndHeader(ROUTE_IDEMPOTENT_INFINISPAN, "body", HEADER_NAME, "2");
		camelContext.createProducerTemplate().requestBodyAndHeader(ROUTE_IDEMPOTENT_INFINISPAN, "body", HEADER_NAME, "1"); //equal
		camelContext.createProducerTemplate().requestBodyAndHeader(ROUTE_IDEMPOTENT_INFINISPAN, "body", HEADER_NAME, "3");
	}
	
}
