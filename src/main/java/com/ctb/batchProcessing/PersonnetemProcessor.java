package com.ctb.batchProcessing;

import org.springframework.batch.item.ItemProcessor;

import com.ctb.entity.Personne;

public class PersonnetemProcessor implements ItemProcessor<Personne, Personne> {
	
	public Personne process(Personne personne) {
		
		String firsName = personne.getFirstName().toUpperCase();
		String lastName= personne.getLastName().toUpperCase();
		
		Personne p= new Personne();
		
		p.setFirstName(firsName);
		p.setLastName(lastName);
		
		return p;
	}

}
