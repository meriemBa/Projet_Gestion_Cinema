
package org.sid.dao;

import java.util.List;

import org.sid.entities.ProjectionFilm;
import org.sid.entities.Seance;
import org.sid.entities.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
@RepositoryRestResource
@CrossOrigin("*")
public interface TicketRepository extends JpaRepository<Ticket, Long> {
	
	//public List<Ticket> findAll();
	//public Page<Ticket> findAll(Pageable pageable);
	public Page<Ticket> findByNameClientContains(String mc,  Pageable pageable);
}