package br.nom.bicudo.bruno;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import br.nom.bicudo.bruno.helpers.InputValidator;
import br.nom.bicudo.bruno.helpers.MathOperations;
import br.nom.bicudo.bruno.helpers.NumberConverter;

@RestController
public class MathController {
	
	private MathOperations mathOperations = new MathOperations();
	
	@GetMapping("/sum/{numberOne}/{numberTwo}")
	public Double sum(
		@PathVariable String numberOne,
		@PathVariable String numberTwo
	) throws Exception {
		InputValidator.checkSupportedValues(numberOne, numberTwo);

		return mathOperations.sum(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));
	}

	@GetMapping("/sub/{numberOne}/{numberTwo}")
	public Double sub(
		@PathVariable String numberOne,
		@PathVariable String numberTwo
	) throws Exception {
		InputValidator.checkSupportedValues(numberOne, numberTwo);
		
		return mathOperations.sub(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));
	}

	@GetMapping("/mult/{numberOne}/{numberTwo}")
	public Double mult(
		@PathVariable String numberOne,
		@PathVariable String numberTwo
	) throws Exception {
		InputValidator.checkSupportedValues(numberOne, numberTwo);
		
		return mathOperations.mult(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));
	}

	@GetMapping("/div/{numberOne}/{numberTwo}")
	public Double div(
		@PathVariable String numberOne,
		@PathVariable String numberTwo
	) throws Exception {
		InputValidator.checkSupportedValues(numberOne, numberTwo);
		
		return mathOperations.div(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));
	}
}
