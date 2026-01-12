package com.ifmo.isdb.strattanoakmant.service;

import java.util.List;
import java.util.stream.Collectors;

public interface MappingService {
    <S, D> D map(S source, Class<D> destinationClass);

    default <S, D> List<D> mapList(List<S> source, Class<D> destinationClass) {
        return source.stream()
                .map(s -> map(s, destinationClass))
                .collect(Collectors.toList());
    }
}


