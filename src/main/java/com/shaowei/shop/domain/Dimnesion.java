package com.shaowei.shop.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Dimnesion.
 */
@Document(collection = "dimnesion")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "dimnesion")
public class Dimnesion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("length")
    private Double length;

    @Field("width")
    private Double width;

    @Field("height")
    private Double height;

    @Field("weight")
    private Double weight;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getLength() {
        return length;
    }

    public Dimnesion length(Double length) {
        this.length = length;
        return this;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public Dimnesion width(Double width) {
        this.width = width;
        return this;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public Dimnesion height(Double height) {
        this.height = height;
        return this;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public Dimnesion weight(Double weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
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
        Dimnesion dimnesion = (Dimnesion) o;
        if (dimnesion.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dimnesion.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Dimnesion{" +
            "id=" + getId() +
            ", length=" + getLength() +
            ", width=" + getWidth() +
            ", height=" + getHeight() +
            ", weight=" + getWeight() +
            "}";
    }
}
