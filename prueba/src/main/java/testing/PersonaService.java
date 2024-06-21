package testing;

import java.awt.event.ItemEvent;

import org.apache.tomcat.util.openssl.pem_password_cb;

import lombok.experimental.var;

public class PersonaService {
	private PersonaRepository dao;

	public PersonaService(PersonaRepository dao) {
		this.dao = dao;
	}
	
	public void ponMayusculas(int id) {
		var item = dao.getOne(id);
		if(item.isEmpty())
			throw new IllegalArgumentException("Id no encontrado");
		var p = item.get();
		p.setNombre(p.getNombre().toUpperCase());
		dao.modify(p);
		
	}
}
