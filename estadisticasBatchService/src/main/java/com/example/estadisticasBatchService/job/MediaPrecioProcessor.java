package com.example.estadisticasBatchService.job;

import com.example.estadisticasBatchService.dtos.DtoVenta;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MediaPrecioProcessor implements ItemProcessor<List<DtoVenta>, Map<String, Double>> {

    @Override
    public Map<String, Double> process(List<DtoVenta> ventas) {
        return ventas.stream()
                .collect(Collectors.groupingBy(
                        DtoVenta::getNombreEvento,
                        Collectors.averagingDouble(DtoVenta::getPrecio)
                ));
    }
}
