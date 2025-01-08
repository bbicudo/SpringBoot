package br.nom.bicudo.bruno.services;


import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import br.nom.bicudo.bruno.controllers.BookController;
import br.nom.bicudo.bruno.data.vo.v1.BookVO;
import br.nom.bicudo.bruno.exceptions.RequiredObjectIsNullException;
import br.nom.bicudo.bruno.exceptions.ResourceNotFoundException;
import br.nom.bicudo.bruno.mapper.DozerMapper;
import br.nom.bicudo.bruno.model.Book;
import br.nom.bicudo.bruno.repositories.BookRepository;

@Service
public class BookServices {


	private Logger logger = Logger.getLogger(BookServices.class.getName());
	
	@Autowired
	BookRepository repository;
	
	public List<BookVO> findAll() {
		logger.info("Finding all people.");

		var people = DozerMapper.parseListObjects(repository.findAll(), BookVO.class);
		
		people.stream().forEach(p -> p.add(linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel()));
		
		return people;
	}

	public BookVO create(BookVO book) {
		if (book == null) {
			throw new RequiredObjectIsNullException();
		}

		logger.info("Creating a book");

		var entity = DozerMapper.parseObject(book, Book.class);
		var vo = DozerMapper.parseObject(repository.save(entity), BookVO.class);
		
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
		
		return vo;
	}
	
	public BookVO findById(Long id) {
		logger.info("Finding a book.");
		
		var entity =  repository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("No records found for this id."));
		
		var vo = DozerMapper.parseObject(entity, BookVO.class);
		
		vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
		
		return vo;
	}

	public BookVO update(BookVO book) {
		if (book == null) {
			throw new RequiredObjectIsNullException();
		}

		logger.info("Updating a book");
		
		var entity = repository.findById(book.getKey())
			.orElseThrow(() -> new ResourceNotFoundException("No records found for this id."));
		
		entity.setAuthor(book.getAuthor());
		entity.setLaunchDate(book.getLaunchDate());
		entity.setPrice(book.getPrice());
		entity.setTitle(book.getTitle());

		var vo = DozerMapper.parseObject(repository.save(entity), BookVO.class);
		
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
		
		return vo;
	}

	public void delete(Long id) {
		logger.info("Deleting a book");
		
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this id."));
		
		repository.delete(entity);
	}
	
}
