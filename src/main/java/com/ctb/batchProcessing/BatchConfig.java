package com.ctb.batchProcessing;



import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContexts;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.ctb.entity.Personne;
import com.ctb.repository.PersonneRepository;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
	
	
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	
	@Autowired
	private PersonneRepository personneRepository;
	
	
	
	@Bean
	public FlatFileItemReader<Personne> reader(){
		
		
		return new FlatFileItemReaderBuilder<Personne>()
				.name("personneItemReader")
				.resource(new ClassPathResource("sample-data.csv"))
				.delimited()
				.names(new String[] {"firstName","lastName"})
				.fieldSetMapper(new BeanWrapperFieldSetMapper<Personne>() {{setTargetType(Personne.class);}}).build();
		
	}
	
	@Bean
	public PersonnetemProcessor processor() {
		
		return new PersonnetemProcessor();
	}
	
	
	
	@Bean
	public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
	  return jobBuilderFactory.get("importUserJob")
	    .incrementer(new RunIdIncrementer())
	    .listener(listener)
	    .flow(step1)
	    .end()
	    .build();
	}
	
	@Bean
	public PersonneItemWriter writer() {
		
		return new PersonneItemWriter();
	}

	@Bean
	public Step step1(List <? extends Personne> writer) {
	  return stepBuilderFactory.get("step1")
	    .<Personne, Personne> chunk(10)
	    .reader(reader())
	    .processor(processor())
	    .writer(writer())
	    .build();
	}
	
	

}
