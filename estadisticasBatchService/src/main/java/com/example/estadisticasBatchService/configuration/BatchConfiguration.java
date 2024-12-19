package com.example.estadisticasBatchService.configuration;

import com.example.estadisticasBatchService.dtos.DtoVenta;
import com.example.estadisticasBatchService.job.EstadisticaItemWriter;
import com.example.estadisticasBatchService.job.VentaItemReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;
import java.util.Map;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Bean
    public Step localizarDatosStep(JobRepository jobRepository,
                                   PlatformTransactionManager transactionManager,
                                   VentaItemReader reader) {
        return new StepBuilder("localizarDatosStep", jobRepository)
                .<List<DtoVenta>, List<DtoVenta>>chunk(1, transactionManager)
                .reader(reader)
                .writer(items -> {
                    // Este paso no hace nada más que identificar dónde están las cosas
                })
                .build();
    }

    @Bean
    public Step calcularMediasStep(JobRepository jobRepository,
                                   PlatformTransactionManager transactionManager,
                                   EstadisticaItemWriter writer) {
        return new StepBuilder("guardarDatosEstadisticasStep", jobRepository)
                .<Map<Long, Double>, Map<String, Double>>chunk(1, transactionManager)
                .writer(writer)
                .build();
    }

    @Bean
    public Job estadisticasJob(JobRepository jobRepository,
                               Step localizarDatosStep,
                               Step calcularMediasStep,
                               Step guardarDatosEstadisticasStep) {
        return new JobBuilder("estadisticasJob", jobRepository)
                .start(localizarDatosStep)
                .next(calcularMediasStep)
                .next(guardarDatosEstadisticasStep)
                .build();
    }
}
