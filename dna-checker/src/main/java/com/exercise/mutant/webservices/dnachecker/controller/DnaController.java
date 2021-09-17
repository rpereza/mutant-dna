package com.exercise.mutant.webservices.dnachecker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.exercise.mutant.webservices.dnachecker.manager.DnaManager;
import com.exercise.mutant.webservices.dnachecker.model.HumanDna;
import com.exercise.mutant.webservices.dnachecker.model.MutantStats;
import com.exercise.mutant.webservices.dnachecker.repository.DnaRepository;

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
	public ResponseEntity<Object> mutant(@RequestBody HumanDna humanDna) {
		//Chequeamos la secuencia de ADN
		boolean valid = manager.isMutant(humanDna.getDna());		
		//Chequeamos el resultado de la validacion
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
	public MutantStats stats() {
		//Declaramos las estadísticas de retorno 
		MutantStats stats = new MutantStats();
		
		//Obtenemos la cantidad de humanos no mutantes
		long humanCount = repository.countNonMutant();
		stats.setCount_human_dna(humanCount);
		
		//obtenemos la cantidad de mutantes
		long mutantCount = repository.countMutant();				
		stats.setCount_mutant_dna(mutantCount);
		
		//Retornamos las estadísticas
		return stats;
	}
}
