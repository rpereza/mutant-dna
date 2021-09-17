package com.exercise.mutant.webservices.dnachecker.controller;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.exercise.mutant.webservices.dnachecker.model.HumanDna;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class DnaControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void emptyDnaSecuence() throws Exception {
		// Given
		HumanDna dna = new HumanDna();
		// When and then
		mockMvc.perform(MockMvcRequestBuilders.post("/mutant").contentType("application/json")
				.content(objectMapper.writeValueAsString(dna))).andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	void nonSquareMatrixDnaSecuence() throws Exception {
		// Given
		HumanDna dna = new HumanDna(new ArrayList<String>(Arrays.asList("AGCC", "ATTA", "CGC")));

		// When and then
		mockMvc.perform(MockMvcRequestBuilders.post("/mutant").contentType("application/json")
				.content(objectMapper.writeValueAsString(dna))).andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	void nonValidCharacterDnaSecuence() throws Exception {
		// Given
		HumanDna dna = new HumanDna(new ArrayList<String>(Arrays.asList("AGCC", "AZTA", "CGCT", "GTAC")));
		
		// When and then
		mockMvc.perform(MockMvcRequestBuilders.post("/mutant").contentType("application/json")
				.content(objectMapper.writeValueAsString(dna))).andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	void onlyOneHorizontalDnaSecuenceMatch() throws Exception {
		// Given
		HumanDna dna = new HumanDna(new ArrayList<String>(Arrays.asList("AGCCT", "ATTAC", "CGCTG", "GGGGG", "GTACT")));

		// When and then
		mockMvc.perform(MockMvcRequestBuilders.post("/mutant").contentType("application/json")
				.content(objectMapper.writeValueAsString(dna))).andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	void onlyOneVerticalDnaSecuenceMatch() throws Exception {
		// Given
		HumanDna dna = new HumanDna(new ArrayList<String>(Arrays.asList("ATCCT", "ATTAC", "CTCTG", "GTCCA", "GTACT")));

		// When and then
		mockMvc.perform(MockMvcRequestBuilders.post("/mutant").contentType("application/json")
				.content(objectMapper.writeValueAsString(dna))).andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	void onlyOneDiagonalLtrDnaSecuenceMatch() throws Exception {
		// Given
		HumanDna dna = new HumanDna(new ArrayList<String>(Arrays.asList("CTCCT", "ACTAC", "CACTG", "GGCCA", "GAACC")));

		// When and then
		mockMvc.perform(MockMvcRequestBuilders.post("/mutant").contentType("application/json")
				.content(objectMapper.writeValueAsString(dna))).andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	void onlyOneDiagonalRtlDnaSecuenceMatch() throws Exception {
		// Given
		HumanDna dna = new HumanDna(new ArrayList<String>(Arrays.asList("ATCCT", "ACTTC", "CATTG", "GTCCA", "TAACT")));

		// When and then
		mockMvc.perform(MockMvcRequestBuilders.post("/mutant").contentType("application/json")
				.content(objectMapper.writeValueAsString(dna))).andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	void oneHorizontalOneVerticalDnaSecuenceMatch() throws Exception {
		// Given
		HumanDna dna = new HumanDna(new ArrayList<String>(Arrays.asList("AGCCCA", "ATTACC", "CGCTCC", "GTTACG", "GGGGGG", "GTACTT")));

		// When and then
		mockMvc.perform(MockMvcRequestBuilders.post("/mutant").contentType("application/json")
				.content(objectMapper.writeValueAsString(dna))).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void oneVerticalOneDiagonalLtrDnaSecuenceMatch() throws Exception {
		// Given
		HumanDna dna = new HumanDna(new ArrayList<String>(Arrays.asList("AGCTCA", "ATTACC", "CTCTCC", "ATTACG", "GCTTAG", "GTACTT")));

		// When and then
		mockMvc.perform(MockMvcRequestBuilders.post("/mutant").contentType("application/json")
				.content(objectMapper.writeValueAsString(dna))).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void oneDiagonalLtrOneDiagonalRltDnaSecuenceMatch() throws Exception {
		// Given
		HumanDna dna = new HumanDna(new ArrayList<String>(Arrays.asList("AGCTAA", "ATTACC", "CTCTGC", "TCAACG", "GCCCAG", "GTCCTT")));

		// When and then
		mockMvc.perform(MockMvcRequestBuilders.post("/mutant").contentType("application/json")
				.content(objectMapper.writeValueAsString(dna))).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void getMutantStats() throws Exception {

		// When and then
		mockMvc.perform(MockMvcRequestBuilders.get("/stats").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

}
