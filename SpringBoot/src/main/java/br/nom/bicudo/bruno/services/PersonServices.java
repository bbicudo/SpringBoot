package br.nom.bicudo.bruno.services;


import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.nom.bicudo.bruno.exceptions.ResourceNotFoundException;
import br.nom.bicudo.bruno.model.Person;
import br.nom.bicudo.bruno.repositories.PersonRepository;

@Service
public class PersonServices {


	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	@Autowired
	PersonRepository repository;
	
	public List<Person> findAll() {
		logger.info("Finding all people.");

		return repository.findAll() ;
	}

	public Person create(Person person) {
		logger.info("Creating a person");


		return repository.save(person);
	}
	
	public Person findById(Long id) {
		logger.info("Finding a person.");
		
		Person person = new Person();
		person.setFirstName("Bruno");
		person.setLastName("Bicudo");
		person.setAddress("Bragança Paulista - SP - BR");
		person.setGender("Male");
		

		return repository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("No records found for this id."));
	}

	public Person update(Person person) {
		logger.info("Updating a person");
		
		Person entity = repository.findById(person.getId())
			.orElseThrow(() -> new ResourceNotFoundException("No records found for this id."));
		
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());

		return repository.save(person);
	}

	public void delete(Long id) {
		logger.info("Deleting a person");
		
		Person entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this id."));
		
		repository.delete(entity);
	}
	
}
