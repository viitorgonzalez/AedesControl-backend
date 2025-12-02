package com.aedescontrol.backend.service;

import com.aedescontrol.backend.dto.AddressDTO;
import com.aedescontrol.backend.dto.CityDTO;
import com.aedescontrol.backend.exception.ResourceNotFoundException;
import com.aedescontrol.backend.mapper.ObjectMapper;
import com.aedescontrol.backend.repository.CityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {
    private static final Logger log = LoggerFactory.getLogger(CityService.class);
    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<CityDTO> getAllCities() {
        log.debug("Procurando todos as cidades");
        return ObjectMapper.parseListObjects(cityRepository.findAll(), CityDTO.class);
    }

    public CityDTO getCityByIdOrThrow(Long id) {
        log.debug("Procurando cidade no banco, id={}", id);

        return cityRepository.findById(id).map( city -> {
           log.info("Cidade encontrada: {}", id);
           return ObjectMapper.parseObject(city, CityDTO.class);
        }).orElseThrow(() -> {
          log.warn("Cidade não encontrada: {}", id);
          return new ResourceNotFoundException("Cidade não encontrada com id: " + id);
        });
    }
}
