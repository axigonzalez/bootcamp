package com.catalogo.domains.entities.models;

import com.catalogo.domains.entities.Category;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class CategoryDTO {
	@JsonProperty("id")
	private int categoryId;
	@JsonProperty("nombre")
	private String name;

	
	public static CategoryDTO from(Category source) {
		return new CategoryDTO(
				source.getCategoryId(), 
				source.getName());
	}
	
	public static Category from(CategoryDTO source) {
		return new Category(
				source.getCategoryId(),
				source.getName());
				
	}
}

