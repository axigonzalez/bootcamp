package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.platform.commons.annotation.Testable;

import lombok.experimental.var;

class PersonaTest {

	@Nested
	@DisplayName("Proceso de instanciación")
	class Create {
		@Nested
		class OK {
			@Test
			void soloNombre() {
//				var persona = new Persona(1, "Pepe");
				var persona = new Persona(1, "Juan");
				
				assertNotNull(persona);
				assertAll("Persona",
				() -> assertEquals(1, persona.getId(), "id"),
				() -> assertEquals("Juan", persona.getNombre(), "nombre"),
				() -> assertTrue(persona.getApellidos().isEmpty(), "apellidos"));
			}
		}
		
		@ParameterizedTest(name = "{0} {1}")
		@CsvSource(value = {"1, Pepito", "2, Pepito Grillo", "3,Grillo,Pepito", "4, ''"})
		void soloNombre(int id, String nombre) {
			var persona = new Persona(id, nombre);
			assertNotNull(persona);
			assertAll("Persona",
			() -> assertEquals(id, persona.getId(), "id"),
			() -> assertEquals(nombre, persona.getNombre(), "nombre"),
			() -> assertTrue(persona.getApellidos().isEmpty(), "apellidos"));
		}
	}
	
		@ParameterizedTest(name = "{0} {1}")
		@CsvSource(value = {"1, Pepito", "2, Pepito Grillo", "3,Grillo,Pepito"})
		void soloNombre(ArgumentsAccessor args) {
			var persona = args.size() == 3 ? 
					new Persona(args.getInteger(0), args.getString(1), args.getString(2)) :
					new Persona(args.getInteger(0), args.getString(1));	
				
			assertNotNull(persona);
			assertAll("Persona",
			() -> assertEquals(args.getInteger(0), persona.getId(), "id"),
			() -> assertEquals(args.getString(1), persona.getNombre(), "nombre"),
			() -> assertTrue(persona.getApellidos().isEmpty(), "apellidos"));
		}
	}
		
		@Nested
		class KO {
			@ParameterizedTest(name = "{0} {1}")
			@CsvSource(value = {"3,", "4, ''", "5, '   '"})
			void soloNombre(int id, String nombre) {
				assertThrows(Exception.class, () -> new Persona(id, nombre));
			}
		
			
		
	
	@Test
	void test() {
		fail("Not yet implemented");
	}

}
