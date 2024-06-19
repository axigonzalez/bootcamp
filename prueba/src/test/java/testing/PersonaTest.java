package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
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
		
		@Nested
		class KO {
			
		}
	}
	@Test
	void test() {
		fail("Not yet implemented");
	}

}
