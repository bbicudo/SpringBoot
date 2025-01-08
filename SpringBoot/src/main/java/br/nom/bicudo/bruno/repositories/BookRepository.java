package br.nom.bicudo.bruno.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.nom.bicudo.bruno.model.Book;

public interface BookRepository extends JpaRepository<Book, Long>{}