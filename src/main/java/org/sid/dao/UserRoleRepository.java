package org.sid.dao;
import org.sid.entities.roles;
import org.sid.entities.user_roles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
@RepositoryRestResource
@CrossOrigin("*")
public interface  UserRoleRepository extends JpaRepository<user_roles, Long>{
	
	public Page<roles>  findByRoleContains(String mc, Pageable pageable);

	


}