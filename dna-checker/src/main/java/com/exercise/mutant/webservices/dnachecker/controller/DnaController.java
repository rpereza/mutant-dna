package com.exercise.mutant.webservices.dnachecker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.exercise.mutant.webservices.dnachecker.Repository.DnaRepository;
import com.exercise.mutant.webservices.dnachecker.manager.DnaManager;
import com.exercise.mutant.webservices.dnachecker.model.DnaStats;
import com.exercise.mutant.webservices.dnachecker.model.HumanDna;
import com.exercise.mutant.webservices.dnachecker.model.MutantStats;

/**
 * 
 * @author Rodolfo Pérez Avila
 * Clase controladora con servicios de chequeo y consulta de estadísticas
 */
@RestController
public class DnaController {
	
	@Autowired
	private DnaManager manager;
	
	@Autowired
	private DnaRepository repository;
	
	/**
	 * Método que evalua una secuencia de ADN dada
	 * @param humanDna Información de la secuencia de ADN
	 * @return Retorna estado 200 si es mutante y 403 en caso contrario
	 */
	@PostMapping(path = "/mutant")
	public ResponseEntity<Object> Mutant(@RequestBody HumanDna humanDna) {
		boolean valid = false;
		if(humanDna != null) {
			valid = manager.IsMutant(humanDna.getDna());		
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
		//Declaramos las estadísticas de retorno 
		MutantStats stats = new MutantStats();
		
		//Obtenemos la cantidad de humanos no mutantes
		long humanCount = repository.CountNonMutant();
		stats.setCount_human_dna(humanCount);
		
		//obtenemos la cantidad de mutantes
		long mutantCount = repository.CountMutant();				
		stats.setCount_mutant_dna(mutantCount);
		
		//Retornamos las estadísticas
		return stats;
	}
}
