package com.aedescontrol.backend.dto;

import com.aedescontrol.backend.config.StatusDeserializer;
import com.aedescontrol.backend.model.Address.Status;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@JsonDeserialize(using = StatusDeserializer.class)
public class AddressDTO {

    private Long id;

    @NotNull
    private String street;

    @NotNull
    private String city;

    @NotNull
    private String zipCode;

    @NotNull
    @Pattern(regexp = "SUSPEITA|CONFIRMADO|LIVRE", message = "Status inválido")
    @JsonDeserialize(using = StatusDeserializer.class)
    private Status status;

    private Double latitude;
    private Double longitude;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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
