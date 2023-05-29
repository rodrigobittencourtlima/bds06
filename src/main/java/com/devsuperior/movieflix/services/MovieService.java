package com.devsuperior.movieflix.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.MovieByGenreDTO;
import com.devsuperior.movieflix.dto.MovieByIdDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.exceptions.ResourceNotFoundException;
import com.devsuperior.movieflix.repositories.MovieRepository;

@Service
public class MovieService {

	@Autowired
	private MovieRepository repository;

	@Transactional(readOnly = true)
	public MovieByIdDTO findById(Long id) {
		Optional<Movie> opt = repository.findById(id);
		Movie entity = opt.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new MovieByIdDTO(entity);
	}

	@Transactional(readOnly = true)
	public Page<MovieByGenreDTO> findByGenre(Long genreId, Pageable pageable) {
		Page<Movie> movies;
		if (genreId == 0) {
			movies = repository.findAllByOrderByTitle(pageable);
		} else {
			movies = repository.findByGenreByOrderByTitle(genreId, pageable);
		}
		return movies.map(movie -> new MovieByGenreDTO(movie));
	}

}