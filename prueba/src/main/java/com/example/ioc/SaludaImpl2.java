package com.example.ioc;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("saludaEn")
@Qualifier("en")
public class SaludaImpl2 implements Saluda {
	
	Entorno entorno;


	public SaludaImpl2(Entorno entorno) {
		this.entorno = entorno;
	}


	@Override
	public void saluda(String nombre) {
		System.out.println("Hello " + nombre);
	}


	@Override
	public int getContador() {
		return entorno.getContador();
	}
	
	public Optional <Entorno>getEntorno(){
		return Optional.ofNullable(entorno);
	}
}
