package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.ioc.Saluda;


@SpringBootApplication
public class PruebaApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(PruebaApplication.class, args);
	}
	
	@Autowired
	Saluda saluda2;
	
	@Autowired
	Saluda saluda;
	
	
	@Override
	public void run(String... args) throws Exception {
		System.err.println("Aplicación arrancada");
		//var saluda = new Saluda();
		System.out.println(saluda2.getContador());
		saluda.saluda("mundo");
		saluda.saluda("mundo");
		System.out.println(saluda2.getContador());
		System.out.println(saluda.getContador());
		
	}

}
