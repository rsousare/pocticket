package com.nttdata.ecommerce.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class Mapper extends ModelMapper{

    public <S, T> T mapToEntity(S source, Class<T> destinationType) {
        return map(source, destinationType);
    }
    public <S, T> T mapToDTO(S source, Class<T> destinationType) {
        return map(source, destinationType);
    }
    public <S, T> List<T> mapCartToList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> map(element, targetClass))
                .toList();
    }

}



