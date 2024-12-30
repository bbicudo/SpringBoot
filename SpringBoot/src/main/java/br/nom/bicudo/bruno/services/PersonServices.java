package br.nom.bicudo.bruno.services;


import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

		return DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);
	}

	public PersonVO create(PersonVO person) {
		logger.info("Creating a person");

		var entity = DozerMapper.parseObject(person, Person.class);
		var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		
		return vo;
	}
	
	public PersonVO findById(Long id) {
		logger.info("Finding a person.");
		
		var entity =  repository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("No records found for this id."));
		
		return DozerMapper.parseObject(entity, PersonVO.class);
	}

	public PersonVO update(PersonVO person) {
		logger.info("Updating a person");
		
		var entity = repository.findById(person.getId())
			.orElseThrow(() -> new ResourceNotFoundException("No records found for this id."));
		
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());

		var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		
		return vo;
	}

	public void delete(Long id) {
		logger.info("Deleting a person");
		
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this id."));
		
		repository.delete(entity);
	}
	
}
