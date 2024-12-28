package br.nom.bicudo.bruno.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import br.nom.bicudo.bruno.model.Person;

@Service
public class PersonServices {

	private final AtomicLong counter = new AtomicLong();
	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	public List<Person> findAll() {
		List<Person> persons = new ArrayList<>();
		logger.info("Finding all people.");
		for (int i = 0; i < 8; i++) {
			Person person = mockPerson(i);
			persons.add(person);
		}

		return persons ;
	}

	public Person create(Person person) {
		logger.info("Creating a person");

		return person;
	}

	public Person update(Person person) {
		logger.info("Updating a person");
		
		return person;
	}

	public void delete(String id) {
		logger.info("Deleting a person");
	}

	public Person findById(String id) {
		logger.info("Finding a person.");
		
		Person person = new Person();
		person.setId(counter.incrementAndGet());
		person.setFirstName("Bruno");
		person.setLastName("Bicudo");
		person.setAddress("Bragança Paulista - SP - BR");
		person.setGender("Male");
		
		return person;
	}
	

	private Person mockPerson(int i) {
		Person person = new Person();
		person.setId(counter.incrementAndGet());
		person.setFirstName("Bruno" + i);
		person.setLastName("Bicudo" + i);
		person.setAddress("Bragança Paulista - SP - BR" + i);
		person.setGender("Male");
		
		return person;
	}
	
}
