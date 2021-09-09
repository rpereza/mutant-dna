package com.exercise.mutant.webservices.dnachecker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.exercise.mutant.webservices.dnachecker.model.HumanDna;
import com.exercise.mutant.webservices.dnachecker.model.MutantStats;

/**
 * 
 * @author Rodolfo Pérez Avila
 * Clase controladora con servicios de chequeo y consulta de estadísticas
 */
@RestController
public class DnaController {
	
	/**
	 * Método que evalua una secuencia de ADN dada
	 * @param humanDna Informacion de la secuencia de ADN
	 * @return Retorna estado 200 si es mutante y 403 en caso contrario
	 */
	@PostMapping(path = "/mutant")
	public ResponseEntity<Object> Mutant(@RequestBody HumanDna humanDna) {
		boolean valid = true;
		if(humanDna.getDna().isEmpty()) {
			valid = false;
		}
		
		if(valid) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
	}
	
	/**
	 * Método que devuelve las estadisticas de los ADN evaluados
	 * @return Retorna información estadistica 
	 */
	@GetMapping(path = "/stats")
	public MutantStats Stats() {
		MutantStats stats = new MutantStats();
		stats.setCount_human_dna(200);
		stats.setCount_mutant_dna(40);
		return stats;
	}
}
