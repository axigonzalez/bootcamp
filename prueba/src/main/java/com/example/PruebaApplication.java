package com.example;

import org.springframework.beans.factory.annotation.Autowired;
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
	Saluda saludaEn;
	
	@Autowired
	Saluda saludaEs;
	
//	@Autowired(required = false)
//	SaludaImpl2 kkImpl2;
	
	
	@Override
	public void run(String... args) throws Exception {
		System.err.println("Aplicación arrancada");
		//var saluda = new Saluda();
		System.out.println(saludaEn.getContador());
		saludaEn.saluda("mundo");
		saludaEs.saluda("mundo");
		System.out.println(saludaEn.getContador());
		System.out.println(saludaEs.getContador());
		
	}

}
