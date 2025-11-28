package com.aedescontrol.backend.service;

import com.aedescontrol.backend.exception.ResourceNotFoundException;
import com.aedescontrol.backend.mapper.ObjectMapper;
import com.aedescontrol.backend.model.Address;
import com.aedescontrol.backend.repository.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddressServiceTest {

    private AddressRepository addressRepository;
    private ObjectMapper mapper;
    private AddressService addressService;

    @BeforeEach
    void setup() {
        addressRepository = mock(AddressRepository.class);
        mapper = mock(ObjectMapper.class);
        addressService = new AddressService(addressRepository, mapper);
    }

    @Test
    @DisplayName("Should get all addresses from DB")
    void getAllAddresses() {
        Address a1 = new Address();
        a1.setId(1L);
        Address a2 = new Address();
        a2.setId(2L);

        when(addressRepository.findAll()).thenReturn(List.of(a1, a2));

        List<Address> result = addressService.getAllAddresses();

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());
        verify(addressRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should get address by id from DB and found")
    void getAddressByIdOrThrow_found() {
        Address address = new Address();
        address.setId(1L);

        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));

        Address result = addressService.getAddressByIdOrThrow(1L);
        assertEquals(1L, result.getId());
    }

    @Test
    @DisplayName("Should get address by id from DB and not found")
    void getAddressByIdOrThrow_notFound() {
        when(addressRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> addressService.getAddressByIdOrThrow(1L));
    }

    @Test
    void getAddressesByStatus() {
    }

    @Test
    void saveAddress() {
    }

    @Test
    void deleteAddress() {
    }
}