package com.thewrenchess.quikchart.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.thewrenchess.quikchart.models.Graph;

@Repository
public interface GraphRepository extends CrudRepository<Graph, Long> {
	@Query("SELECT g FROM Graph g JOIN g.user u WHERE u.id = ?1 ORDER BY g.updatedAt DESC")
	List<Graph> getAllGraphByUserId(long uid);
	
	@Query("Select g FROM PValue p JOIN p.graph g WHERE p.id = ?1")
	Graph getGraphByPValueId(long pid);
}
