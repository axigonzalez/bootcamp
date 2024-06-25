package com.example;

import java.awt.event.ItemEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.entities.Actor;
import com.example.ioc.Rango;
import com.example.ioc.Saluda;
import com.example.ioc.SaludaImpl2;

import ch.qos.logback.core.joran.conditional.ElseAction;



@SpringBootApplication
public class PruebaApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(PruebaApplication.class, args);
	}
	
	@Autowired
	ActorRepository dao;
	
	@Override
	public void run(String... args) throws Exception {
//		var item = dao.findById(301);
//		if(item.isEmpty())
//			System.err.println("No encontrado");
//	} else {
//		System.out.println(item.get());
//		
//		dao.findAll().forEach(System.out::println);	
//		var actor = new Actor(0, "Pepito", "Grillo");
//		System.out.println(dao.save(actor));
//		
		var item = dao.findById(201);
//		if(item.isEmpty())
//			System.err.println("No encontrado");
//		
//		if(!item.isEmpty()) {
//			var actor= item.get();
//			actor.setFirstName(actor.getFirstName().toUpperCase());
//			dao.save(actor);
//		}
//			System.out.println(item.get());
//		dao.findAll().forEach(System.out::println);
		
//		dao.findTop5ByLastNameStartingWithOrderByFirstNameDesc("P").forEach(System.out::println);
//		dao.findTop5ByLastNameStartingWith("P", Sort.by("LastName").ascending()).forEach(System.out::println);

		dao.findByActorIdGreaterThanEqual(200).forEach(System.out::println);
		dao.findAll((root, query, builder) -> builder.greaterThanOrEqualTo(root.get("actorId"), 200)).forEach(System.out::println);;
		dao.findAll((root, query, builder) -> builder.lessThan(root.get("actorId"), 10)).forEach(System.out::println);;

		}
	
//	@Autowired
//	@Qualifier("es")
//	Saluda saluda;
//	
//	@Autowired
//	@Qualifier("en")
//	Saluda saluda2;
//	
//	@Autowired
//	Rango rango;
//	
////	@Autowired(required = false)
////	SaludaImpl2 kkImpl2;
//	
	


}
