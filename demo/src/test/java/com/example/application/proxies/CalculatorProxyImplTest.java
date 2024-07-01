package com.example.application.proxies;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.DemoApplication;
import com.example.DemoWsApplication;
import com.example.domains.contracts.proxies.CalculatorProxy;

@SpringBootTest(classes = DemoWsApplication.class)
class CalculatorProxyImplTest {

	@Autowired
	CalculatorProxy calculadora;
	
	@Test
	void testAdd() {
		assertEquals(0.3, calculadora.add(0.1, 0.2));
	}
	
	@Test
	void testSubtract() {
		assertEquals(0.9, calculadora.subtract(1, 0.1));
	}
	
	@Test
	void testMultiply() {
		assertEquals(5, calculadora.subtract(2, 2.5));
	}
	
	@Test
	void testDivideOK() {
		assertEquals(0.5, calculadora.subtract(1, 2));
	}

}
