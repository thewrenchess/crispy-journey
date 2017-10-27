package com.thewrenchess.quikchart.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.thewrenchess.quikchart.models.HValue;

@Repository
public interface HValueRepository extends CrudRepository<HValue, Long> {
	@Query("SELECT h FROM HValue h JOIN h.graph g WHERE g.id = ?1")
	List<HValue> findHValuesByGraphId(long gid);
}
