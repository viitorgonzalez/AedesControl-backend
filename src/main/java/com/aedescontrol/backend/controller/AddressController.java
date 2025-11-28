package com.aedescontrol.backend.controller;

import com.aedescontrol.backend.dto.AddressDTO;
import com.aedescontrol.backend.dto.CreateAddressDTO;
import com.aedescontrol.backend.mapper.AddressMapper;
import com.aedescontrol.backend.model.Address;
import com.aedescontrol.backend.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    private final AddressService addressService;
    private final AddressMapper mapper;

    public AddressController(AddressService addressService, AddressMapper mapper) {
        this.addressService = addressService;
        this.mapper = mapper;
    }

    @GetMapping()
    public List<AddressDTO> getAllAddresses() {
        return addressService.getAllAddresses()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public AddressDTO getAddressById(@PathVariable Long id) {
        return mapper.toDTO(addressService.getAddressByIdOrThrow(id));
    }

    @GetMapping("/status/{status}")
    public List<AddressDTO> getAddressesByStatus(@PathVariable String status) {
        Address.Status enumStatus = Address.Status.valueOf(status.toUpperCase());
        return addressService.getAddressesByStatus(enumStatus)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @PostMapping
    public ResponseEntity<AddressDTO> createAddress(@RequestBody @Valid CreateAddressDTO dto) {
        Address saved = addressService.saveAddress(dto);
        AddressDTO savedDTO = mapper.toDTO(saved);

        URI location = URI.create("/addresses/" + saved.getId());

        return ResponseEntity
                .created(location)
                .body(savedDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDTO> updateAddress(@RequestBody @Valid CreateAddressDTO dto, @PathVariable Long id) {
        Address address = addressService.updateAddress(dto, id);
        AddressDTO addressDTO = mapper.toDTO(address);
        return ResponseEntity.ok(addressDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }
}
