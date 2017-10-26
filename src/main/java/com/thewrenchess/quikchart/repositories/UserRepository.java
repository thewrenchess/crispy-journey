package com.thewrenchess.quikchart.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.thewrenchess.quikchart.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	User findByUsername(String username);
	
	@Query("SELECT u FROM User u JOIN u.roles r WHERE r.name='ROLE_ADMIN'")
	List<User> getAllAdmins();
}
