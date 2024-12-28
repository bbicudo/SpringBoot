package br.nom.bicudo.bruno.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.nom.bicudo.bruno.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long>{}