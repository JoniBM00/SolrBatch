package com.viewnext.solrproject2.tasklets;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WriterTasklet implements Tasklet, StepExecutionListener {

	private SolrClient solrClient;
	private List<String> lPath;

	/**
	 * Hace la conexion con Solr y recoge la lista que mandamos en el otro Tasklet
	 * por el executionContext
	 */
	@Override
	public void beforeStep(StepExecution stepExecution) {
		solrClient = new HttpSolrClient.Builder("http://localhost:8983/solr/Tarifa").build();
		ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();
		this.lPath = (List<String>) executionContext.get("lPath");
	}

	/**
	 * Manda los ficheros json para añadir al Solr
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		ContentStreamUpdateRequest request = new ContentStreamUpdateRequest("/update");
		for (String path : lPath) {
			File file = new File(path);
			request.addFile(file, "application/json");
		}
		solrClient.request(request);
		solrClient.commit();
		log.info("Ficheros insertados en Solr");
		return RepeatStatus.FINISHED;
	}

	/**
	 * Cierra la conexion con Solr
	 */
	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		try {
			solrClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ExitStatus.COMPLETED;
	}

}
