package com.shaowei.shop.service.dto;

import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Product entity.
 */
public class ProductDTO implements Serializable {

    private String id;

    private String brand;

    private String name;

    private Instant releaseDate;

    private String comment;

    private String manufactureOrigin;

    private String meterials;

    private String externalUrl;

    private Double originalPrice;

    private Double actualPrice;

    private Boolean garantie;

    private String photo;

    private String state;

    private Instant creationDate;

    private Instant modificationDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Instant releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getManufactureOrigin() {
        return manufactureOrigin;
    }

    public void setManufactureOrigin(String manufactureOrigin) {
        this.manufactureOrigin = manufactureOrigin;
    }

    public String getMeterials() {
        return meterials;
    }

    public void setMeterials(String meterials) {
        this.meterials = meterials;
    }

    public String getExternalUrl() {
        return externalUrl;
    }

    public void setExternalUrl(String externalUrl) {
        this.externalUrl = externalUrl;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Double getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(Double actualPrice) {
        this.actualPrice = actualPrice;
    }

    public Boolean isGarantie() {
        return garantie;
    }

    public void setGarantie(Boolean garantie) {
        this.garantie = garantie;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Instant getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Instant modificationDate) {
        this.modificationDate = modificationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductDTO productDTO = (ProductDTO) o;
        if (productDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
            "id=" + getId() +
            ", brand='" + getBrand() + "'" +
            ", name='" + getName() + "'" +
            ", releaseDate='" + getReleaseDate() + "'" +
            ", comment='" + getComment() + "'" +
            ", manufactureOrigin='" + getManufactureOrigin() + "'" +
            ", meterials='" + getMeterials() + "'" +
            ", externalUrl='" + getExternalUrl() + "'" +
            ", originalPrice=" + getOriginalPrice() +
            ", actualPrice=" + getActualPrice() +
            ", garantie='" + isGarantie() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", state='" + getState() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", modificationDate='" + getModificationDate() + "'" +
            "}";
    }
}
