package com.aedescontrol.backend.service;

import com.aedescontrol.backend.dto.AddressDTO;
import com.aedescontrol.backend.dto.CreateAddressDTO;
import com.aedescontrol.backend.exception.ResourceNotFoundException;
import com.aedescontrol.backend.model.Address;
import com.aedescontrol.backend.model.Address.Status;
import com.aedescontrol.backend.repository.AddressRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
class AddressServiceTest {

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16")
                    .withDatabaseName("testdb")
                    .withUsername("test")
                    .withPassword("test");

    @DynamicPropertySource
    static void configureDatasource(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "validate");
        registry.add("spring.flyway.enabled", () -> "true");
    }

    @Autowired
    private AddressService addressService;

    @Autowired
    private AddressRepository addressRepository;

    @BeforeEach
    void cleanDb() {
        addressRepository.deleteAll();
    }

    // ------------------------------
    // TESTE 1: SALVAR
    // ------------------------------
    @Test
    @DisplayName("Should save a new address in DB")
    void saveAddress() {
        CreateAddressDTO dto = new CreateAddressDTO();
        dto.setStreet("Rua A");
        dto.setCity("Cidade B");
        dto.setZipCode("12345-000");
        dto.setStatus(Status.SUSPEITA);
        dto.setLatitude(10.0);
        dto.setLongitude(20.0);

        AddressDTO saved = addressService.saveAddress(dto);

        assertNotNull(saved.getId());
        assertEquals("Rua A", saved.getStreet());
        assertEquals(Status.SUSPEITA, saved.getStatus());
    }

    // ------------------------------
    // TESTE 2: BUSCAR TUDO
//    public Address(AddressDTO dto) {
//        this.street = dto.getStreet();
//        this.city = dto.getCity();
//        this.zipCode = dto.getZipCode();
//        this.status = dto.getStatus();
//        this.latitude = dto.getLatitude();
//        this.longitude = dto.getLongitude();
//    }
    // ------------------------------
    @Test
    @DisplayName("Should get all addresses from DB")
    void getAllAddresses() {
        addressRepository.save(new Address(null, "Rua 1", "Cidade", "00000-000", Status.LIVRE, 1.0, 1.0));
        addressRepository.save(new Address(null, "Rua 2", "Cidade", "00000-000", Status.LIVRE, 2.0, 2.0));

        List<AddressDTO> result = addressService.getAllAddresses();

        assertEquals(2, result.size());
    }

    // ------------------------------
    // TESTE 3: BUSCAR POR ID (OK)
    // ------------------------------
    @Test
    @DisplayName("Should find address by ID")
    void getAddressById_found() {
        Address address = addressRepository.save(
                new Address(null, "Rua Teste", "Cidade", "00000-000", Status.CONFIRMADO, 1.0, 1.0)
        );

        AddressDTO result = addressService.getAddressByIdOrThrow(address.getId());

        assertEquals(address.getId(), result.getId());
        assertEquals("Rua Teste", result.getStreet());
    }

    // ------------------------------
    // TESTE 4: BUSCAR POR ID (ERRO)
    // ------------------------------
    @Test
    @DisplayName("Should throw error when address id not found")
    void getAddressById_notFound() {
        assertThrows(ResourceNotFoundException.class,
                () -> addressService.getAddressByIdOrThrow(999L));
    }

    // ------------------------------
    // TESTE 5: DELETAR
    // ------------------------------
    @Test
    @DisplayName("Should delete by ID")
    void deleteAddress() {
        Address address = addressRepository.save(
                new Address(null, "Rua Teste", "Cidade", "00000-000", Status.SUSPEITA, 0.0, 0.0)
        );

        addressService.deleteAddress(address.getId());

        assertFalse(addressRepository.findById(address.getId()).isPresent());
    }
}
