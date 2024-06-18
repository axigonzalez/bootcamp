package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.ioc.Saluda;
import com.example.ioc.SaludaImpl2;


@SpringBootApplication
public class PruebaApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(PruebaApplication.class, args);
	}
	
	@Autowired
	@Qualifier("es")
	Saluda saluda;
	
	@Autowired
	@Qualifier("en")
	Saluda saluda2;
	
//	@Autowired(required = false)
//	SaludaImpl2 kkImpl2;
	
	
	@Override
	public void run(String... args) throws Exception {
		System.err.println("Aplicación arrancada");
		//var saluda = new Saluda();
		System.out.println(saluda.getContador());
		saluda.saluda("mundo");
		saluda2.saluda("mundo");
		System.out.println(saluda.getContador());
		System.out.println(saluda2.getContador());
		
	}

}
