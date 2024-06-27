package com.catalogo.application.service;

import java.security.Timestamp;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catalogo.application.contracts.CatalogoService;
import com.catalogo.application.models.NovedadesDTO;
import com.catalogo.domains.contracts.services.ActorService;
import com.catalogo.domains.contracts.services.CategoryService;
import com.catalogo.domains.contracts.services.FilmService;
import com.catalogo.domains.contracts.services.LanguageService;

@Service
	public class CatalogoServiceImpl implements CatalogoService {
		@Autowired
		private FilmService filmSrv;
		@Autowired
		private ActorService artorSrv;
		@Autowired
		private CategoryService categorySrv;
		@Autowired
		private LanguageService languageSrv;
		@Override
		public NovedadesDTO novedades(Timestamp fecha) {
			// TODO Auto-generated method stub
			return null;
		}

//		@Override
//		public NovedadesDTO novedades(Timestamp fecha) {
//			// Timestamp fecha = Timestamp.valueOf("2019-01-01 00:00:00");
//			if(fecha == null)
//				fecha = Timestamp.from(Instant.now().minusSeconds(36000));
//			return new NovedadesDTO(
//					filmSrv.novedades(fecha).stream().map(item -> new FilmShortDTO(item.getFilmId(), item.getTitle())).toList(), 
//					artorSrv.novedades(fecha).stream().map(item -> ActorDTO.from(item)).toList(), 
//					categorySrv.novedades(fecha), 
//					languageSrv.novedades(fecha)
//					);
//		}

	}
