package com.exercise.mutant.webservices.dnachecker.model;

import java.util.ArrayList;

public class HumanDna {

	/**
	 * Representa la secuencia del ADN de un humano
	 */
	private ArrayList<String> dna;
	
	/**
	 * Constructor que inicializa la secuencia vacia
	 */
	public HumanDna() {
		this.dna = new ArrayList<String>();
	}
	
	/**
	 * Constructor que inicializa con una secuencia dada
	 */
	public HumanDna(ArrayList<String> dna) {
		this.dna = dna;
	}

	/**
	 * @return Retorna la secuencia de ADN
	 */
	public ArrayList<String> getDna() {
		return dna;
	}

	/**
	 * @param Establece la secuencia de ADN
	 */
	public void setDna(ArrayList<String> dna) {
		this.dna = dna;
	}
}
