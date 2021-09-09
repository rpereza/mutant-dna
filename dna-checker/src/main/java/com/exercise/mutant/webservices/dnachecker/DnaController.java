package com.exercise.mutant.webservices.dnachecker;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author Rodolfo Pérez Avila
 * Clase controladora con servicios de chequeo y consulta de estadísticas
 */
@RestController
public class DnaController {
	
	@GetMapping(path = "/stats")
	public MutantStats Stats() {
		MutantStats stats = new MutantStats();
		stats.setCount_human_dna(200);
		stats.setCount_mutant_dna(40);
		return stats;
	}
}
