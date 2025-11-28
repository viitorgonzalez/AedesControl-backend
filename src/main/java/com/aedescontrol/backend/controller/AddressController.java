package com.aedescontrol.backend.controller;

import com.aedescontrol.backend.dto.AddressDTO;
import com.aedescontrol.backend.dto.CreateAddressDTO;
import com.aedescontrol.backend.mapper.ObjectMapper;
import com.aedescontrol.backend.model.Address;
import com.aedescontrol.backend.service.AddressService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    private static final Logger log = LoggerFactory.getLogger(AddressController.class);

    private final AddressService addressService;

    public AddressController(AddressService addressService, ObjectMapper mapper) {
        this.addressService = addressService;
    }

    @GetMapping
    public List<AddressDTO> getAllAddresses() {
        long start = System.currentTimeMillis();
        log.info("GET /addresses - Início da requisição");

        List<AddressDTO> result = addressService.getAllAddresses();

        long elapsed = System.currentTimeMillis() - start;
        log.info("GET /addresses - Sucesso. {} endereços encontrados em {}ms", result.size(), elapsed);

        return result;
    }

    @GetMapping("/{id}")
    public AddressDTO getAddressById(@PathVariable Long id) {
        long start = System.currentTimeMillis();
        log.info("GET /addresses/{} - Início da requisição", id);

        AddressDTO addressDto = addressService.getAddressByIdOrThrow(id);

        long elapsed = System.currentTimeMillis() - start;
        log.info("GET /addresses/{} - Concluído em {}ms", id, elapsed);

        return addressDto;
    }

    @GetMapping("/status/{status}")
    public List<AddressDTO> getAddressesByStatus(@PathVariable String status) {
        long start = System.currentTimeMillis();
        log.info("GET /addresses/status/{} - Início da requisição", status);

        Address.Status enumStatus;
        try {
            enumStatus = Address.Status.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Status inválido: " + status);
        }

        long elapsed = System.currentTimeMillis() - start;
        log.info("GET /addresses/status/{} - Concluído em {}ms", status, elapsed);

        return addressService.getAddressesByStatus(enumStatus);
    }

    @PostMapping
    public ResponseEntity<AddressDTO> createAddress(@RequestBody @Valid CreateAddressDTO dto) {
        long start = System.currentTimeMillis();
        log.info("POST /addresses - Início da requisição, body recebido");

        AddressDTO saved = addressService.saveAddress(dto);

        long elapsed = System.currentTimeMillis() - start;
        log.info("POST /addresses - Endereço criado com ID={} em {}ms", saved.getId(), elapsed);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDTO> updateAddress(@RequestBody @Valid CreateAddressDTO dto, @PathVariable Long id) {
        long start = System.currentTimeMillis();
        log.info("PUT /addresses/{} - Início da requisição", id);

        AddressDTO updatedDto = addressService.updateAddress(dto, id);

        long elapsed = System.currentTimeMillis() - start;
        log.info("PUT /addresses/{} - Atualização concluída em {}ms", id, elapsed);

        return ResponseEntity.ok(updatedDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        long start = System.currentTimeMillis();
        log.info("DELETE /addresses/{} - Início da requisição", id);

        addressService.deleteAddress(id);

        long elapsed = System.currentTimeMillis() - start;
        log.info("DELETE /addresses/{} - Exclusão concluída em {}ms", id, elapsed);

        return ResponseEntity.noContent().build();
    }
}
