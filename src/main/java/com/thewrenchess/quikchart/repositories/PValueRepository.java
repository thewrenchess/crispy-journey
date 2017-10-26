package com.thewrenchess.quikchart.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.thewrenchess.quikchart.models.PValue;

@Repository
public interface PValueRepository extends CrudRepository<PValue, Long> {
	@Query("SELECT p FROM PValue p JOIN p.graph g WHERE g.id = ?1")
	List<PValue> findPValuesByGraphId(long gid);
}
