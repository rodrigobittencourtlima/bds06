package com.devsuperior.movieflix.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.MovieByGenreDTO;
import com.devsuperior.movieflix.dto.MovieByIdDTO;
import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.repositories.GenreRepository;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.resources.exceptions.ResourceNotFoundException;

@Service
public class MovieService {

	@Autowired
	private MovieRepository repository;

	@Autowired
	private GenreRepository genreRepository;

	@Transactional(readOnly = true)
	public MovieByIdDTO findById(Long id) {
		Optional<Movie> opt = repository.findById(id);
		Movie entity = opt.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new MovieByIdDTO(entity);
	}

	@Transactional(readOnly = true)
	public Page<MovieByGenreDTO> findByGenre(Long genreId, Pageable pageable) {
		Genre genre = genreRepository.getOne(genreId);
		Page<Movie> movies = repository.findByGenreOrderByTitleAsc(genre, pageable);
		return movies.map(movie -> new MovieByGenreDTO(movie));
	}

}