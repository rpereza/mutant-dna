package com.exercise.mutant.webservices.dnachecker.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Rodolfo Pérez Avila
 * Representa el objeto de ADN procesado con la información de si es mutante o no
 */
@Entity
public class DnaStats {
	
	/**
	 * Atributo que representa el identificador
	 */
	@Id
	@GeneratedValue
	private long id;
		
	/**
	 * Atributo que representa la secuencia de ADN
	 */
	private String dna;
	
	/**
	 * Atributo que representa la cantidad de ADN humano procesado
	 */
	private boolean is_mutant;
	
	
	public DnaStats() {
		super();
	}

	public DnaStats(String dna, boolean is_mutant) {
		super();
		this.dna = dna;
		this.is_mutant = is_mutant;
	}

	/**
	 * @return Retorna el identificador
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param Establece el identificador
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	/**
	 * @return Retorna la secuencia de ADN
	 */
	public String getDna() {
		return dna;
	}

	/**
	 * @param Establece la secuencia de ADN
	 */
	public void setDna(String dna) {
		this.dna = dna;
	}

	/**
	 * @return Retorna si es mutante
	 */
	public boolean getIs_mutant() {
		return is_mutant;
	}

	/**
	 * @param Establece si es mutante
	 */
	public void setIs_mutant(boolean is_mutant) {
		this.is_mutant = is_mutant;
	}
	
}
