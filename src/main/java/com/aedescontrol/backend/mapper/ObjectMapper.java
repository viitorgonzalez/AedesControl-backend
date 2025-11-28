package com.aedescontrol.backend.mapper;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ObjectMapper {

    private static final Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    public static <O, D> D parseObject(O origin, Class<D> destination) {
        return mapper.map(origin, destination);
    }

    public static <O, D> List<D> parseListObjects(List<O> originList, Class<D> destination) {
        return originList.stream()
                .map(o -> mapper.map(o, destination))
                .collect(Collectors.toList());
    }

    public static <O, D> void updateObject(O origin, D destination) {
        mapper.map(origin, destination);
    }

}
