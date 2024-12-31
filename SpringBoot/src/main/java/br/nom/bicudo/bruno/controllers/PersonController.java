package br.nom.bicudo.bruno.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.nom.bicudo.bruno.data.vo.v1.PersonVO;
import br.nom.bicudo.bruno.services.PersonServices;
import br.nom.bicudo.bruno.util.MediaType;

@RestController
@RequestMapping("/api/person/v1")
public class PersonController {
	
	@Autowired
	private PersonServices service;
	
	@GetMapping(
		value = "/{id}",
		produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML }
	)
	public PersonVO findById(
		@PathVariable Long id
	) {
		return service.findById(id);
	}

	@GetMapping(produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML })
	public List<PersonVO> findAll(){
		return service.findAll();
	}
	
	@PostMapping(
		produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML },
		consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML }
	)
	public PersonVO create(
		@RequestBody PersonVO person
	){
		return service.create(person);
	}

	@PutMapping(
		produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML },
		consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML }
	)
	public PersonVO update(
			@RequestBody PersonVO person
			){
		return service.update(person);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(
		@PathVariable Long id
	){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
