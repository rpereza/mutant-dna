package com.exercise.mutant.webservices.dnachecker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.exercise.mutant.webservices.dnachecker.model.DnaStats;

@Repository
public interface DnaRepository extends JpaRepository<DnaStats, Long> {
	
	@Query("SELECT COUNT(s) FROM DnaStats s WHERE s.is_mutant=1")
    long countMutant();
	
	@Query("SELECT COUNT(s) FROM DnaStats s WHERE s.is_mutant=0")
    long countNonMutant();
	
	@Query("SELECT COUNT(s) FROM DnaStats s WHERE s.dna=?1")
    long countSameSecuence(String secuence);
}
