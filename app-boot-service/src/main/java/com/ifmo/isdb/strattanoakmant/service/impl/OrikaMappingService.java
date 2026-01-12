package com.ifmo.isdb.strattanoakmant.service.impl;

import com.ifmo.isdb.strattanoakmant.service.MappingService;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrikaMappingService implements MappingService {

    private final MapperFacade mapperFacade;

    @Override
    public <S, D> D map(S source, Class<D> destinationClass) {
        return mapperFacade.map(source, destinationClass);
    }
}


