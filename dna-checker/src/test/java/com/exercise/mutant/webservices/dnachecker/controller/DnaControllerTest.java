package com.exercise.mutant.webservices.dnachecker.controller;


import java.util.ArrayList;

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
	void EmptyDnaSecuence() throws Exception {
		
		HumanDna dna = new HumanDna();
		
		mockMvc.perform(MockMvcRequestBuilders.post("/mutant").contentType("application/json")
				.content(objectMapper.writeValueAsString(dna)))
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}
	
	@Test
	void NonSquareMatrixDnaSecuence() throws Exception {
		
		HumanDna dna = new HumanDna();
		
		ArrayList<String> dnaSecuence = new ArrayList<String>();
		dnaSecuence.add("AGCC");
		dnaSecuence.add("ATTA");
		dnaSecuence.add("CGC");
		
		dna.setDna(dnaSecuence);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/mutant").contentType("application/json")
				.content(objectMapper.writeValueAsString(dna)))
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}
	
	@Test
	void NonValidCharacterDnaSecuence() throws Exception {
		
		HumanDna dna = new HumanDna();
		
		ArrayList<String> dnaSecuence = new ArrayList<String>();
		dnaSecuence.add("AGCC");
		dnaSecuence.add("AZTA");
		dnaSecuence.add("CGCT");
		dnaSecuence.add("GTAC");
		
		dna.setDna(dnaSecuence);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/mutant").contentType("application/json")
				.content(objectMapper.writeValueAsString(dna)))
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}
	
	@Test
	void OnlyOneHorizontalDnaSecuenceMatch() throws Exception {
		
		HumanDna dna = new HumanDna();
		
		ArrayList<String> dnaSecuence = new ArrayList<String>();
		dnaSecuence.add("AGCCT");
		dnaSecuence.add("ATTAC");
		dnaSecuence.add("CGCTG");
		dnaSecuence.add("GGGGG");
		dnaSecuence.add("GTACT");
		
		dna.setDna(dnaSecuence);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/mutant").contentType("application/json")
				.content(objectMapper.writeValueAsString(dna)))
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}
	
	@Test
	void OnlyOneVerticalDnaSecuenceMatch() throws Exception {
		
		HumanDna dna = new HumanDna();
		
		ArrayList<String> dnaSecuence = new ArrayList<String>();
		dnaSecuence.add("ATCCT");
		dnaSecuence.add("ATTAC");
		dnaSecuence.add("CTCTG");
		dnaSecuence.add("GTCCA");
		dnaSecuence.add("GTACT");
		
		dna.setDna(dnaSecuence);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/mutant").contentType("application/json")
				.content(objectMapper.writeValueAsString(dna)))
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}
	
	@Test
	void OnlyOneDiagonalLtrDnaSecuenceMatch() throws Exception {
		
		HumanDna dna = new HumanDna();
		
		ArrayList<String> dnaSecuence = new ArrayList<String>();
		dnaSecuence.add("CTCCT");
		dnaSecuence.add("ACTAC");
		dnaSecuence.add("CACTG");
		dnaSecuence.add("GGCCA");
		dnaSecuence.add("GAACC");
		
		dna.setDna(dnaSecuence);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/mutant").contentType("application/json")
				.content(objectMapper.writeValueAsString(dna)))
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}
	
	@Test
	void OnlyOneDiagonalRtlDnaSecuenceMatch() throws Exception {
		
		HumanDna dna = new HumanDna();
		
		ArrayList<String> dnaSecuence = new ArrayList<String>();
		dnaSecuence.add("ATCCT");
		dnaSecuence.add("ACTTC");
		dnaSecuence.add("CATTG");
		dnaSecuence.add("GTCCA");
		dnaSecuence.add("TAACT");
		
		dna.setDna(dnaSecuence);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/mutant").contentType("application/json")
				.content(objectMapper.writeValueAsString(dna)))
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}
	
	@Test
	void OneHorizontalOneVerticalDnaSecuenceMatch() throws Exception {
		
		HumanDna dna = new HumanDna();
		
		ArrayList<String> dnaSecuence = new ArrayList<String>();
		dnaSecuence.add("AGCCCA");
		dnaSecuence.add("ATTACC");
		dnaSecuence.add("CGCTCC");
		dnaSecuence.add("GTTACG");
		dnaSecuence.add("GGGGGG");
		dnaSecuence.add("GTACTT");
		
		dna.setDna(dnaSecuence);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/mutant").contentType("application/json")
				.content(objectMapper.writeValueAsString(dna)))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	void OneVerticalOneDiagonalLtrDnaSecuenceMatch() throws Exception {
		
		HumanDna dna = new HumanDna();
		
		ArrayList<String> dnaSecuence = new ArrayList<String>();
		dnaSecuence.add("AGCTCA");
		dnaSecuence.add("ATTACC");
		dnaSecuence.add("CTCTCC");
		dnaSecuence.add("ATTACG");
		dnaSecuence.add("GCTTAG");
		dnaSecuence.add("GTACTT");
		
		dna.setDna(dnaSecuence);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/mutant").contentType("application/json")
				.content(objectMapper.writeValueAsString(dna)))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	void OneDiagonalLtrOneDiagonalRltDnaSecuenceMatch() throws Exception {
		
		HumanDna dna = new HumanDna();
		
		ArrayList<String> dnaSecuence = new ArrayList<String>();
		dnaSecuence.add("AGCTAA");
		dnaSecuence.add("ATTACC");
		dnaSecuence.add("CTCTGC");
		dnaSecuence.add("TCAACG");
		dnaSecuence.add("GCCCAG");
		dnaSecuence.add("GTCCTT");
		
		dna.setDna(dnaSecuence);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/mutant").contentType("application/json")
				.content(objectMapper.writeValueAsString(dna)))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	void GetMutantStats() throws Exception {
		
		mockMvc.perform(MockMvcRequestBuilders.get("/stats")
				.accept(MediaType.APPLICATION_JSON))
			    .andExpect(MockMvcResultMatchers.status().isOk());
	}

}
