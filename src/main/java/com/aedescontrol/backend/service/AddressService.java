package com.aedescontrol.backend.service;

import com.aedescontrol.backend.dto.CreateAddressDTO;
import com.aedescontrol.backend.exception.ResourceNotFoundException;
import com.aedescontrol.backend.mapper.AddressMapper;
import com.aedescontrol.backend.model.Address;
import com.aedescontrol.backend.repository.AddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AddressService {

    private static final Logger log = LoggerFactory.getLogger(AddressService.class);
    private final AddressRepository addressRepository;
    private final AddressMapper mapper;

    public AddressService(AddressRepository addressRepository, AddressMapper mapper) {
        this.addressRepository = addressRepository;
        this.mapper = mapper;
    }

    public List<Address> getAllAddresses() {
        return addressRepository.findAll(); // to-do: Pageable
    }

    public Address getAddressByIdOrThrow(Long id) {
        log.debug("Procurando endereço no banco, id={}", id);
        return addressRepository.findById(id).map(address -> {
                    log.info("Endereço encontrado: {}", id);
                    return address;
                })
                .orElseThrow(() -> {
                    log.warn("Endereço não encontrado: {}", id);
                    return new ResourceNotFoundException("Endereço não encontrado com id: " + id);
                });

    }

    public List<Address> getAddressesByStatus(Address.Status status) {
        log.debug("Procurando endereço no banco, status={}", status);
        List<Address> addresses = addressRepository.findByStatus(status);

        if (addresses.isEmpty()) {
            log.warn("Endereço não encontrado com status: {}", status);
            throw new ResourceNotFoundException("Endereço não encontrado com status: " + status);
        }

        log.debug("Foram encontrados {} endereços com status {}", addresses.size(), status);
        return addresses;
    }

    public Address saveAddress(CreateAddressDTO dto) {
        log.debug("Salvando endereço: body={}", dto);
        Address address = mapper.toEntity(dto);
        log.debug("DTO convertido para entidade: {}", address);

        Address saved = addressRepository.save(address);
        log.info("Endereço salvo com sucesso, id={}", saved.getId());
        return saved;
    }

    public Address updateAddress(CreateAddressDTO dto, Long id) {
        log.info("Atualizando endereço ID={}", id);
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Tentativa de atualizar endereço inexistente. ID={}", id);
                    return new ResourceNotFoundException("Endereço não encontrado com id: " + id);
                });

        log.debug("Dados recebidos para atualização: {}", dto);
        mapper.updateEntity(address, dto);

        Address saved = addressRepository.save(address);

        log.info("Endereço ID={} atualizado com sucesso.", id);
        return saved;
    }

    public void deleteAddress(Long id) {
        log.debug("Solicitada exclusão do endereço ID={}", id);
        long start = System.currentTimeMillis();

        Address address = getAddressByIdOrThrow(id);

        addressRepository.delete(address);

        long elapsed = System.currentTimeMillis() - start;
        log.info("Endereço ID={} deletado com sucesso em {}ms", id, elapsed);
    }

}
