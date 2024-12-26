package br.nom.bicudo.bruno.helpers;

import br.nom.bicudo.bruno.exceptions.UnsupportedMathOperationException;

public class InputValidator {

	public static void checkSupportedValues(String numberOne, String numberTwo) {
		if (
			!isNumeric(numberOne) ||
			!isNumeric(numberTwo)
		) {
			throw new UnsupportedMathOperationException("Please type only numbers!");
		}	
	}

	public static boolean isNumeric(String strNumber) {
		if (strNumber == null) { 
			return false;
		}
		
		String number = strNumber.replaceAll(",", ".");
		
		return number.matches("[-+]?[0-9]*\\.?[0-9]+");
	}
}
