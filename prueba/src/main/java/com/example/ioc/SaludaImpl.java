package com.example.ioc;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import io.micrometer.common.lang.NonNull;

@Component("saludaEs")
@Qualifier("es")
@Scope("prototype")
public class SaludaImpl implements Saluda {
	
	Entorno entorno;


	public SaludaImpl(Entorno entorno) {
		this.entorno = entorno;
	}


	@Override
	public void saluda(@lombok.NonNull String nombre) {
		if(nombre == null) {
			throw new IllegalArgumentException("El nombre es obligatorio");
		}
		entorno.write("Hola " + nombre.toUpperCase());
	}


	@Override
	public int getContador() {
		// TODO Auto-generated method stub
		return  entorno.getContador();
	}
}
