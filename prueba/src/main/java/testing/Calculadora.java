package testing;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculadora {

	private double redondea(double o) {
		return (new BigDecimal(o)).setScale(16,RoundingMode.HALF_UP).doubleValue();
	}
	
	public double add(double i, double j) {
		return redondea(i + j);
	}
	
	public double div(double i, double j) {
		if (j == 0) throw new ArithmeticException();
		return redondea(i / j);
	}
	
	public int div (int i, int j) {
		return (i / j);
	}

}
