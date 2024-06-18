package com.example.ioc;

import io.micrometer.common.lang.NonNull;

public interface Saluda {

	void saluda(@NonNull String nombre);
	
	int getContador();

}