package com.viewnext.solrproject2.tasklets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReaderTasklet implements Tasklet, StepExecutionListener {

	private List<Path> lPath;

	/**
	 * Inicializa los atributos
	 */
	@Override
	public void beforeStep(StepExecution stepExecution) {
		lPath = new ArrayList<>();
	}

	/**
	 * Recorre una carpeta y busca los ficheros json, al encontrarlos los va
	 * metiendo en una lista
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		try {
			lPath = Files.list(Paths.get("./../ficheroEntrada")).filter(Files::isRegularFile)
					.filter(path -> path.toString().endsWith(".json")).toList();

		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info("Ficheros json filtrados y copiados");
		return RepeatStatus.FINISHED;
	}

	/**
	 * Al acabar el Tasklet cambia los Path a tipo String y los manda al
	 * executionContext para pasarselos al otro Step
	 */
	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		List<String> lLista = new ArrayList<>();

		for (Path path : lPath) {
			lLista.add(path.toString());
		}
		stepExecution.getJobExecution().getExecutionContext().put("lPath", lLista);

		return ExitStatus.COMPLETED;
	}

}
