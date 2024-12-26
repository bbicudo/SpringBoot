package br.nom.bicudo.bruno;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.nom.bicudo.bruno.exceptions.UnsupportedMathOperationException;

@RestController
public class MathController {
	
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	
	@RequestMapping(value = "/calculate/{operation}/{numberOne}/{numberTwo}", method=RequestMethod.GET)
	public Double sum(
		@PathVariable(value = "operation") String operation,
		@PathVariable(value = "numberOne") String numberOne,
		@PathVariable(value = "numberTwo") String numberTwo
	) throws Exception {
		if (
			!isNumeric(numberOne) ||
			!isNumeric(numberTwo)
		) {
			throw new UnsupportedMathOperationException("Please inform the correct values in the order '/<operation>/<numberOne>/<numberTwo>'!");
		}
		return calculate(operation, convertToDouble(numberOne), convertToDouble(numberTwo));
	}


	private Double calculate(String op, double n1, double n2) {
		switch(op) {
			case "sum":
				return n1+n2;
			case "sub":
				return n1-n2;
			case "mult":
				return n1*n2;
			case "div":
				return n1/n2;
			default:
				throw new UnsupportedMathOperationException("Valid operations are 'sum', 'sub', 'mult', 'div'!");
		}
	}


	private Double convertToDouble(String strNumber) {
		if (strNumber == null) { 
			return 0D;
		}
		String number = strNumber.replaceAll(",", ".");

		if (isNumeric(number)) {
			return Double.parseDouble(number);
		}
		
		return 0D;
	}

	private boolean isNumeric(String strNumber) {
		if (strNumber == null) { 
			return false;
		}
		
		String number = strNumber.replaceAll(",", ".");
		
		return number.matches("[-+]?[0-9]*\\.?[0-9]+");
	}
}
