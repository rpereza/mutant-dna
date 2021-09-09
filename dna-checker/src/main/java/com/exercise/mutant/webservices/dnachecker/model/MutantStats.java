package com.exercise.mutant.webservices.dnachecker.model;

/**
 * @author Rodolfo Pérez Avila
 * Representa el objeto de retorno con la información de las estadísticas
 */
public class MutantStats {
	
	/**
	 * Atributo que representa la cantidad de ADN mutante procesado
	 */
	private int count_mutant_dna;
	
	/**
	 * Atributo que representa la cantidad de ADN humano procesado
	 */
	private int count_human_dna;
	

	/**
	 * @return Retorna la cantidad de ADN mutante
	 */
	public int getCount_mutant_dna() {
		return count_mutant_dna;
	}

	/**
	 * @param Establece la cantidad de ADN mutante
	 */
	public void setCount_mutant_dna(int count_mutant_dna) {
		this.count_mutant_dna = count_mutant_dna;
	}

	/**
	 * @return Retorna la cantidad de ADN humano
	 */
	public int getCount_human_dna() {
		return count_human_dna;
	}

	/**
	 * @param Establece la cantidad de ADN humano
	 */
	public void setCount_human_dna(int count_human_dna) {
		this.count_human_dna = count_human_dna;
	}

	/**
	 * @return Retorna la proporción de ADN mutante sobre el humano
	 */
	public float getRatio() {
		if(this.count_human_dna == 0)
			return this.count_mutant_dna > 0 ? 1 : 0;
		return (float)this.count_mutant_dna/this.count_human_dna;
	}
}
