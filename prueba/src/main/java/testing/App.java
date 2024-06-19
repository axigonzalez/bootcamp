package testing;

import lombok.experimental.var;

public class App {
	
	public static void main (String [] args) {
		System.out.println("Hello world");
		var c = new Calculadora();
		System.out.println(c.add(1,2));
	}

}
