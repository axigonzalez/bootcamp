package com.catalogo.application.contracts;

import java.security.Timestamp;

import com.catalogo.application.models.NovedadesDTO;

public interface CatalogoService {

	NovedadesDTO novedades(Timestamp fecha);

}
