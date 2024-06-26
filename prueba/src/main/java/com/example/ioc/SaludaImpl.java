package com.example.ioc;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import io.micrometer.common.lang.NonNull;

@Component("saludaEs")
@Qualifier("es")
@Scope("prototype")
public class SaludaImpl implements Saluda {
	
//	public static class SaludaEvento{
//		private String tipo;
//		private String destinatario;
//		
//		public SaludaEvento(String tipo, String destinatario) {
//			this.tipo = tipo;
//			this.destinatario = destinatario;
//		}
//		public String getTipo() {
//			return tipo;
//		}
//		public String getDestinatario() {
//			return destinatario;
//		}
//			
//	}
	
	public static record  SaludaEvent (String tipo, String destinatario) {}
	
	private ApplicationEventPublisher publisher;
	private Entorno entorno;
	

	public SaludaImpl(Entorno entorno, ApplicationEventPublisher publisher) {
		this.entorno = entorno;
		this.publisher = publisher;
	}
	
	protected void onSaluda(@NonNull String tipo, @NonNull String destinatario) {
		publisher.publishEvent(new SaludaEvent(tipo, destinatario));
	}


	@Override
	public void saluda(@lombok.NonNull String nombre) {
		if(nombre == null) {
			throw new IllegalArgumentException("El nombre es obligatorio");
		}
		entorno.write("Hola " + nombre.toUpperCase());
		onSaluda("saluda", nombre);
	}


	@Override
	public int getContador() {
		// TODO Auto-generated method stub
		return  entorno.getContador();
	}
}
