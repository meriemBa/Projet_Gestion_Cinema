package org.sid.entities;

import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
public class roles {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)

	private Long id;
	private String roles;
	
	@OneToMany(mappedBy="role")
	private List<User> users;
}
