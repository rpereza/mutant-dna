package com.exercise.mutant.webservices.dnachecker.manager;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

/**
 * 
 * @author Rodolfo Pérez Avila Clase para implementación de lógica de chequeo de
 *         ADN
 */
@Component
public class DnaManager {

	/*
	 * public boolean IsMutant(ArrayList<String> dna) { //Chequeamos si tiene
	 * elementos el arreglo if(dna.isEmpty()) return false;
	 * 
	 * //Obtenemos la cantidad de elementos de la secuencia para validar int
	 * secuenceSize = dna.size();
	 * 
	 * //Validamos que la secuencia tenga al menos 4 segmentos if(secuenceSize < 4)
	 * return false;
	 * 
	 * //Declaramos un arreglo de string para las combinaciones verticales String[]
	 * vertical = new String[secuenceSize]; //Declaramos dos arreglos de string para
	 * las combinaciones diagonales int diagonalSize = ((secuenceSize - 3)*2)-1;
	 * //String[] diagonal1 = new String[diagonalSize]; //String[] diagonal2 = new
	 * String[diagonalSize];
	 * 
	 * //Declaramos una expresión para validar las letras permitidas Pattern pattern
	 * = Pattern.compile("[ATCG]*");
	 * 
	 * //Recorremos las secuencias para validar for(int i = 0; i < secuenceSize;
	 * i++) { String secuence = dna.get(i); int segmentSize = secuence.length();
	 * //Chequeamos que la secuencia sea consistente if(segmentSize != secuenceSize)
	 * return false;
	 * 
	 * //Chequeamos que la secuencia tenga las letras permitidas Matcher matcher =
	 * pattern.matcher(secuence); if(!matcher.matches()) return false;
	 * 
	 * //Llenamos la presentación vertical de la secuencia for(int j = 0; j <
	 * segmentSize; j++) { vertical[j] += secuence.substring(j, j+1); //diagonal1[j]
	 * }
	 * 
	 * }
	 * 
	 * return true; }
	 */

	/**
	 * Método que verifica una secuencia de ADN dada para chequear si es mutante
	 * 
	 * @param dna
	 * @return Retorna verdadero o falso
	 */
	public boolean IsMutant(ArrayList<String> dna) {
		// Chequeamos si tiene elementos el arreglo
		if (dna.isEmpty())
			return false;

		// Obtenemos la cantidad de elementos de la secuencia para validar
		int secuenceSize = dna.size();

		// Validamos que la secuencia tenga al menos 4 segmentos
		if (secuenceSize < 4)
			return false;

		// Declaramos una expresión para validar las letras permitidas
		Pattern pattern = Pattern.compile("[ATCG]*");

		// declaramos una matriz para representar la secuencia
		char[][] matrix = new char[secuenceSize][secuenceSize];
		// Declaramos un contador para las secuencias completas
		int completedSecuences = 0;
		// Creamos un diccionario para contar las letras del mismo tipo consecutivas
		Hashtable<Character, Integer> fileCount = new Hashtable<Character, Integer>();
		fileCount.put('A', 1);
		fileCount.put('T', 1);
		fileCount.put('C', 1);
		fileCount.put('G', 1);
		// Recorremos las secuencias para validar
		for (int i = 0; i < secuenceSize; i++) {
			String secuence = dna.get(i);
			int segmentSize = secuence.length();
			// Chequeamos que la secuencia sea consistente
			if (segmentSize != secuenceSize)
				return false;

			// Chequeamos que la secuencia tenga las letras permitidas
			Matcher matcher = pattern.matcher(secuence);
			if (!matcher.matches())
				return false;

			// llenamos la matriz para luego hacer recorridos comparativos
			for (int j = 0; j < segmentSize; j++) {
				// obtenemos caracter a caracter
				char letter = secuence.charAt(j);
				// ubicamos en la matriz
				matrix[i][j] = letter;
				// chequeamos si no es la última posición
				if (j < (segmentSize - 1)) {
					char nextletter = secuence.charAt(j + 1);
					// comparamos la letra con la siguiente a ver si son iguales
					if (letter == nextletter) {
						// Incrementamos en 1 la cantidad para la letra consecutiva
						int currentCount = fileCount.get(letter);
						fileCount.replace(letter, ++currentCount);
						// Chequeamos si el consecutivo alcanzó un múltiplo de 4
						if (currentCount % 4 == 0) {
							completedSecuences++;
						}
					} else {
						// Reiniciamos a 1 la cantidad para la letra no consecutiva
						fileCount.replace(letter, 1);
					}
				}
			}
		}

		// Chequeamos si no se ha alcanzado la cantidad para seguir analizando
		if (completedSecuences < 2) {
			// recorremos la matriz para analizar las columnas
			for (int i = 0; i < secuenceSize; i++) {
				// Reiniciamos contadores para chequeo vertical
				fileCount.replace('A', 1);
				fileCount.replace('T', 1);
				fileCount.replace('C', 1);
				fileCount.replace('G', 1);
				for (int j = 0; j < secuenceSize - 1; j++) {
					// obtenemos la letra a comparar
					char columnLetter = matrix[j][i];
					char nextColumnLetter = matrix[j + 1][i];
					// comparamos la letra con la siguiente a ver si son iguales
					if (columnLetter == nextColumnLetter) {
						// Incrementamos en 1 la cantidad para la letra consecutiva
						int currentCount = fileCount.get(columnLetter);
						fileCount.replace(columnLetter, ++currentCount);
						// Chequeamos si el consecutivo alcanzó un múltiplo de 4
						if (currentCount % 4 == 0) {
							completedSecuences++;
						}
					} else {
						// Reiniciamos a 1 la cantidad para la letra no consecutiva
						fileCount.replace(columnLetter, 1);
					}
				}
			}
		}

		// Chequeamos si no se ha alcanzado la cantidad para seguir analizando
		if (completedSecuences < 2) {

		}

		return false;
	}
}
