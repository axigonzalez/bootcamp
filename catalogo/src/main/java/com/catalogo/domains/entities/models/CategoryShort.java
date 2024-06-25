package com.catalogo.domains.entities.models;

import org.springframework.beans.factory.annotation.Value;

public interface CategoryShort {
	@Value("#{target.CategoryId}")
	int getId();
	@Value("#{target.name}")
	String getNombre();

}


