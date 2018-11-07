package com.shaowei.shop.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Product.
 */
@Document(collection = "product")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("brand")
    private String brand;

    @Field("name")
    private String name;

    @Field("release_date")
    private Instant releaseDate;

    @Field("comment")
    private String comment;

    @Field("manufacture_origin")
    private String manufactureOrigin;

    @Field("meterials")
    private String meterials;

    @Field("external_url")
    private String externalUrl;

    @Field("original_price")
    private Double originalPrice;

    @Field("actual_price")
    private Double actualPrice;

    @Field("garantie")
    private Boolean garantie;

    @Field("photo")
    private String photo;

    @Field("state")
    private String state;

    @Field("creation_date")
    private Instant creationDate;

    @Field("modification_date")
    private Instant modificationDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public Product brand(String brand) {
        this.brand = brand;
        return this;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public Product name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getReleaseDate() {
        return releaseDate;
    }

    public Product releaseDate(Instant releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public void setReleaseDate(Instant releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getComment() {
        return comment;
    }

    public Product comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getManufactureOrigin() {
        return manufactureOrigin;
    }

    public Product manufactureOrigin(String manufactureOrigin) {
        this.manufactureOrigin = manufactureOrigin;
        return this;
    }

    public void setManufactureOrigin(String manufactureOrigin) {
        this.manufactureOrigin = manufactureOrigin;
    }

    public String getMeterials() {
        return meterials;
    }

    public Product meterials(String meterials) {
        this.meterials = meterials;
        return this;
    }

    public void setMeterials(String meterials) {
        this.meterials = meterials;
    }

    public String getExternalUrl() {
        return externalUrl;
    }

    public Product externalUrl(String externalUrl) {
        this.externalUrl = externalUrl;
        return this;
    }

    public void setExternalUrl(String externalUrl) {
        this.externalUrl = externalUrl;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public Product originalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
        return this;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Double getActualPrice() {
        return actualPrice;
    }

    public Product actualPrice(Double actualPrice) {
        this.actualPrice = actualPrice;
        return this;
    }

    public void setActualPrice(Double actualPrice) {
        this.actualPrice = actualPrice;
    }

    public Boolean isGarantie() {
        return garantie;
    }

    public Product garantie(Boolean garantie) {
        this.garantie = garantie;
        return this;
    }

    public void setGarantie(Boolean garantie) {
        this.garantie = garantie;
    }

    public String getPhoto() {
        return photo;
    }

    public Product photo(String photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getState() {
        return state;
    }

    public Product state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public Product creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Instant getModificationDate() {
        return modificationDate;
    }

    public Product modificationDate(Instant modificationDate) {
        this.modificationDate = modificationDate;
        return this;
    }

    public void setModificationDate(Instant modificationDate) {
        this.modificationDate = modificationDate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        if (product.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Product{" +
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
