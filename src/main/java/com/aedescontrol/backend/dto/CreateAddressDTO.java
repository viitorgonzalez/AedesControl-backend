package com.aedescontrol.backend.dto;

import com.aedescontrol.backend.config.StatusDeserializer;
import com.aedescontrol.backend.model.Address;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.*;

public class CreateAddressDTO {

    @NotBlank
    @Size(max = 255)
    private String street;

    @NotBlank
    private String city;

    @NotBlank
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP must be in the format 12345-678")
    private String zipCode;

    @NotNull
    @Pattern(regexp = "SUSPEITA|CONFIRMADO|LIVRE", message = "Status inválido")
    @JsonDeserialize(using = StatusDeserializer.class)
    private Address.Status status;

    @DecimalMin(value = "-90.0", message = "Latitude must be >= -90")
    @DecimalMax(value = "90.0", message = "Latitude must be <= 90")
    private Double latitude;

    @DecimalMin(value = "-180.0", message = "Longitude must be >= -180")
    @DecimalMax(value = "180.0", message = "Longitude must be <= 180")
    private Double longitude;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Address.Status getStatus() {
        return status;
    }

    public void setStatus(Address.Status status) {
        this.status = status;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
