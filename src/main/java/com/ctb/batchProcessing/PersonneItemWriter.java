package com.ctb.batchProcessing;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.ctb.entity.Personne;
import com.ctb.repository.PersonneRepository;

public class PersonneItemWriter implements ItemWriter<Personne> {
	
	@Autowired
	PersonneRepository personneRepository;

	@Override
	public void write(List<? extends Personne> items) throws Exception {
		
		personneRepository.saveAll(items);
		
		
		
	}

}
