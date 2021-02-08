package org.sid.dao;


import org.sid.entities.Cinema;
import org.sid.entities.ProjectionFilm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
@RepositoryRestResource
@CrossOrigin("*")
public interface ProjectionFilmRepository extends JpaRepository<ProjectionFilm, Long>{


//	public Page<ProjectionFilm> findByIdContains( String mc,Pageable pageable);
	//public Page<Cinema> findByNameContains(String mc, Pageable pageable);

	//public Page<ProjectionFilm> findAllById(String mc,Pageable pageable);

	//public Page<ProjectionFilm> findByPrixContains(String mc,  Pageable pageable);
	
	public Page<ProjectionFilm> findByFilmTitreContains(String mc,  Pageable pageable);

	
	
	

	
}
