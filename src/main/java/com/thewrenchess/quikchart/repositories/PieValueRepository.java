package com.thewrenchess.quikchart.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.thewrenchess.quikchart.models.PieValue;

@Repository
public interface PieValueRepository extends CrudRepository<PieValue, Long> {
	@Query("SELECT p FROM PieValue p JOIN p.graph g WHERE g.id = ?1")
	List<PieValue> findPieValuesByGraphId(long gid);
}
