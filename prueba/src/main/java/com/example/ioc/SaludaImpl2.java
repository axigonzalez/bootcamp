package com.example.ioc;

import org.springframework.stereotype.Component;

//@Component
public class SaludaImpl2 implements Saluda {
	
	Entorno entorno;


	public SaludaImpl2(Entorno entorno) {
		this.entorno = entorno;
	}


	@Override
	public void saluda(String nombre) {
		System.out.println("Hola " + nombre);
	}


	@Override
	public int getContador() {
		// TODO Auto-generated method stub
		return entorno.getContador();
	}
}
