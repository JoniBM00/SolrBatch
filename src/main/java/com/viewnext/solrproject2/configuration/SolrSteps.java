package com.viewnext.solrproject2.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.viewnext.solrproject2.tasklets.ReaderTasklet;
import com.viewnext.solrproject2.tasklets.WriterTasklet;

@Configuration
public class SolrSteps {

	@Bean
	public WriterTasklet writerTasklet() {
		return new WriterTasklet();
	}

	@Bean
	public ReaderTasklet readerTasklet() {
		return new ReaderTasklet();
	}

	@Bean
	public Step addToSolr(JobRepository jobRepository, PlatformTransactionManager transactionManager) {

		return new StepBuilder("addToSolr", jobRepository).tasklet(writerTasklet(), transactionManager).build();

	}

	@Bean
	public Step getJsonPaths(JobRepository jobRepository, PlatformTransactionManager transactionManager) {

		return new StepBuilder("getJsonPaths", jobRepository).tasklet(readerTasklet(), transactionManager).build();

	}

	@Bean
	public Job job(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new JobBuilder("taskletsJob", jobRepository).start(getJsonPaths(jobRepository, transactionManager))
				.next(addToSolr(jobRepository, transactionManager)).build();
	}

}
