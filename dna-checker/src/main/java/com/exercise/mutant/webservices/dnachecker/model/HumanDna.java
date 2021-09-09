package com.exercise.mutant.webservices.dnachecker.model;

import java.util.ArrayList;
import java.util.List;

public class HumanDna {

	/**
	 * Representa la secuencia del ADN de un humano
	 */
	private List<String> dna;
	
	/**
	 * Constructor que inicializa la secuencia vacia
	 */
	public HumanDna() {
		this.dna = new ArrayList<String>();
	}

	/**
	 * @return Retorna la secuencia de ADN
	 */
	public List<String> getDna() {
		return dna;
	}

	/**
	 * @param Establece la secuencia de ADN
	 */
	public void setDna(List<String> dna) {
		this.dna = dna;
	}
}
