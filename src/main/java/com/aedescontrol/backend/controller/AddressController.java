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

    private final AddressService addressService;

    public AddressController(AddressService addressService, ObjectMapper mapper) {
        this.addressService = addressService;
    }

    @GetMapping
    public List<AddressDTO> getAllAddresses() {
        return addressService.getAllAddresses();
    }

    @GetMapping("/{id}")
    public AddressDTO getAddressById(@PathVariable Long id) {
        return addressService.getAddressByIdOrThrow(id);
    }

    @GetMapping("/status/{status}")
    public List<AddressDTO> getAddressesByStatus(@PathVariable String status) {
        Address.Status enumStatus;
        try {
            enumStatus = Address.Status.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Status inválido: " + status);
        }
        return addressService.getAddressesByStatus(enumStatus);
    }

    @PostMapping
    public ResponseEntity<AddressDTO> createAddress(@RequestBody @Valid CreateAddressDTO dto) {
        AddressDTO saved = addressService.saveAddress(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDTO> updateAddress(@RequestBody @Valid CreateAddressDTO dto, @PathVariable Long id) {
        AddressDTO updatedDto = addressService.updateAddress(dto, id);
        return ResponseEntity.ok(updatedDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }
}
