package br.nom.bicudo.bruno.services;


import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import br.nom.bicudo.bruno.controllers.PersonController;
import br.nom.bicudo.bruno.data.vo.v1.PersonVO;
import br.nom.bicudo.bruno.exceptions.ResourceNotFoundException;
import br.nom.bicudo.bruno.mapper.DozerMapper;
import br.nom.bicudo.bruno.model.Person;
import br.nom.bicudo.bruno.repositories.PersonRepository;

@Service
public class PersonServices {


	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	@Autowired
	PersonRepository repository;
	
	public List<PersonVO> findAll() {
		logger.info("Finding all people.");

		var people = DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);
		
		people.stream().forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
		
		return people;
	}

	public PersonVO create(PersonVO person) {
		logger.info("Creating a person");

		var entity = DozerMapper.parseObject(person, Person.class);
		var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		
		return vo;
	}
	
	public PersonVO findById(Long id) {
		logger.info("Finding a person.");
		
		var entity =  repository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("No records found for this id."));
		
		var vo = DozerMapper.parseObject(entity, PersonVO.class);
		
		vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		
		return vo;
	}

	public PersonVO update(PersonVO person) {
		logger.info("Updating a person");
		
		var entity = repository.findById(person.getKey())
			.orElseThrow(() -> new ResourceNotFoundException("No records found for this id."));
		
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());

		var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		
		return vo;
	}

	public void delete(Long id) {
		logger.info("Deleting a person");
		
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this id."));
		
		repository.delete(entity);
	}
	
}
