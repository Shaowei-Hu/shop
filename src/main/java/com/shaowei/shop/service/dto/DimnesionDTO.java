package com.shaowei.shop.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Dimnesion entity.
 */
public class DimnesionDTO implements Serializable {

    private String id;

    private Double length;

    private Double width;

    private Double height;

    private Double weight;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DimnesionDTO dimnesionDTO = (DimnesionDTO) o;
        if (dimnesionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dimnesionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DimnesionDTO{" +
            "id=" + getId() +
            ", length=" + getLength() +
            ", width=" + getWidth() +
            ", height=" + getHeight() +
            ", weight=" + getWeight() +
            "}";
    }
}
